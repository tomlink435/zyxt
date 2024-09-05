package com.gk.study.service.impl;


import com.gk.study.entity.Thing;
import com.gk.study.pojo.VO.BlockTransactionInfoVO;
import com.gk.study.pojo.VO.BlockTransactionVO;
import com.gk.study.service.BassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class BassServiceImpl implements BassService {

    @Resource
    private RestTemplate restTemplate;
    /**
     * 将thing上链
     * @param thing
     * @return
     */
    @Override
    public String save(Thing thing) {
        String url = "http://59.110.93.238:30880/kapis/fabric.kubesphere.io/v1alpha1/evidences/invoke";

        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("args", "setValue;key;title;" + thing.getTitle());
        map.add("keyNumber", "1");
        map.add("extras", "1");
        map.add("namespace", "trustedblockchain");
        map.add("channelName", "trustedblockchainchannel");
        map.add("orgName", "trustedblockchainorg");
        map.add("evidenceName", "trustedblockchain-trustedblockchain.trustedblockchainchannel.evidence");
        map.add("userName", "admin");
        map.add("isEncrypt", "0");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<BlockTransactionVO> response = restTemplate.postForEntity(url, request, BlockTransactionVO.class);

        System.out.println(response);

        return response.getBody().getMetadata().getName();
    }

    @Override
    public BlockTransactionInfoVO getDetailByTxId(String TxId) {
        String url = "http://59.110.93.238:30880/kapis/blockcontroller.kubesphere.io/v1alpha1/GetBlockByTxID?nameSpaceName={nameSpaceName}&channelName={channelName}&TxID={TxId}";

        Map<String, String> map = new HashMap<>();
        map.put("nameSpaceName", "trustedblockchain");
        map.put("channelName", "trustedblockchainchannel");
        map.put("TxId", TxId);

        ResponseEntity<BlockTransactionInfoVO> response = restTemplate.getForEntity(url, BlockTransactionInfoVO.class, map);

        return response.getBody();

    }
}
