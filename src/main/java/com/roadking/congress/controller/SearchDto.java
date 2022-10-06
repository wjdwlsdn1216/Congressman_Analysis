package com.roadking.congress.controller;

import lombok.Data;

@Data
public class SearchDto {

    private Long id;
    private String name;
    private String bthGbnNm;

    public SearchDto(Long id, String name, String bthGbnNm) {
        this.id = id;
        this.name = name;
        this.bthGbnNm = bthGbnNm;
    }
}
