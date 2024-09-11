package com.market.bc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.model.TransactionReceipt;

import java.math.BigInteger;
import java.util.Date;

@Data
public class TransferParameter {
    TransactionReceipt receipt;
    BigInteger tokenId;
    String from;
    String to;
    String pubkey;
    String transactionHash;
    String fromUsername;
    String toUsername;
    String watermarkCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date time;
    String type = "transfer";
}
