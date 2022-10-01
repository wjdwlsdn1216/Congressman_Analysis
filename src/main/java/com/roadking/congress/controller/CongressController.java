package com.roadking.congress.controller;

import com.roadking.congress.domain.Congressman;
import com.roadking.congress.domain.Sns;
import com.roadking.congress.repository.congressman.dto.CongressmanFlatDto;
import com.roadking.congress.service.CongressService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@RequiredArgsConstructor
public class CongressController {

    private final CongressService congressService;

    //출처: 역대 국회의원 인적사항 (국회 OpenApi)
    //json 데이터 congressman table에 저장
    @GetMapping("/api/load/congressman")
    public void apiLoad() throws Exception {
        StringBuilder sb2 = new StringBuilder();


        final String requestUrl = "https://open.assembly.go.kr/portal/openapi/";
        String urlKey = "nwvrqwxyaytdsfvhu";
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
                String hjName = jsonObject.get("HJ_NM").toString();
                String enName = jsonObject.get("ENG_NM").toString();
                String bthGbnNm = jsonObject.get("BTH_GBN_NM").toString();
                String bthDate = jsonObject.get("BTH_DATE").toString();
                String jobResNm = jsonObject.get("JOB_RES_NM").toString();
                String polyNm = jsonObject.get("POLY_NM").toString();
                String origNm = jsonObject.get("ORIG_NM").toString();
                String electGbnNm = jsonObject.get("ELECT_GBN_NM").toString();
                String cmitNm = jsonObject.get("CMIT_NM").toString();
                String cmits = jsonObject.get("CMITS").toString();
                String reeleGbnNm = jsonObject.get("REELE_GBN_NM").toString();
                String units = jsonObject.get("UNITS").toString();
                String sex = jsonObject.get("SEX_GBN_NM").toString();
                String telNo = jsonObject.get("TEL_NO").toString();
                String email = jsonObject.get("E_MAIL").toString();
                String homepage = jsonObject.get("HOMEPAGE").toString();
                String staff = jsonObject.get("STAFF").toString();
                String secretary = jsonObject.get("SECRETARY").toString();
                String secretary2 = jsonObject.get("SECRETARY2").toString();
                String monaCd = jsonObject.get("MONA_CD").toString();
                String memTitle = jsonObject.get("MEM_TITLE").toString();
                String assemAddr = jsonObject.get("ASSEM_ADDR").toString();

                Congressman congressman = new Congressman(name, hjName, enName, bthGbnNm, bthDate, jobResNm, polyNm, origNm, electGbnNm, cmitNm, cmits, reeleGbnNm, units, sex, telNo, email, homepage, staff, secretary, secretary2, monaCd, memTitle, assemAddr);


                congressService.save(congressman);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("congressman 데이터 저장완료!");
    }

    //의원 상세보기
    @GetMapping("/congressman/detail")
    public String detail(@RequestParam Long congressmanId, Model model) {
        Congressman congressman = congressService.findOne(congressmanId);

        //\r 로 저장되어있는 문자를 <br>로 바꿔서 화면에는 줄바꿈해서 나오게 수정
        String replaced = congressman.getMemTitle().replace("\r", "<br>");
        congressman.setMemTitle(replaced);
        model.addAttribute("congressman", congressman);
        return "congressman/congressmanDetail";
    }

    //의원 닮은꼴 뷰
    @GetMapping("/congressman/similar")
    public String similar(@RequestParam Long congressmanId, Model model) {
        Congressman congressman = congressService.findOne(congressmanId);
        model.addAttribute("congressman", congressman);
        return "congressman/congressmanSimilar";
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
