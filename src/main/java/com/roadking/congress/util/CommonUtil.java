package com.roadking.congress.util;


import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

    private static final String UPLOAD_FOLDER = "httpTest";

    public static File getImageDirectory(MultipartHttpServletRequest request) {

        //로컬
        ServletContext servlet = request.getSession().getServletContext();
//        String root = servlet.getRealPath("/") + UPLOAD_FOLDER + File.separator;

        //서버
        //String root = "/data" + File.separator + UPLOAD_FOLDER + File.separator;

        //커스텀
        String root = "/Users/anyone/Desktop/git/Congressman_Analysis/src/main/resources/static/image/receive/";

        System.out.println("root = " + root);

        String dayPath = new SimpleDateFormat("yyMMdd").format(new Date());

        dayPath = root + File.separator + dayPath;

        File dayDirectory = new File(dayPath);
        if (!dayDirectory.isDirectory()) {
            dayDirectory.mkdirs();
        }

        int subCount = dayDirectory.listFiles().length;

        if (subCount == 0) {
            String subPath = dayDirectory + File.separator + subCount;
            File subDirectory = new File(subPath);
            subDirectory.mkdirs();
            return subDirectory;
        } else {
            String subPath = dayDirectory + File.separator + (subCount - 1);
            File subDirectory = new File(subPath);
            if (subDirectory.isDirectory()) {
                if (subDirectory.listFiles().length > 0) {
                    subPath = dayDirectory + File.separator + subCount;
                    subDirectory = new File(subPath);
                    subDirectory.mkdirs();
                }
            } else {
                subDirectory.mkdirs();
            }
            return subDirectory;
        }
    }
}
