package com.gk.study.service;


import com.gk.study.entity.Thing;
import com.gk.study.pojo.VO.BlockTransactionInfoVO;
import com.gk.study.pojo.VO.BlockTransactionVO;

/**
 * 区块链平台提供服务
 */
public interface BassService {
    /**
     * 将thing上链
     * @param thing
     * @return
     */
    String save(Thing thing);

    //根据txId获取数据详情
    BlockTransactionInfoVO getDetailByTxId(String TxId);
}
