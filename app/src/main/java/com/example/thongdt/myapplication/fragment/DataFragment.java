package com.example.thongdt.myapplication.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.thongdt.myapplication.R;
import com.example.thongdt.myapplication.adapter.DataRecyclerViewAdapter;
import com.example.thongdt.myapplication.objects.InformationGame;
import com.example.thongdt.myapplication.objects.Menu;

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
 * Created by thongdt on 14/10/2015.
 */

@EFragment(R.layout.fragment_data)
public class DataFragment extends Fragment {

    @ViewById(R.id.recyclerViewData)
    RecyclerView mRecyclerViewData;

    private ArrayList<InformationGame> mInformationGames;
    private DataRecyclerViewAdapter mDataRecyclerViewAdapter;

    @AfterViews
    void init() {
        mInformationGames = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewData.setLayoutManager(layoutManager);
        mDataRecyclerViewAdapter = new DataRecyclerViewAdapter(mInformationGames);
        mRecyclerViewData.setAdapter(mDataRecyclerViewAdapter);

        loadInformationGame("http://tyso.bongda.com.vn/widgets/widget-fixtures-finished.php?league_id=1&season=1516&limit=10&css=http%3A%2F%2Ftyso.bongda.com.vn%2Fcss%2Ffixture-recent.css"
                ,"http://tyso.bongda.com.vn/widgets/widget-fixtures-not-started.php?league_id=1&season=1516&limit=10&css=http%3A%2F%2Ftyso.bongda.com.vn%2Fcss%2Ffixture-recent.css");
    }

    @Background
    public void loadInformationGame(String urlStarted, String urlNotStart) {
        mInformationGames.clear();
        loadInformationGameNotStart(urlNotStart);
        loadInformationGameStarted(urlStarted);
        setBgItem();
        setUiApplication();
    }

    @UiThread
    void setUiApplication() {
        mDataRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void loadInformationGameNotStart(String url) {

        InformationGame informationGame = new InformationGame();
        informationGame.setType(0);
        informationGame.setTitle("Những trận sắp thi đấu");
        mInformationGames.add(informationGame);

     Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .header("Accept-Encoding", "gzip, deflate")
                    .maxBodySize(0)
                    .timeout(600000)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element elementMainContent = doc.select("tbody").first();
        Elements elementItems = elementMainContent.select("tr");

        for (int i = 0;i < elementItems.size(); i ++) {
            Elements informationItem = elementItems.get(i).select("td");
            InformationGame informationGame1 = new InformationGame();
            informationGame1.setType(1);
            informationGame1.setDate(informationItem.get(0).text());
            informationGame1.setNameAwayClub(informationItem.get(1).text());
            informationGame1.setNameHomeClub(informationItem.get(3).text());
            informationGame1.setTime(informationItem.get(2).text());
            mInformationGames.add(informationGame1);
        }
    }

    private void loadInformationGameStarted(String url) {
        InformationGame informationGame = new InformationGame();
        informationGame.setType(0);
        informationGame.setTitle("Những trận đã thi đấu");
        mInformationGames.add(informationGame);

        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .header("Accept-Encoding", "gzip, deflate")
                    .maxBodySize(0)
                    .timeout(600000)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element elementMainContent = doc.select("tbody").first();
        Elements elementItems = elementMainContent.select("tr");

        for (int i = 0;i < elementItems.size(); i ++) {
            Elements informationItem = elementItems.get(i).select("td");
            InformationGame informationGame1 = new InformationGame();
            informationGame1.setType(1);
            informationGame1.setDate(informationItem.get(0).text());
            informationGame1.setNameAwayClub(informationItem.get(1).text());
            informationGame1.setNameHomeClub(informationItem.get(3).text());
            informationGame1.setTime(informationItem.get(2).text());
            mInformationGames.add(informationGame1);
        }
    }

    private void setBgItem() {
        for (int i = 0 ; i < mInformationGames.size(); i++ ) {
            if (i % 2 == 1) {
                mInformationGames.get(i).setBg(true);
            }
        }
    }
}
