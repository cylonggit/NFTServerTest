package com.market.bc.pojo;

import java.math.BigInteger;



public class NFTInfo {
   BigInteger tokenId;
   String owner;
   String files;
   String auth;
   String pubkey;
   byte[] data;



   public String getOwner() {
      return owner;
   }

   public void setOwner(String owner) {
      this.owner = owner;
   }

   public BigInteger getTokenId() {
      return tokenId;
   }

   public void setTokenId(BigInteger tokenId) {
      this.tokenId = tokenId;
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

   public byte[] getData() {
      return data;
   }

   public void setData(byte[] data) {
      this.data = data;
   }





}
