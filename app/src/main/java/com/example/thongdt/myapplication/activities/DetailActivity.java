package com.example.thongdt.myapplication.activities;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.example.thongdt.myapplication.R;
import com.example.thongdt.myapplication.adapter.DetailRecyclerViewAdapter;
import com.example.thongdt.myapplication.objects.Detail;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by thongdt on 19/10/2015.
 */

@EActivity(R.layout.activity_detail)
public class DetailActivity extends Activity {

    @ViewById(R.id.recyclerDetail)
    RecyclerView mRecyclerViewDetail;
    private ArrayList<Detail> mDetails;
    private DetailRecyclerViewAdapter mDetailRecyclerViewAdapter;

    @Extra("url")
    String mUrl;

    @AfterViews
    void init() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewDetail.setLayoutManager(layoutManager);

        mDetails = new ArrayList<>();
        mDetailRecyclerViewAdapter = new DetailRecyclerViewAdapter(mDetails);
        mRecyclerViewDetail.setAdapter(mDetailRecyclerViewAdapter);
        setListener();
        loadData();
    }

    @Background
    void loadData() {
        Document doc = null;
        try {
            doc = Jsoup.connect(mUrl)
                    .header("Accept-Encoding", "gzip, deflate")
                    .maxBodySize(0)
                    .timeout(600000)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element element = doc.select("div.article_text").first();
        Element elementTbody = element.select("tbody").first();
        Elements elementDetails = elementTbody.select("tr");
        for (int i = 0; i < elementDetails.size(); i++) {
            if (i == 0) {
                Detail detail = new Detail();
                detail.setType(DetailRecyclerViewAdapter.DETAIL_TITLE);
                detail.setTitle(elementDetails.get(i).text());

                Log.v("title", detail.getTitle());

                mDetails.add(detail);
            } else if (i == 1) {
                Detail detail = new Detail();
                detail.setType(DetailRecyclerViewAdapter.DETAIL_DATE_TIME);
                detail.setDate(elementDetails.get(i).select("div.art_date").first().text());
                Log.v("date", detail.getDate());

                mDetails.add(detail);
            } else if (i == 4) {
                Detail detail = new Detail();
                detail.setType(DetailRecyclerViewAdapter.DETAIL_INTRODUCE);
                detail.setInformation(elementDetails.get(i).text());
                Log.v("introduce", detail.getInformation());
                mDetails.add(detail);
            } else if (i == 5) {
                Elements elementTexts = elementDetails.get(i).select("div");

                if (elementTexts.size() > 3) {

                    for (int j = 1; j < elementTexts.size() - 1; j++) {
                        if (!elementTexts.get(j).select("img").attr("src").trim().equals("")) {
                            Detail detail = new Detail();
                            detail.setType(DetailRecyclerViewAdapter.DETAIL_IMAGE);

                            // Log.v("url",elementsFont.get(k).select("img").attr("src").trim());

                            String url = elementTexts.get(j).select("img").attr("src").trim();
                            if (!(url.contains("http://") || url.contains("https://"))) {
                                url = "http://bongdaso.com" + url;
                            }
                            detail.setUrlImage(url);
                            Log.v("url", url);
                            mDetails.add(detail);
                        } else {

                            if (!elementTexts.get(j).text().trim().equals("")) {
                                Detail detail = new Detail();
                                detail.setType(DetailRecyclerViewAdapter.DETAIL_TEXT);
                                detail.setInformation(elementTexts.get(j) + "");
                                Log.v("text" + j, detail.getInformation());
                                mDetails.add(detail);
                            }
                        }
                    }
                } else {
                    for (int j = 1; j < elementTexts.size() - 1; j++) {

                        Elements elementsFont = elementTexts.get(j).select("font");
                        Log.v("size", elementsFont.size() + "");

                        for (int k = 0; k < elementsFont.size(); k++) {
                            if (!elementsFont.get(k).select("img").attr("src").trim().equals("")) {

                                Detail detail = new Detail();
                                detail.setType(DetailRecyclerViewAdapter.DETAIL_IMAGE);

                                // Log.v("url",elementsFont.get(k).select("img").attr("src").trim());

                                String url = elementsFont.get(k).select("img").attr("src").trim();
                                if (!(url.contains("http://") || url.contains("https://"))) {
                                    url = "http://bongdaso.com" + url;
                                }
                                detail.setUrlImage(url);
                                mDetails.add(detail);

                            } else {
                                if (!elementsFont.get(k).text().trim().equals("")) {
                                    Detail detail = new Detail();
                                    detail.setType(DetailRecyclerViewAdapter.DETAIL_TEXT);
                                    detail.setInformation(elementsFont.get(k) + "");
                                    mDetails.add(detail);
                                }
                            }
                        }

                    }
                }

            } else if (i == 6) {
                Detail detail = new Detail();
                detail.setType(DetailRecyclerViewAdapter.DETAIL_AUTHOR);
                detail.setAuthor(elementDetails.get(i).text());
                Log.v("author", detail.getAuthor());
                mDetails.add(detail);
            } else if (i == 8 || i == 10) {
                Elements elementSames = elementDetails.get(i).select("li");
                for (int j = 0; j < elementSames.size(); j++) {
                    Detail detail = new Detail();
                    detail.setType(DetailRecyclerViewAdapter.DETAIL_SAME);
                    detail.setInformation(elementSames.get(j).text().trim());

                    String url = elementSames.get(j).select("a").attr("href").trim();
                    if (!(url.contains("http://") || url.contains("https://"))) {
                        url = "http://bongdaso.com/" + url;
                    }
                    detail.setUrlNews(url);
                    mDetails.add(detail);
                }
            }
        }

        Detail detail = new Detail();
        detail.setType(DetailRecyclerViewAdapter.DETAIL_TEXT);
        detail.setInformation("");
        mDetails.add(detail);

        setUiDetail();
    }

    @UiThread
    void setUiDetail() {
        mDetailRecyclerViewAdapter.notifyDataSetChanged();
    }

    void setListener() {
        mDetailRecyclerViewAdapter.setOnItemSameClick(new DetailRecyclerViewAdapter.OnItemSameClick() {
            @Override
            public void onClick(int position) {
                mUrl = mDetails.get(position).getUrlNews();
                mDetails.clear();
                loadData();
            }
        });
    }
}
