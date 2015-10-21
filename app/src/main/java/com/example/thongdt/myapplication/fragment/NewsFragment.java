package com.example.thongdt.myapplication.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.thongdt.myapplication.R;
import com.example.thongdt.myapplication.activities.DetailActivity_;
import com.example.thongdt.myapplication.adapter.NewsRecyclerViewAdapter;
import com.example.thongdt.myapplication.adapter.ReportRecyclerViewAdapter;
import com.example.thongdt.myapplication.objects.Menu;
import com.example.thongdt.myapplication.objects.New;
import com.example.thongdt.myapplication.objects.Report;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by thongdt on 13/10/2015.
 */

@EFragment(R.layout.fragment_news)
public class NewsFragment extends Fragment {

    @ViewById(R.id.recyclerViewNews)
    RecyclerView mRecyclerViewNews;

    private static final String TAG = "NewsFragment";
    private ArrayList<New> mNews;
    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    private ReportRecyclerViewAdapter mReportRecyclerViewAdapter;
    private ArrayList<Report> mReports;

    @AfterViews
    void init() {
        mNews = new ArrayList<>();
        mReports = new ArrayList<>();

        mNewsRecyclerViewAdapter = new NewsRecyclerViewAdapter(mNews);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewNews.setLayoutManager(layoutManager);
        mRecyclerViewNews.setAdapter(mNewsRecyclerViewAdapter);
        mReportRecyclerViewAdapter = new ReportRecyclerViewAdapter(mReports);

        onListener();
        loadDocument();
    }

    @Background
    void loadDocument() {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://bongdaso.com/news.aspx").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getTitleNews(doc);
        getListNews(doc);

        setUiApplication();
    }

    @UiThread
    void setUiApplication() {

        mNewsRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getTitleNews(Document doc) {
        Element elementTitle = doc.select("div#infocus_left_tile").first();
        New n1 = new New();

        Element elementTitleChild = elementTitle.select("div._title_").first();
        Log.v("title", elementTitleChild.text());
        n1.setType(0);
        n1.setTitleHeader(elementTitleChild.text());

        Element elementTitleImage = elementTitle.select("div._img_").first();
        Element elementTitleA = elementTitleImage.select("a").first();
        String link = elementTitleA.attr("href");
        if (!(link.contains("http://") || link.contains("https://"))) {
            link = "http://bongdaso.com/" + link;
        }
        n1.setLink(link);

        Element elementTitleSrc = elementTitleA.select("img").first();
        Log.v("href", elementTitleSrc.attr("src"));
        n1.setUrlImageHeader(elementTitleSrc.attr("src"));

        Element elementInformation = elementTitle.select("div._brief_").first();
        Log.v("infor", elementInformation.text());
        n1.setTitle(elementInformation.text());

        mNews.add(n1);

    }

    private void getListNews(Document doc) {
        Element elementListNews = doc.select("div#infocus_right_tile").first();
        for (int i = 0; i < elementListNews.childNodeSize(); i++) {
            New n = new New();
            Element elementItemChild = elementListNews.child(i);
            Element elementA = elementItemChild.select("a").first();

            String link = elementA.attr("href");
            if (!(link.contains("http://") || link.contains("https://"))) {
                link = "http://bongdaso.com/" + link;
            }
            n.setLink(link);

            Element elementImg = elementA.select("img").first();
            n.setState(elementImg.attr("src"));

            Element elementH1 = elementA.select("h1").first();
            n.setTitle(elementH1.text());

            n.setType(1);

            mNews.add(n);
        }
    }

    @Background
    public void loadReportList(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element element = doc.select("div#REPORT_tile").first();
        Elements elementDatas = element.select("div");

        for (int i = 1; i < elementDatas.size() - 4;) {
            Report report = new Report();
            report.setUrlImage(elementDatas.get(i + 1).select("img").attr("src"));
            report.setTitle(elementDatas.get(i + 3).text());
            report.setLink(elementDatas.get(i + 3).select("a").attr("href"));
            report.setInformation(elementDatas.get(i + 4).text());
            mReports.add(report);
            i = i + 5;
        }
        setUiNews();
    }

    @UiThread
    public void setUiNews() {
        mRecyclerViewNews.setAdapter(mReportRecyclerViewAdapter);
    }

    @Background
    public void loadDataSection(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mNews.clear();
        getTitleNews(doc);
        getListNewsSection(doc);
        setUiSection();
    }

    @UiThread
    void setUiSection() {
        mNewsRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getListNewsSection(Document document) {
        Element elementListNews = document.select("div#infocus_right_tile").first();
        Elements elementTitles = elementListNews.select("div._title_");
        for (int i = 0; i < elementTitles.size(); i ++) {
            New n = new New();
            n.setType(1);
            n.setTitle(elementTitles.get(i).text());
            String link = elementTitles.get(i).select("a").attr("href");
            if (!(link.contains("http://") || link.contains("https://"))) {
                link = "http://bongdaso.com/" + link;
            }
            n.setLink(link);
            mNews.add(n);
        }
    }

    private void onListener() {
        mNewsRecyclerViewAdapter.setOnItemClickListener(new NewsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                DetailActivity_.intent(getContext()).extra("url", mNews.get(position).getLink()).start();
            }
        });
    }
}