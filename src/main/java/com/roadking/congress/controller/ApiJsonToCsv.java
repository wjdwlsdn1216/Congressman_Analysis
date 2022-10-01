package com.roadking.congress.controller;


import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiJsonToCsv {

    //OpenApi 에서 json 데이터를 받아 csv 파일로 바꿔서 저장
    public static void main(String[] args) throws Exception {
        StringBuilder sb2  = new StringBuilder();
        List<JSONArray> jsonArrays = new ArrayList<>();

        for (int d = 1; d <= 22; d++) {

            //pindex 조절
            for (int i = 1; i <= 2; i++) {

                final String requestUrl = "https://open.assembly.go.kr/portal/openapi/";
                String urlKey = "npffdutiapkzbfyvr";
                final String myKey = "43db7dc8640d4d6eb030d770bd5628d7";
                final String type = "json";
                final int pSize = 1000;
                int pindex = i;

                //OpenApi 주소에서 json 형식의 데이터를 받아 한줄씩읽어 StringBuilder에 저장
                StringBuilder sb = getOpenApiData(requestUrl, urlKey, myKey, type, pindex, pSize, d);

                try {
                    //json 형식으로 파싱하고 원하는 JsonArray 리턴
                    JSONArray rowData = parseJson(urlKey, sb);

                    //rowData 여러개를 List에 넣어야 함
                    jsonArrays.add(rowData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        File file = new File("/Users/anyone/Desktop/git/Congress_OpenApi_Hackathon/ok7.csv");
        for (JSONArray jsonArray : jsonArrays) {
            String csvString = CDL.toString(jsonArray);
            sb2.append(csvString);
        }

        FileUtils.writeStringToFile(file, sb2.toString());

    }

    private static StringBuilder getOpenApiData(String requestUrl, String urlKey, String myKey, String type, int pindex, int pSize, int d) throws Exception {
        int num = 100000 + d;
        StringBuilder urlBuilder = new StringBuilder(requestUrl + urlKey + "?" + "KEY=" + myKey +
                "&Type=" + type + "&pindex=" + pindex + "&pSize=" + pSize + "&UNIT_CD=" + num);

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb;
    }

    //org.json.JSONObject, JSONArray 이용
    private static JSONArray parseJson(String urlKey, StringBuilder sb) throws Exception {
        JSONObject jsonObject = new JSONObject(sb.toString());
        JSONArray jsonArray = (JSONArray) jsonObject.get(urlKey);
        JSONObject jsonObject1 = (JSONObject) jsonArray.get(1);
        JSONArray rowData = (JSONArray) jsonObject1.get("row");

        return rowData;
    }

}




