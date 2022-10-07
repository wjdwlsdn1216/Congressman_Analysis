package com.roadking.congress.controller;

import lombok.Data;

@Data
public class SearchDto {

    private Long id;
    private String name;
    private String bthDate;
    private String polyNm;

    public SearchDto(Long id, String name, String bthDate, String polyNm) {
        this.id = id;
        this.name = name;
        this.bthDate = bthDate;
        this.polyNm = polyNm;
    }
}
