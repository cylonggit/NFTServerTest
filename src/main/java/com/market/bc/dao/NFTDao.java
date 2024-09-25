package com.market.bc.dao;

import com.market.bc.pojo.NFT;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface NFTDao extends JpaRepository<NFT, String>, JpaSpecificationExecutor<NFT> {

    NFT findNFTByNftID(String id);



    Page<NFT> findNFTByCreatorID(String id, Pageable pageable);

    @Query(value = "SELECT category FROM tb_nft WHERE nftID=?", nativeQuery = true)
    String findCategoryByNftID(String id);

    @Modifying
    @Query(value = "UPDATE tb_nft SET status=?2, reviewer=?3 WHERE nftID=?1", nativeQuery = true)
    void updateState(String id, String status, String reviewer);

    @Modifying
    @Query(value = "UPDATE tb_nft SET thumbup=thumbup+1 WHERE nftID=?", nativeQuery = true)
    void addThumbup(String id);

    @Modifying
    @Query(value = "UPDATE tb_nft SET visits=visits+1 WHERE nftID=?", nativeQuery = true)
    void addVisits(String id);

    @Modifying
    @Query(value = "UPDATE tb_nft SET favorite=favorite+1 WHERE nftID=?", nativeQuery = true)
    void addFavorite(String id);

    @Modifying
    @Query(value = "UPDATE tb_nft SET favorite=favorite-1 WHERE nftID=?", nativeQuery = true)
    void reduceFavorite(String nftID);

//    @Query(value = "SELECT n FROM NFT n,NFTLabel nl WHERE nl.labelID=:labelID AND n.nftID=nl.nftID")
//    Page<NFT> findNFTByLabelID(@Param("labelID") String labelID, Pageable pageable);
//
//    @Query(value = "SELECT n FROM Label l, NFTLabel nl, NFT n WHERE nl.labelID=l.labelID AND l.labelName=:labelName AND n.nftID=nl.nftID")
//    Page<NFT> findNFTByLabelName(@Param("labelName") String labelName, Pageable pageable);
//
//    @Query(value = "SELECT new com.market.nft.pojo.NFTInfo(n.nftID,n.title,n.uri) FROM NFT n where nftID=?1")
//    NFTInfo findNFTInfoByNFTID(String id);

    @Modifying
    @Query(value = "UPDATE tb_nft SET status='deleted' WHERE nftID=?", nativeQuery = true)
    void deleteByNftID(String id);

    Page<NFT> findNFTByNftIDIn(List<String> idList,Pageable pageable);

    @Query(value = "SELECT * FROM tb_nft WHERE category='image' ORDER BY rand() LIMIT ?", nativeQuery = true)
    List<NFT> findRandN(int n);

    @Query(value = "SELECT nft_hash FROM tb_nft ORDER BY ABS(CONV(nft_hash, 16, 10) - CONV(?1, 16, 10)) ASC LIMIT 1", nativeQuery = true)
    Optional<String> findClosestHexValue(String hexValue);
}
