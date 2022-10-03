package com.roadking.congress.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

@Service
public class HttpMultifileService {

    //보내는부분
    public void client(File uploadFile){


        try {
            String url = "http://localhost:8080/getURL";
            String charset = "UTF-8";
            File binaryFile = uploadFile;
            String boundary = Long.toHexString(System.currentTimeMillis());
            String CRLF = "\r\n";

            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream output = null;
            try {
                output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
                // Send binary file.
                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
                writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
                writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                writer.append(CRLF).flush();
                Files.copy(binaryFile.toPath(), output);
                output.flush(); // Important before continuing with writer!
                writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

                // End of multipart/form-data.
                writer.append("--" + boundary + "--").append(CRLF).flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            int responseCode = ((HttpURLConnection) connection).getResponseCode();
            System.out.println(responseCode); // Should be 200

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
