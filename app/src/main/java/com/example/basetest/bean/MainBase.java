package com.example.basetest.bean;

import android.graphics.Bitmap;

/**
 * this is a MainBase for MainRecyclerAdapter
 * Created by YinZZ on 2016/8/29.
 */
public class MainBase {
    private String title;
    private Bitmap bitmap;

    public MainBase() {

    }

    public MainBase(String title) {
        this.title = title;
    }

    public MainBase(Bitmap bitmap, String title) {
        this.bitmap = bitmap;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
