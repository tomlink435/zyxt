package com.gk.study.pojo.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockTransactionInfoVO {
    @JsonProperty("Number")
    private int number;

    @JsonProperty("TransactionsNum")
    private int transactionsNum;

    @JsonProperty("CurrentBlockHash")
    private String currentBlockHash;

    @JsonProperty("PreviousBlockHash")
    private String previousBlockHash;

    @JsonProperty("DataHash")
    private String dataHash;

    @JsonProperty("Transactions")
    private List<Transaction> transactions;

    // getters and setters
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Transaction {
    @JsonProperty("BlockNumber")
    private int blockNumber;

    @JsonProperty("No")
    private int no;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("ChaincodeID")
    private String chaincodeId;

    @JsonProperty("ChaincodeVersion")
    private String chaincodeVersion;

    @JsonProperty("MspId")
    private String mspId;

    @JsonProperty("Payload")
    private List<Object> payload;

    @JsonProperty("TxId")
    private String txId;

    @JsonProperty("Type")
    private int type;

    @JsonProperty("Channel")
    private String channel;

    @JsonProperty("Timestamp")
    private String timestamp;

    // getters and setters
}