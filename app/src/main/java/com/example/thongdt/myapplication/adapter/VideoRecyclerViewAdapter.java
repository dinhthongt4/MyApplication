package com.example.thongdt.myapplication.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.thongdt.myapplication.R;
import com.example.thongdt.myapplication.objects.FullInformationVideo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by thongdt on 20/10/2015.
 */
public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private ArrayList<FullInformationVideo.Item> mItems;
    private DisplayImageOptions mDisplayImageOptions;
    private OnLoadMoreListener mOnLoadMoreListener;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PRO = 1;

    private int mVisibleThreshold = 3;
    private int mLastVisibleItem;
    private int mTotalItemCount;
    private boolean mIsLoading;

    public VideoRecyclerViewAdapter(ArrayList<FullInformationVideo.Item> items, RecyclerView recyclerView) {
        this.mItems = items;
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    mTotalItemCount = linearLayoutManager.getItemCount();
                    mLastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition();

                    Log.v("total","" + mTotalItemCount);
                    Log.v("last", "" + mLastVisibleItem);
                    if (!mIsLoading
                            && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                        mIsLoading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position) != null ? TYPE_ITEM : TYPE_PRO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_video, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_video_progress, parent, false);
            return new ProgressViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvTitle.setText(mItems.get(position).snippet.title);
            ImageLoader.getInstance().displayImage(mItems.get(position).snippet.thumbnails.high.url, itemViewHolder.imgAvatar, mDisplayImageOptions);
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setLoaded() {
        mIsLoading = false;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        TextView tvTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClick(getPosition());
                    }
                }
            });
        }
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
