package com.example.test.mobilesafe.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by test on 11/1/2015.
 */
public class FunctionInfo {
    private Drawable drawable;
    private String name;

    public FunctionInfo(Drawable drawable, String name) {
        this.drawable = drawable;
        this.name = name;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
