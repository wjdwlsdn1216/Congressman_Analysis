package com.roadking.congress.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Congressman {
    //출처: 역대 국회의원 현황(국회 OpenApi)
    //필드명과 OpenApi key 이름이 다른것은 주석에 표시

    @Id @GeneratedValue
    private Long id;

    private String name;
    //BIRTH 생년월일
    private String birthDate;
    //POSI 출생지
    private String birthplace;
    //DAE 소속정당
    private String party;
    //HAK 학력 및 경력
    private String academicBackground;
    //종교 및 취미
    private String hobby;
    //기타정보(사망일)
    private String dead;
}
