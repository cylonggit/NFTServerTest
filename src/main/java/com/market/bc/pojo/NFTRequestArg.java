package com.market.bc.pojo;

import lombok.AllArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor

public class NFTRequestArg {
   long timestamp;
   BigInteger tokenId;
   String from;
   String to;
   String files;
   String auth;
   String pubkey;
   byte[] data;

   public long getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   public BigInteger getTokenId() {
      return tokenId;
   }

   public void setTokenId(BigInteger tokenId) {
      this.tokenId = tokenId;
   }

   public String getFrom() {
      return from;
   }

   public void setFrom(String from) {
      this.from = from;
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

   public byte[] getData() {
      return data;
   }

   public void setData(byte[] data) {
      this.data = data;
   }

   public boolean isValid(int minit){
      long res=System.currentTimeMillis()-timestamp;
        return (res<minit*60*1000);
   }



}
