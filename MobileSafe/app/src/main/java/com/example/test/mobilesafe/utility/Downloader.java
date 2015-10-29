package com.example.test.mobilesafe.utility;

import android.app.ProgressDialog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by test on 10/28/2015.
 */
public class Downloader {
    public static File downloadFile(String websiteUrl, String path, ProgressDialog progressDialog)
            throws Exception{
        File file = new File(path);
        URL url = new URL(websiteUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(8888);
        connection.setReadTimeout(8888);
        InputStream inputStream = connection.getInputStream();
        int total = connection.getContentLength();
        int len = 0;
        progressDialog.setMax(total);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int progress = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
            progress += len;
            progressDialog.setProgress(progress);
        }
        fos.flush();
        fos.close();
        inputStream.close();
        return file;
    }
}
