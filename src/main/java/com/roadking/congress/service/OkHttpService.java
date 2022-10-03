package com.roadking.congress.service;

import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class OkHttpService {


    public void client(MultipartFile multipartFile) throws IOException {
        String basePath = "/Users/anyone/Desktop/git/Congressman_Analysis/src/main/resources/static/image/upload/";
        String uuidFileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        File file = new File(basePath, uuidFileName);
        multipartFile.transferTo(file);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("fileName", file.getName())
                .url("http://localhost:8080/test")
                .post(RequestBody.create(MediaType.parse("image"), file))
                .build();

        Response response = okHttpClient.newCall(request).execute();

        String msg = response.header("msg");
        System.out.println(msg);
    }
}
