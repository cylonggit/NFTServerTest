package com.market.bc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_transfer")
@Data
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
}
