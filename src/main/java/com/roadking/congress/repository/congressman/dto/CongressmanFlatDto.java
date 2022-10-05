package com.roadking.congress.repository.congressman.dto;

import lombok.Data;

@Data
public class CongressmanFlatDto {

    //HG_NM	이름
    private String name;
    //HJ_NM	한자명
    private String hjName;
    //ENG_NM	영문명칭
    private String enName;
    //BTH_GBN_NM	음양력
    private String bthGbnNm;
    //BTH_DATE	생년월일
    private String bthDate;
    //JOB_RES_NM 직책명
    private String jobResNm;
    //POLY_NM	정당명
    private String polyNm;
    //ORIG_NM	선거구
    private String origNm;
    //ELECT_GBN_NM	선거구구분
    private String electGbnNm;
    //CMIT_NM	대표위원회
    private String cmitNm;
    //CMITS	소속위원회목록
    private String cmits;
    //REELE_GBN_NM	재선
    private String reeleGbnNm;
    //UNITS	당선
    private String units;
    //SEX_GBN_NM	성별
    private String sex;
    //TEL_NO	전화번호
    private String telNo;
    //E_MAIL	이메일
    private String email;
    //HOMEPAGE	홈페이지
    private String homepage;
    //STAFF	보좌관
    private String staff;
    //SECRETARY	비서관
    private String secretary;
    //SECRETARY2	비서
    private String secretary2;
    //MONA_CD	국회의원코드
    private String monaCd;
    //MEM_TITLE	약력
    private String memTitle;
    //ASSEM_ADDR	사무실호실
    private String assemAddr;


    //T_URL 트위터 URL
    private String twitterUrl;
    //F_URL 페이스북 URL
    private String facebookUrl;
    //Y_URL 유튜브 URL
    private String utubeUrl;
    //B_URL 블로그 URL
    private String blogUrl;

    public CongressmanFlatDto(String name, String hjName, String enName, String bthGbnNm, String bthDate, String jobResNm, String polyNm, String origNm, String electGbnNm, String cmitNm, String cmits, String reeleGbnNm, String units, String sex, String telNo, String email, String homepage, String staff, String secretary, String secretary2, String monaCd, String memTitle, String assemAddr, String twitterUrl, String facebookUrl, String utubeUrl, String blogUrl) {
        this.name = name;
        this.hjName = hjName;
        this.enName = enName;
        this.bthGbnNm = bthGbnNm;
        this.bthDate = bthDate;
        this.jobResNm = jobResNm;
        this.polyNm = polyNm;
        this.origNm = origNm;
        this.electGbnNm = electGbnNm;
        this.cmitNm = cmitNm;
        this.cmits = cmits;
        this.reeleGbnNm = reeleGbnNm;
        this.units = units;
        this.sex = sex;
        this.telNo = telNo;
        this.email = email;
        this.homepage = homepage;
        this.staff = staff;
        this.secretary = secretary;
        this.secretary2 = secretary2;
        this.monaCd = monaCd;
        this.memTitle = memTitle;
        this.assemAddr = assemAddr;
        this.twitterUrl = twitterUrl;
        this.facebookUrl = facebookUrl;
        this.utubeUrl = utubeUrl;
        this.blogUrl = blogUrl;
    }
}
