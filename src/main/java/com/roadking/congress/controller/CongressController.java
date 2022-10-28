package com.roadking.congress.controller;

import com.roadking.congress.domain.Congressman;
import com.roadking.congress.domain.Sns;
import com.roadking.congress.service.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CongressController {

    private final CongressService congressService;
    private final SnsService snsService;
    private final FileService fileService;
    private final HttpFileService httpFileService;
    private final HttpMultifileService httpMultifileService;
    private final OkHttpService okHttpService;

    //출처: 국회의원 인적사항 (국회 OpenApi)
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

                //update column sns_id in congressman table
                Sns oneSns = snsService.findSnsByMonaCd(monaCd);
                Long snsId = oneSns.getId();
                congressService.updateSnsId(snsId, monaCd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("congressman 데이터 저장완료!");
    }

    //의원 상세보기
    @GetMapping("/congressman/detail")
    public String detail(@RequestParam(required = false) String name, @RequestParam(required = false) Long id, Model model) {
        Congressman congressman;
        if (id != null) {
            congressman = congressService.findOne(id);
        } else {
            //resultPerson 이름으로 국회의원 엔티티 불러오기
            congressman = congressService.findByName(name);
        }

        //조회수 1상승
        congressService.updateView(congressman);

        //\r 로 저장되어있는 문자를 <br>로 바꿔서 화면에는 줄바꿈해서 나오게 수정
        congressman.replaceMem();

        model.addAttribute("congressman", congressman);
        model.addAttribute("currentPage", "detail");
        return "congressman/congressmanDetail";

    }

    @ExceptionHandler
    public void handleEmptyResultDataAccessException(EmptyResultDataAccessException exception, Model model) {
        model.addAttribute("exception", exception);
    }


    //    //Okhttp
    @PostMapping("/congressman/similar")
    public String similar(@RequestParam MultipartFile multipartFile, Model model) throws Exception {
        String result = okHttpService.client(multipartFile);
        JSONObject jsonObject = new JSONObject(result);
        String resultPerson = jsonObject.get("class_name").toString();
        String similarPercent = jsonObject.get("result").toString();
        String replacedResultPerson = resultPerson.replace("의원", "");

        System.out.println("resultPerson = " + replacedResultPerson);
        System.out.println("similarPercent = " + similarPercent);

        Congressman congressman = congressService.findByName(replacedResultPerson);
        //닮은꼴 의원 나온수 증가
        congressService.updateSimilarView(congressman);

        //결과많이 나온 의원수 top5 조회
        List<Congressman> scons = congressService.findOrderbySimilarView();

        model.addAttribute("scons", scons);
        model.addAttribute("resultPerson", replacedResultPerson);
        model.addAttribute("similarPercent", similarPercent);
        model.addAttribute("currentPage", "similar");

        return "congressman/congressmanSimilar";
    }

    //의원 검색
    @RequestMapping("/search")
    @ResponseBody
    public List<SearchDto> search(SearchDto searchDto) {
        if (searchDto.getName() != null && searchDto.getName() != "") {
            List<SearchDto> conList = congressService.findByNameLike(searchDto.getName());
            return conList;
        }
        return null;
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
