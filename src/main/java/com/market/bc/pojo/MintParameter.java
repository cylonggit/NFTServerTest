package com.market.bc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.model.TransactionReceipt;

import java.math.BigInteger;
import java.util.Date;


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

    public TransactionReceipt getReceipt() {
        return receipt;
    }

    public void setReceipt(TransactionReceipt receipt) {
        this.receipt = receipt;
    }

    public BigInteger getTokenId() {
        return tokenId;
    }

    public void setTokenId(BigInteger tokenId) {
        this.tokenId = tokenId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getWatermarkCode() {
        return watermarkCode;
    }

    public void setWatermarkCode(String watermarkCode) {
        this.watermarkCode = watermarkCode;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
