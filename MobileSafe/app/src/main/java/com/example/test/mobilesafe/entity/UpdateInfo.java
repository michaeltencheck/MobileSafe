package com.example.test.mobilesafe.entity;

/**
 * Created by test on 10/27/2015.
 */
public class UpdateInfo {
    private String version,description, apkUrl;

    public UpdateInfo(String version, String description, String apkUrl) {
        this.version = version;
        this.description = description;
        this.apkUrl = apkUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }
}
