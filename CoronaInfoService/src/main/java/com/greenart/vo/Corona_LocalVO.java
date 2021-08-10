package com.greenart.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class Corona_LocalVO {
    private Integer seq;
    private Date createDt;
    private Integer deathCnt;
    private Integer defCnt;
    private String gubun;
    private String gubunCn;
    private String gubunEn;
    private Integer incDec;
    private Integer isolClearCnt;
    private Integer isolIngCnt;
    private Integer localOccCnt;
    private Integer overFlowCnt;
}
