package com.example.thongdt.myapplication.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thongdt.myapplication.R;
import com.example.thongdt.myapplication.objects.New;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by thongdt on 14/10/2015.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<New> mNews;
    private OnItemClickListener mOnItemClickListener;
    private DisplayImageOptions mDisplayImageOptions;

    public NewsRecyclerViewAdapter(ArrayList<New> news) {
        this.mNews = news;
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_header_news,parent,false);
            return new HeaderViewHolder(view);

        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyceler_view_news, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mNews.get(position).getType() == 0) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            ImageLoader.getInstance().displayImage(mNews.get(position).getUrlImageHeader(), headerViewHolder.imgAvatarHeader, mDisplayImageOptions);
            headerViewHolder.tvInformation.setText(mNews.get(position).getTitle());
            headerViewHolder.tvTitleHeader.setText(mNews.get(position).getTitleHeader());
        } else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvTitle.setText(mNews.get(position).getTitle());

            if (mNews.get(position).getState() != null) {
                if(!mNews.get(position).getState().equals("img/bullet_black.png")) {
                    itemViewHolder.imgState.setImageResource(R.drawable.ic_new_post);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mNews.get(position).getType();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imgState;
        TextView tvTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);

            imgState = (ImageView) itemView.findViewById(R.id.imgState);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClick(getPosition());
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatarHeader;
        TextView tvInformation;
        TextView tvTitleHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            imgAvatarHeader = (ImageView) itemView.findViewById(R.id.imgAvatarNews);
            tvTitleHeader = (TextView) itemView.findViewById(R.id.tvTitleHeader);
            tvInformation = (TextView) itemView.findViewById(R.id.tvInformation);
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

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
