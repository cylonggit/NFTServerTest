package com.market.bc.pojo;

import lombok.AllArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor

public class FileRequestArg {
   long timestamp;
   String tokenId;
   String fileId;



   public long getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   public String getTokenId() {
      return tokenId;
   }

   public void setTokenId(String tokenId) {
      this.tokenId = tokenId;
   }

   public String getFileId() {
      return fileId;
   }

   public void setFileId(String fileId) {
      this.fileId = fileId;
   }

   public boolean isValid(int minit){
      long res=System.currentTimeMillis()-timestamp;
        return (res<minit*60*1000);
   }



}
