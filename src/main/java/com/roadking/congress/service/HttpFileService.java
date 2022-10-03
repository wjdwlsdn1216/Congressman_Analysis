package com.roadking.congress.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

@Service
public class HttpFileService {

    public void httpClient(MultipartFile multipartFile) throws IOException {
        byte[] temp = new byte[2048];
        int length = 0;
        FileOutputStream fos = null;

        String basePath = "/Users/anyone/Desktop/git/Congressman_Analysis/src/main/resources/static/image/upload/";
        String uuidFileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        File file = new File(basePath, uuidFileName);
        multipartFile.transferTo(file);

        FileInputStream fis = new FileInputStream(file);
        OutputStream os = null;
        InputStream is = null;

        URL url = new URL("http://localhost:8080/test");
//        URL url = new URL("https://70b6-221-156-19-39.jp.ngrok.io/predict");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        try {
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("fileName", file.getName());

            os = con.getOutputStream();

//            HashMap<> hashMap = new HashMap<>();
            //fis.read로 byte배열 temp의 크기만큼 데이터를 읽어서 배열 temp에 저장한다.
            //그리고 읽은 바이트수를 반환한다(length 변수).
            while ((length = fis.read(temp)) > 0) {
                //temp 에 저장된 값을 os.write 로 byte배열 temp의 0 위치부터 length 까지 write(데이터를 출력) 한다.
                os.write(temp, 0, length);
            }
            // 즉 보내고자 하는 데이터를 inputStream으로 읽어서 미리 생성해놓은 byte 배열에 저장하고
            // outputStream 으로 그 byte배열을 쓰는것(전송하는것)
            //end


            is = con.getInputStream();
            System.out.println(con.getHeaderField("msg"));

//            //추가
//            String reposiPath = "/Users/anyone/Desktop/git/Congressman_Analysis/src/main/resources/static/image/receive/"+ con.getHeaderField("filename");
//
//            //추가
//            File resfile = new File(reposiPath);
//
//            //추가
//            try {
//                fos = new FileOutputStream(resfile);
//                while ((length = is.read(temp)) > 0) {
//                    fos.write(temp, 0, length);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                is.close();
//                fos.close();
//            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            os.close();
            is.close();
            fis.close();
            con.disconnect();
        }

    }

    public void httpSever(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        InputStream is = null;
        OutputStream os = null;
        int length = 0;
        String header = "";

        byte[] temp = new byte[2048];

        is = req.getInputStream();

        String reposiPath = "/Users/anyone/Desktop/git/Congressman_Analysis/src/main/resources/static/image/receive/"+ req.getHeader("fileName");

        File file = new File(reposiPath);

        try {
            fos = new FileOutputStream(file);
            while ((length = is.read(temp)) > 0) {
                fos.write(temp, 0, length);
            }
            res.addHeader("msg", "success");
        } catch (IOException e) {
            res.addHeader("msg", "false");
            e.printStackTrace();
        } finally {
            is.close();
            fos.close();
        }






    }
}
