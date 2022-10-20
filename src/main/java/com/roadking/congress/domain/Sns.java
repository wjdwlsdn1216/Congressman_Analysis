package com.roadking.congress.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Sns {
    //출처: 국회의원 SNS정보 (국회 OpenApi)

    @Id @GeneratedValue
    @Column(name = "sns_id")
    private Long id;

    @OneToOne(mappedBy = "sns", fetch = FetchType.LAZY)
    private Congressman congressman;

    public Sns(String name, String twitterUrl, String facebookUrl, String utubeUrl, String blogUrl, String monaCd) {
        this.name = name;
        this.twitterUrl = twitterUrl;
        this.facebookUrl = facebookUrl;
        this.utubeUrl = utubeUrl;
        this.blogUrl = blogUrl;
        this.monaCd = monaCd;
    }

    protected Sns() {
    }

    //HG_NM
    private String name;
    //T_URL 트위터 URL
    private String twitterUrl;
    //F_URL 페이스북 URL
    private String facebookUrl;
    //Y_URL 유튜브 URL
    private String utubeUrl;
    //B_URL 블로그 URL
    private String blogUrl;
    //MONA_CD 국회의원코드
    private String monaCd;


}
