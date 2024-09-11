package com.market.bc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.model.TransactionReceipt;

import java.math.BigInteger;
import java.util.Date;

@Data
public class MintParameter {
    TransactionReceipt receipt;
    BigInteger tokenId;
    String to;
    String files;
    String auth;
    String pubkey;
    String transactionHash;
    String toUsername;
    String watermarkCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date time;
    String type = "mint";
}
