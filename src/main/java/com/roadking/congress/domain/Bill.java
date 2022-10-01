package com.roadking.congress.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter @Setter
public class Bill {

    @Id @GeneratedValue
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "congressman_id")
//    private Congressman congressman;

    //BILL_ID 의안ID
    private String billId;
    //BILL_NO 의안번호
    private String billNo;
    //BILL_NAME 법률안명
    private String name;
    //COMMITTEE 소관위원회
    private String committee;
    //PROPOSE_DT 제안일
    private String proposeDate;
    //PROC_RESULT 처리상태
    private String status;
    //AGE 대수
    private String congressAge;
    //DETAIL_LINK 상세페이지
    private String detailLink;
    //PROPOSER 제안자
    private String proposer;
    //MEMBER_LIST 제안자목록링크
    private String memberList;
    //RST_PROPOSER 대표발의자
    private String mainProposer;
    //PUBL_PROPOSER 공동발의자
    private String publProposer;
}
