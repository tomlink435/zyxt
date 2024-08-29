package com.gk.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.common.APIResponse;
import com.gk.study.entity.Thing;
import com.gk.study.entity.ThingTag;
import com.gk.study.mapper.ThingMapper;
import com.gk.study.mapper.ThingTagMapper;
import com.gk.study.pojo.VO.Block;
import com.gk.study.pojo.VO.BlockTransactionVO;
import com.gk.study.service.BassService;
import com.gk.study.service.ThingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThingServiceImpl extends ServiceImpl<ThingMapper, Thing> implements ThingService {
    private final RestTemplate restTemplate;

    @Autowired
    ThingMapper mapper;

    @Autowired
    ThingTagMapper thingTagMapper;

    @Autowired
    private BassService bassService;

    @Override
    public List<Thing> getThingList(String keyword, String sort, String c, String tag) {
        QueryWrapper<Thing> queryWrapper = new QueryWrapper<>();

        // 搜索
        queryWrapper.like(StringUtils.isNotBlank(keyword), "title", keyword);

        // 排序
        if (StringUtils.isNotBlank(sort)) {
            if (sort.equals("recent")) {
                queryWrapper.orderBy(true, false, "create_time");
            } else if (sort.equals("hot") || sort.equals("recommend")) {
                queryWrapper.orderBy(true, false, "pv");
            }
        }else {
            queryWrapper.orderBy(true, false, "create_time");
        }

        // 根据分类筛选
        if (StringUtils.isNotBlank(c) && !c.equals("-1")) {
            queryWrapper.eq(true, "classification_id", c);
        }

        List<Thing> things = mapper.selectList(queryWrapper);

        // tag筛选
        if (StringUtils.isNotBlank(tag)) {
            List<Thing> tThings = new ArrayList<>();
            QueryWrapper<ThingTag> thingTagQueryWrapper = new QueryWrapper<>();
            thingTagQueryWrapper.eq("tag_id", tag);
            List<ThingTag> thingTagList = thingTagMapper.selectList(thingTagQueryWrapper);
            for (Thing thing : things) {
                for (ThingTag thingTag : thingTagList) {
                    if (thing.getId().equals(thingTag.getThingId())) {
                        tThings.add(thing);
                    }
                }
            }
            things.clear();
            things.addAll(tThings);
        }

        // 附加tag
        for (Thing thing : things) {
            QueryWrapper<ThingTag> thingTagQueryWrapper = new QueryWrapper<>();
            thingTagQueryWrapper.lambda().eq(ThingTag::getThingId, thing.getId());
            List<ThingTag> thingTags = thingTagMapper.selectList(thingTagQueryWrapper);
            List<Long> tags = thingTags.stream().map(ThingTag::getTagId).collect(Collectors.toList());
            thing.setTags(tags);
        }
        return things;
    }

    @Override
    public void createThing(Thing thing) {
        System.out.println(thing);
        thing.setCreateTime(String.valueOf(System.currentTimeMillis()));

        if (thing.getPv() == null) {
            thing.setPv("0");
        }
        if (thing.getScore() == null) {
            thing.setScore("0");
        }
        if (thing.getWishCount() == null) {
            thing.setWishCount("0");
        }
        //将thing保存至区块链bass平台
        String TxId = bassService.save(thing);

        thing.setTxId(TxId);

        mapper.insert(thing);
        // 更新tag
        setThingTags(thing);
    }

    @Override
    public void deleteThing(String id) {
        mapper.deleteById(id);
    }

    @Override
    public void updateThing(Thing thing) {

        // 更新tag
        setThingTags(thing);

        mapper.updateById(thing);
    }

    @Override
    public Thing getThingById(String id) {
        return mapper.selectById(id);
    }

    // 心愿数加1
    @Override
    public void addWishCount(String thingId) {
        Thing thing = mapper.selectById(thingId);
        thing.setWishCount(String.valueOf(Integer.parseInt(thing.getWishCount()) + 1));
        mapper.updateById(thing);
    }

    // 收藏数加1
    @Override
    public void addCollectCount(String thingId) {
        Thing thing = mapper.selectById(thingId);
        thing.setCollectCount(String.valueOf(Integer.parseInt(thing.getCollectCount()) + 1));
        mapper.updateById(thing);
    }

    @Override
    public void getById(String id) {
        String url = "http://59.110.93.238:30880/kapis/transactioncontroller.kubesphere.io/v1alpha1/transactions/getInDetail";

//        Map<String, String> map = new HashMap<>();
//        map.put("nameSpaceName", "trustedblockchain");
//        map.put("channelName", "trustedblockchainchannel");
//        map.put("TxId", "32e8b86411adbd6c32c345483e576c1b70f7bf5cef66dd1f4995109c3bfcbcf4");
//
//        ResponseEntity<Block> response = restTemplate.getForEntity(url, Block.class, map);

        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, String> map = new HashMap<>();

        map.put("namespace", "trustedblockchain");
        map.put("channelName", "trustedblockchainchannel");
        map.put("TxId", "32e8b86411adbd6c32c345483e576c1b70f7bf5cef66dd1f4995109c3bfcbcf4");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);


        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        System.out.println(response);
    }

    public void setThingTags(Thing thing) {
        // 删除tag
        Map<String, Object> map = new HashMap<>();
        map.put("thing_id", thing.getId());
        thingTagMapper.deleteByMap(map);
        // 新增tag
        if (thing.getTags() != null) {
            for (Long tag : thing.getTags()) {
                ThingTag thingTag = new ThingTag();
                thingTag.setThingId(thing.getId());
                thingTag.setTagId(tag);
                thingTagMapper.insert(thingTag);
            }
        }
    }

}
