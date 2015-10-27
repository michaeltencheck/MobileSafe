package com.example.test.mobilesafe.utility;

import android.util.Xml;

import com.example.test.mobilesafe.entity.UpdateInfo;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * Created by test on 10/27/2015.
 */
public class XmlParser {
    public static UpdateInfo updateInfoParser(InputStream inputStream) throws Exception {
        UpdateInfo updateInfo = new UpdateInfo("", "", "");
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, "utf-8");
        int eventType = xmlPullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("version".equals(xmlPullParser.getName())) {
                        String version = xmlPullParser.nextText();
                        updateInfo.setVersion(version);
                    }else if ("description".equals(xmlPullParser.getName())) {
                        String description = xmlPullParser.nextText();
                        updateInfo.setDescription(description);
                    }else if ("apkurl".equals(xmlPullParser.getName())) {
                        String apkUrl = xmlPullParser.nextText();
                        updateInfo.setApkUrl(apkUrl);
                    }
                    break;
                default:
                    break;
            }
            eventType = xmlPullParser.next();
        }
        return updateInfo;
    }
}
