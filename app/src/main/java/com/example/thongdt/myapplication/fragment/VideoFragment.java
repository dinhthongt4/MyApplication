package com.example.thongdt.myapplication.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.thongdt.myapplication.R;
import com.example.thongdt.myapplication.activities.VideoActivity_;
import com.example.thongdt.myapplication.adapter.VideoRecyclerViewAdapter;
import com.example.thongdt.myapplication.objects.FullInformationVideo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by thongdt on 20/10/2015.
 */

@EFragment(R.layout.fragment_video)
public class VideoFragment extends Fragment {

    @ViewById(R.id.recyclerViewVideo)
    RecyclerView mRecyclerViewVideo;

    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;

    private ArrayList<FullInformationVideo.Item> mItems;
    private VideoRecyclerViewAdapter mVideoRecyclerViewAdapter;
    private int mCount = 10;

    //https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCyAojDE5b7HoWX6rzYUn5Mg&type=video&order=date&maxResults=5&key=AIzaSyBtEYk0NLi1DcEjYx8Z1TDDWulmmTajV4s
    @AfterViews
    void init() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewVideo.setLayoutManager(layoutManager);
        mItems = new ArrayList<>();
        mVideoRecyclerViewAdapter = new VideoRecyclerViewAdapter(mItems, mRecyclerViewVideo);
        mRecyclerViewVideo.setAdapter(mVideoRecyclerViewAdapter);

        mProgressBar.setVisibility(View.VISIBLE);
        loadInformationChannel();
        setListener();
    }

    private void loadInformationChannel() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://www.googleapis.com").build();
        Api api = restAdapter.create(Api.class);
        api.getVideoChannel("snippet", "UCyAojDE5b7HoWX6rzYUn5Mg",
                "video", "date", mCount, "AIzaSyBtEYk0NLi1DcEjYx8Z1TDDWulmmTajV4s", new Callback<FullInformationVideo>() {
                    @Override
                    public void success(FullInformationVideo fullInformationVideo, Response response) {
                        mItems.addAll(fullInformationVideo.getItems());
                        Log.v("size", "" + mItems.get(0).snippet.title);
                        mVideoRecyclerViewAdapter.notifyDataSetChanged();
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    private void loadInformationDataLoadMore() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://www.googleapis.com").build();
        Api api = restAdapter.create(Api.class);
        api.getVideoChannel("snippet", "UCyAojDE5b7HoWX6rzYUn5Mg",
                "video", "date", mCount, "AIzaSyBtEYk0NLi1DcEjYx8Z1TDDWulmmTajV4s", new Callback<FullInformationVideo>() {
                    @Override
                    public void success(FullInformationVideo fullInformationVideo, Response response) {

                        for (int i = mItems.size() ; i < mCount ; i ++) {
                            mItems.add(fullInformationVideo.getItems().get(i));
                            mVideoRecyclerViewAdapter.notifyItemInserted(mItems.size());
                        }
                        mVideoRecyclerViewAdapter.setLoaded();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    private void setListener() {
        mVideoRecyclerViewAdapter.setOnItemClickListener(new VideoRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                VideoActivity_.intent(getContext()).extra("videoId", mItems.get(position).id.videoId).start();
            }
        });

        mVideoRecyclerViewAdapter.setOnLoadMoreListener(new VideoRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mItems.add(null);
                mVideoRecyclerViewAdapter.notifyItemInserted(mItems.size() - 1);
                if (mCount < 40) {
                    mItems.remove(mItems.size() - 1);
                    mVideoRecyclerViewAdapter.notifyItemRemoved(mItems.size());
                    mCount = mCount + 5;
                    loadInformationDataLoadMore();
                    Log.v("connt", "" + mCount);
                }
            }
        });
    }

    public interface Api {
        @GET("/youtube/v3/search")
        void getVideoChannel(@Query("part") String path, @Query("channelId") String channelId,
                             @Query("type") String type, @Query("order") String order
                , @Query("maxResults") int maxResults, @Query("key") String key, Callback<FullInformationVideo> fullInformationVideoCallback);
    }
}
