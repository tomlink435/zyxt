package com.gk.study.pojo.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class Block {
    @JsonProperty("Number")
    private Integer number;

    @JsonProperty("TransactionsNum")
    private Integer transactionsNum;

    @JsonProperty("CurrentBlockHash")
    private String currentBlockHash;

    @JsonProperty("PreviousBlockHash")
    private String previousBlockHash;

    @JsonProperty("DataHash")
    private String dataHash;

    @JsonProperty("Transactions")
    private String transactions;
}
