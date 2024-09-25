package com.market.bc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_transfer")
public class NFTTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transferID;
    private String nftID;
    private String transferType;
    private String transferHash;
    private String transferFrom;
    private String transferTo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transferTime;
    private String fromUsername;
    private String toUsername;
    private String watermarkCode;

    public Integer getTransferID() {
        return transferID;
    }

    public void setTransferID(Integer transferID) {
        this.transferID = transferID;
    }

    public String getNftID() {
        return nftID;
    }

    public void setNftID(String nftID) {
        this.nftID = nftID;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferHash() {
        return transferHash;
    }

    public void setTransferHash(String transferHash) {
        this.transferHash = transferHash;
    }

    public String getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(String transferFrom) {
        this.transferFrom = transferFrom;
    }

    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
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
}
