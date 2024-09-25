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
//@Data
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNft_title() {
        return nft_title;
    }

    public void setNft_title(String nft_title) {
        this.nft_title = nft_title;
    }

    public String getNft_category() {
        return nft_category;
    }

    public void setNft_category(String nft_category) {
        this.nft_category = nft_category;
    }

    public String getNft_creation_nature() {
        return nft_creation_nature;
    }

    public void setNft_creation_nature(String nft_creation_nature) {
        this.nft_creation_nature = nft_creation_nature;
    }

    public String getNft_creation_finish_time() {
        return nft_creation_finish_time;
    }

    public void setNft_creation_finish_time(String nft_creation_finish_time) {
        this.nft_creation_finish_time = nft_creation_finish_time;
    }

    public String getNft_creation_finish_place() {
        return nft_creation_finish_place;
    }

    public void setNft_creation_finish_place(String nft_creation_finish_place) {
        this.nft_creation_finish_place = nft_creation_finish_place;
    }

    public String getNft_publish_status() {
        return nft_publish_status;
    }

    public void setNft_publish_status(String nft_publish_status) {
        this.nft_publish_status = nft_publish_status;
    }

    public String getNft_first_publish_time() {
        return nft_first_publish_time;
    }

    public void setNft_first_publish_time(String nft_first_publish_time) {
        this.nft_first_publish_time = nft_first_publish_time;
    }

    public String getNft_author_name() {
        return nft_author_name;
    }

    public void setNft_author_name(String nft_author_name) {
        this.nft_author_name = nft_author_name;
    }

    public String getNft_author_signature() {
        return nft_author_signature;
    }

    public void setNft_author_signature(String nft_author_signature) {
        this.nft_author_signature = nft_author_signature;
    }

    public String getNft_director() {
        return nft_director;
    }

    public void setNft_director(String nft_director) {
        this.nft_director = nft_director;
    }

    public String getNft_lead() {
        return nft_lead;
    }

    public void setNft_lead(String nft_lead) {
        this.nft_lead = nft_lead;
    }

    public String getNft_screenwriter() {
        return nft_screenwriter;
    }

    public void setNft_screenwriter(String nft_screenwriter) {
        this.nft_screenwriter = nft_screenwriter;
    }

    public String getNft_dci_code() {
        return nft_dci_code;
    }

    public void setNft_dci_code(String nft_dci_code) {
        this.nft_dci_code = nft_dci_code;
    }

    public String getNft_copyright_holder() {
        return nft_copyright_holder;
    }

    public void setNft_copyright_holder(String nft_copyright_holder) {
        this.nft_copyright_holder = nft_copyright_holder;
    }

    public String getNft_copyright_holder_category() {
        return nft_copyright_holder_category;
    }

    public void setNft_copyright_holder_category(String nft_copyright_holder_category) {
        this.nft_copyright_holder_category = nft_copyright_holder_category;
    }

    public String getNft_certificate_category() {
        return nft_certificate_category;
    }

    public void setNft_certificate_category(String nft_certificate_category) {
        this.nft_certificate_category = nft_certificate_category;
    }

    public String getNft_certificate_number() {
        return nft_certificate_number;
    }

    public void setNft_certificate_number(String nft_certificate_number) {
        this.nft_certificate_number = nft_certificate_number;
    }

    public String getNft_country() {
        return nft_country;
    }

    public void setNft_country(String nft_country) {
        this.nft_country = nft_country;
    }

    public String getNft_province() {
        return nft_province;
    }

    public void setNft_province(String nft_province) {
        this.nft_province = nft_province;
    }

    public String getNft_city() {
        return nft_city;
    }

    public void setNft_city(String nft_city) {
        this.nft_city = nft_city;
    }

    public String getNft_enterprise_category() {
        return nft_enterprise_category;
    }

    public void setNft_enterprise_category(String nft_enterprise_category) {
        this.nft_enterprise_category = nft_enterprise_category;
    }

    public String getNft_right_acquisition_mode() {
        return nft_right_acquisition_mode;
    }

    public void setNft_right_acquisition_mode(String nft_right_acquisition_mode) {
        this.nft_right_acquisition_mode = nft_right_acquisition_mode;
    }

    public String getNft_right_ascription_mode() {
        return nft_right_ascription_mode;
    }

    public void setNft_right_ascription_mode(String nft_right_ascription_mode) {
        this.nft_right_ascription_mode = nft_right_ascription_mode;
    }

    public String getNft_right_scope() {
        return nft_right_scope;
    }

    public void setNft_right_scope(String nft_right_scope) {
        this.nft_right_scope = nft_right_scope;
    }

    public String getNft_registration_number() {
        return nft_registration_number;
    }

    public void setNft_registration_number(String nft_registration_number) {
        this.nft_registration_number = nft_registration_number;
    }

    public String getNft_issue_date() {
        return nft_issue_date;
    }

    public void setNft_issue_date(String nft_issue_date) {
        this.nft_issue_date = nft_issue_date;
    }

    public String getJudgeID() {
        return judgeID;
    }

    public void setJudgeID(String judgeID) {
        this.judgeID = judgeID;
    }

    public String getNftID() {
        return nftID;
    }

    public void setNftID(String nftID) {
        this.nftID = nftID;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWatermark_uri() {
        return watermark_uri;
    }

    public void setWatermark_uri(String watermark_uri) {
        this.watermark_uri = watermark_uri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getThumbup() {
        return thumbup;
    }

    public void setThumbup(Integer thumbup) {
        this.thumbup = thumbup;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getNft_hash() {
        return nft_hash;
    }

    public void setNft_hash(String nft_hash) {
        this.nft_hash = nft_hash;
    }

    public String getNft_watermark() {
        return nft_watermark;
    }

    public void setNft_watermark(String nft_watermark) {
        this.nft_watermark = nft_watermark;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }
}
