package com.market.bc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 实体类
 *
 * @author Administrator
 */

public class User implements Serializable {


    private String userID;
    private String mobile;
    private String password;
    private String username;
    private String email;
    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastDate;
    private String ethAddress;
    private String status = "normal";
    private String reviewer;
    private Integer fanscount=0;
    private Integer followcount=0;

    private String accountAddress;
    private String hexPrivateKey;
    private String hexPublicKey;

    private String nftRSAPrivateKey;

    private String nftRSAPublicKey;

    public String getNftRSAPrivateKey() {
        return nftRSAPrivateKey;
    }

    public void setNftRSAPrivateKey(String nftRSAPrivateKey) {
        this.nftRSAPrivateKey = nftRSAPrivateKey;
    }

    public String getNftRSAPublicKey() {
        return nftRSAPublicKey;
    }

    public void setNftRSAPublicKey(String nftRSAPublicKey) {
        this.nftRSAPublicKey = nftRSAPublicKey;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public String getHexPrivateKey() {
        return hexPrivateKey;
    }

    public void setHexPrivateKey(String hexPrivateKey) {
        this.hexPrivateKey = hexPrivateKey;
    }

    public String getHexPublicKey() {
        return hexPublicKey;
    }

    public void setHexPublicKey(String hexPublicKey) {
        this.hexPublicKey = hexPublicKey;
    }

    public Integer getFanscount() {
        return fanscount;
    }

    public void setFanscount(Integer fanscount) {
        this.fanscount = fanscount;
    }

    public Integer getFollowcount() {
        return followcount;
    }

    public void setFollowcount(Integer followcount) {
        this.followcount = followcount;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }

    public User(Map map) {
        this.username = (String) map.get("username");
        this.hexPrivateKey = (String) map.get("hexPrivateKey");
        this.nftRSAPublicKey = (String) map.get("nftRSAPublicKey");
    }
}
