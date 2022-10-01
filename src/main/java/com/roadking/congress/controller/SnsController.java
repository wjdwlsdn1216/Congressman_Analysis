package com.roadking.congress.controller;

import com.roadking.congress.domain.Congressman;
import com.roadking.congress.domain.Sns;
import com.roadking.congress.service.SnsService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@RequiredArgsConstructor
public class SnsController {

    private final SnsService snsService;

    //출처: 역대 국회의원 인적사항 (국회 OpenApi)
    //json 데이터 congressman table에 저장
    @GetMapping("/api/load/sns")
    public void apiLoad() throws Exception {
        StringBuilder sb2 = new StringBuilder();



        final String requestUrl = "https://open.assembly.go.kr/portal/openapi/";
        String urlKey = "negnlnyvatsjwocar";
        final String myKey = "43db7dc8640d4d6eb030d770bd5628d7";
        final String type = "json";
        final int pSize = 1000;
        int pindex = 1;

        //OpenApi 주소에서 json 형식의 데이터를 받아 한줄씩읽어 StringBuilder에 저장
        StringBuilder sb = getOpenApiData(requestUrl, urlKey, myKey, type, pindex, pSize);

        try {
            //json 형식으로 파싱하고 원하는 JsonArray 리턴
            JSONArray jsonArray = parseJson(urlKey, sb);
            System.out.println("jsonArray.length() = " + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                String name = jsonObject.get("HG_NM").toString();
                String twitterUrl = jsonObject.get("T_URL").toString();
                String facebookUrl = jsonObject.get("F_URL").toString();
                String utubeUrl = jsonObject.get("Y_URL").toString();
                String blogUrl = jsonObject.get("B_URL").toString();
                String monaCd = jsonObject.get("MONA_CD").toString();

                Sns sns = new Sns(name, twitterUrl, facebookUrl, utubeUrl, blogUrl, monaCd);
                snsService.save(sns);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("congressman 데이터 저장완료!");
    }

    private static StringBuilder getOpenApiData(String requestUrl, String urlKey, String myKey, String type, int pindex, int pSize) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(requestUrl + urlKey + "?" + "KEY=" + myKey +
                "&Type=" + type + "&pindex=" + pindex + "&pSize=" + pSize);

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
