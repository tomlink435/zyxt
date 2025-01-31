package com.gk.study.controller;

import com.gk.study.common.APIResponse;
import com.gk.study.common.ResponeCode;
import com.gk.study.entity.Thing;
import com.gk.study.permission.Access;
import com.gk.study.permission.AccessLevel;
import com.gk.study.pojo.VO.BlockTransactionInfoVO;
import com.gk.study.service.BannerService;
import com.gk.study.service.BassService;
import com.gk.study.service.ThingService;
import com.gk.study.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/thing")
@Slf4j
@CrossOrigin
public class ThingController {
    @Autowired
    private RestTemplate restTemplate;

    private final static Logger logger = LoggerFactory.getLogger(ThingController.class);

    @Autowired
    ThingService service;

    @Autowired
    private BassService bassService;


    @Autowired
    private AliOssUtil aliOssUtil;

    @Value("${File.uploadPath}")
    private String uploadPath;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public APIResponse list(String keyword, String sort, String c, String tag){
        List<Thing> list =  service.getThingList(keyword, sort, c, tag);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public APIResponse detail(String id){
        Thing thing =  service.getThingById(id);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", thing);
    }

    /**
     * 上传数据
     * @param thing
     * @return
     * @throws IOException
     */
    @Access(level = AccessLevel.ADMIN)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Transactional
    public APIResponse create(Thing thing) throws IOException {
        log.info("新增数据:{}", thing);
        String url = uploadImage(thing.getImageFile());
        if(!StringUtils.isEmpty(url)) {
            thing.cover = url;
        }
        service.createThing(thing);
        return new APIResponse(ResponeCode.SUCCESS, "创建成功");
    }

    @Access(level = AccessLevel.ADMIN)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public APIResponse delete(String ids){
        System.out.println("ids===" + ids);
        // 批量删除
        String[] arr = ids.split(",");
        for (String id : arr) {
            service.deleteThing(id);
        }
        return new APIResponse(ResponeCode.SUCCESS, "删除成功");
    }

    /**
     * 获取交易数据
     * @param id
     * @return
     */
    @GetMapping("/getByTxId")
    public APIResponse getById(String id){
        log.info("获取上链数据:{}", id);
        BlockTransactionInfoVO vo = bassService.getDetailByTxId(id);
        return new APIResponse(ResponeCode.SUCCESS, "证书生成成功", vo);
    }

    /**
     * 更新数据
     * @param thing
     * @return
     * @throws IOException
     */
    @Access(level = AccessLevel.ADMIN)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public APIResponse update(Thing thing) throws IOException {
        System.out.println(thing);
        String url = uploadImage(thing.getImageFile());
        if(!StringUtils.isEmpty(url)) {
            thing.cover = url;
        }
        service.updateThing(thing);
        return new APIResponse(ResponeCode.SUCCESS, "更新成功");
    }

    public String saveThing(Thing thing) throws IOException {
        MultipartFile file = thing.getImageFile();
        String newFileName = null;
        if(file !=null && !file.isEmpty()) {
            // 存文件
            String oldFileName = file.getOriginalFilename();
            String randomStr = UUID.randomUUID().toString();
            newFileName = randomStr + oldFileName.substring(oldFileName.lastIndexOf("."));
//            String filePath = uploadPath + File.separator + "image" + File.separator + newFileName;
            String filePath = uploadPath + "/image" + '/'+ newFileName;  //mac用户
            File destFile = new File(filePath);
            if(!destFile.getParentFile().exists()){
                destFile.getParentFile().mkdirs();
            }
            file.transferTo(destFile);
        }
        if(!StringUtils.isEmpty(newFileName)) {
            thing.cover = newFileName;
        }
        return newFileName;
    }

    //上传图片
    public String uploadImage(MultipartFile file){
        log.info("上传图片:{}", file);

        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名后缀 img.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;
            //文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
