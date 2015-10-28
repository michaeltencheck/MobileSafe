package com.example.test.mobilesafe.utility;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by test on 10/27/2015.
 */
public class HttpWeb {
    public static InputStream getInputStream(URL url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(8888);
        connection.setReadTimeout(8888);
        InputStream is = new BufferedInputStream(connection.getInputStream());
        return is;
    }

    public static InputStream getInputStream(String str) throws Exception {
        URL url = new URL(str);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(8888);
        connection.setReadTimeout(8888);
        InputStream is = new BufferedInputStream(connection.getInputStream());
        return is;
    }
}
