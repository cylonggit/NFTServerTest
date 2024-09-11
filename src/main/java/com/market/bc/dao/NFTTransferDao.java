package com.market.bc.dao;

import com.market.bc.pojo.NFTTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NFTTransferDao extends JpaRepository<NFTTransfer, Integer> {
    List<NFTTransfer> findNFTTransfersByNftID(String nftID);
}
