package com.roadking.congress.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

@Service
public class FileService {

    public void client(MultipartFile multipartFile) {
        System.out.println("client---->");
        try {
            String basePath = "/Users/anyone/Desktop/uploadImgs";
            Socket socket = new Socket("127.0.0.1", 58824);
            if (socket.isConnected()) {
                System.out.println("isConnected");

                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(multipartFile.getOriginalFilename()); //
                String uuidFileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
                File file = new File(basePath, uuidFileName);
                multipartFile.transferTo(file);

                byte[] buff = new byte[4096];
                InputStream inputStream = new FileInputStream(file);
                int len = -1;
                while ((len = inputStream.read(buff)) != -1) {
                    dataOutputStream.write(buff, 0, len);
                }
                inputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();
                System.out.println("데이터 송신 완료!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void server() {
        System.out.println("----->server");

        try {
            ServerSocket server = new ServerSocket(58824);
            Socket socket = server.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String readUTF = dataInputStream.readUTF();
            System.out.println("imgName = " + readUTF);

            byte[] buff = new byte[4096];
            OutputStream OutputStream = new FileOutputStream("/Users/anyone/Desktop/receiveImgs/" + readUTF);//

            int len = -1;
            while ((len = dataInputStream.read(buff)) != -1) {
                OutputStream.write(buff, 0, len);
            }

            OutputStream.flush();
            OutputStream.close();
            dataInputStream.close();
            System.out.println("데이터 수신 완료");

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server end");
    }
}
