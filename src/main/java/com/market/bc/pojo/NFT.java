package com.market.bc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

//nft全部信息
@Entity
@Table(name = "tb_nft")
@Data
//TODO
//@Where(clause = "status!='deleted'")
public class NFT implements Serializable {
    private String uri;
    private String category;
    private String title;
    // 作品信息
    private String nft_title; // 作品名称
    private String nft_category; // 作品类型
    private String nft_creation_nature; // 创作性质
    private String nft_creation_finish_time; // 创作完成日期
    private String nft_creation_finish_place; // 创作完成地点
    private String nft_publish_status; // 发表状态
    private String nft_first_publish_time; // 首次发表日期

    private String nft_author_name; // 作者姓名 （书画专有）
    private String nft_author_signature; // 作者署名 （书画专有）

    private String nft_director; // 导演 （电影专有）
    private String nft_lead; // 主演 （电影专有）
    private String nft_screenwriter; // 编剧 （电影专有）

    private String nft_dci_code; // DCI码

    // 著作权人信息
    private String nft_copyright_holder; // 著作权人
    private String nft_copyright_holder_category; // 著作权人类别
    private String nft_certificate_category; // 证件类型
    private String nft_certificate_number; // 证件号码
    private String nft_country; // 国别（地区）
    private String nft_province; // 省份
    private String nft_city; // 城市
    private String nft_enterprise_category; // 企业类型

    // 权利状况信息
    private String nft_right_acquisition_mode; // 权利取得方式
    private String nft_right_ascription_mode; // 权利归属方式
    private String nft_right_scope; // 权利范围

    // 登记证书信息
    private String nft_registration_number; // 登记号
    private String nft_issue_date; // 发证日期

    private String judgeID; // 认证机构

    @Id
    private String nftID;//ID

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    private String description;

    private String watermark_uri;

    private String status = "draft";
    private String creatorID;
    private Integer visits = 0;
    private Integer num = 1;
    private Integer thumbup = 0;
    private Integer favorite = 0;
    private String labels;
    private String reviewer;


    private String nft_hash;
    private String nft_watermark;

    @Transient
    private String creatorUsername;

    private Boolean isApproved = false;
}
