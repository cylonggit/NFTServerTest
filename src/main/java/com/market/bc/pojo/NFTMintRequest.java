package com.market.bc.pojo;

public class NFTMintRequest {

    String tokenId;
    String address;
    String hash;
    String uri;
    String watermarkCode;

    public String getWatermarkCode() {
        return watermarkCode;
    }

    public void setWatermarkCode(String watermarkCode) {
        this.watermarkCode = watermarkCode;
    }

    public NFTMintRequest(){}

    public NFTMintRequest(String tokenId, String address, String hash, String uri) {
        this.tokenId = tokenId;
        this.address = address;
        this.hash = hash;
        this.uri = uri;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
