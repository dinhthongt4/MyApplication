package com.example.thongdt.myapplication.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thongdt.myapplication.R;
import com.example.thongdt.myapplication.objects.FullInformationVideo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by thongdt on 20/10/2015.
 */
public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.ItemViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private ArrayList<FullInformationVideo.Item> mItems;
    private DisplayImageOptions mDisplayImageOptions;

    public VideoRecyclerViewAdapter(ArrayList<FullInformationVideo.Item> items) {
        this.mItems = items;
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_video, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.tvTitle.setText(mItems.get(position).snippet.title);
        ImageLoader.getInstance().displayImage(mItems.get(position).snippet.thumbnails.high.url, holder.imgAvatar, mDisplayImageOptions);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
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

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
