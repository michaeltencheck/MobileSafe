package com.example.test.mobilesafe.utility;

import android.app.ProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by test on 10/29/2015.
 */
public class DownloaderV1 {
    private String websiteUrl, path;
    private ProgressDialog progressDialog;
    private File file;

    public DownloaderV1(String websiteUrl, String path, ProgressDialog progressDialog) {
        this.websiteUrl = websiteUrl;
        this.path = path;
        this.progressDialog = progressDialog;
        this.file = new File(path);
    }

    public interface MyThreadListener{
        public File threadFinished();
    }

    public void downloadFile(final MyThreadListener listener) {
        progressDialog.show();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(websiteUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8888);
                    connection.setReadTimeout(8888);
                    InputStream inputStream = connection.getInputStream();
                    int total = connection.getContentLength();
                    int len = 0;
                    progressDialog.setMax(total);
                    progressDialog.show();
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
                    progressDialog.dismiss();
                    listener.threadFinished();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
//        thread.join();
//        return file;
    }
}
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
//        return file;
//    }
//}
