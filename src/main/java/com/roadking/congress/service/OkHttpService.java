package com.roadking.congress.service;

import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class OkHttpService {

    public String client(MultipartFile multipartFile) throws IOException {
        String basePath = "/Users/anyone/Desktop/git/Congressman_Analysis/src/main/resources/static/image/upload/";
        String uuidFileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        File file = new File(basePath, uuidFileName);
        multipartFile.transferTo(file);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("fileName", file.getName())
                .url("https://70b6-221-156-19-39.jp.ngrok.io/predict")
                .post(RequestBody.create(MediaType.parse("image"), file))
                .build();

        Response response = okHttpClient.newCall(request).execute();


        String result = response.body().string();
        System.out.println(result);

        return result;







    }
}
