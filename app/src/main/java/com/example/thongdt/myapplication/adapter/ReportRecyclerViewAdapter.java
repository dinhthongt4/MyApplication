package com.example.thongdt.myapplication.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thongdt.myapplication.R;
import com.example.thongdt.myapplication.objects.Report;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by thongdt on 16/10/2015.
 */
public class ReportRecyclerViewAdapter extends RecyclerView.Adapter<ReportRecyclerViewAdapter.ItemViewHolder> {

    private ArrayList<Report> mReports;
    private DisplayImageOptions mDisplayImageOptions;

    public ReportRecyclerViewAdapter(ArrayList<Report> reports) {
        this.mReports = reports;
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_report, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.tvInformation.setText(mReports.get(position).getInformation());
        holder.tvTitle.setText(mReports.get(position).getTitle());
        ImageLoader.getInstance().displayImage(mReports.get(position).getUrlImage(), holder.imgAvatar, mDisplayImageOptions);
    }

    @Override
    public int getItemCount() {
        return mReports.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvInformation;
        ImageView imgAvatar;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvInformation = (TextView) itemView.findViewById(R.id.tvInformation);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
        }
    }
}
