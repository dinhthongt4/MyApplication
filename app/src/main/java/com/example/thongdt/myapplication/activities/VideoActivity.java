package com.example.thongdt.myapplication.activities;

import android.app.Activity;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.thongdt.myapplication.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by thongdt on 20/10/2015.
 */

@EActivity(R.layout.activity_video)
public class VideoActivity extends Activity{

    @ViewById(R.id.webView)
    WebView mWebView;

    @Extra("videoId")
    String mVideoId;

    @AfterViews
    void init() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x/2 - 20;
        int height = size.y/2 -20;
        Log.v("width","" + width);
        Log.v("height","" + height);

        String html = "";

        html += "<html><body>";
        html += "<iframe width=\""+ width +"\" height=\""+ height +"\" src=\"http://www.youtube.com/embed/"+ mVideoId +"?rel=0\" frameborder=\"0\" allowfullscreen></iframe>";
        html += "</body></html>";
        mWebView.setWebChromeClient(new WebChromeClient() {
        });

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        mWebView.loadData(html, "text/html", null);
    }
}
