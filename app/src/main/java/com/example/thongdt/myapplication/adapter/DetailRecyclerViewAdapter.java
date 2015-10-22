package com.example.thongdt.myapplication.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thongdt.myapplication.R;
import com.example.thongdt.myapplication.objects.Detail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by thongdt on 19/10/2015.
 */
public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int DETAIL_TITLE = 0;
    public static final int DETAIL_DATE_TIME = 1;
    public static final int DETAIL_INTRODUCE = 2;
    public static final int DETAIL_TEXT = 3;
    public static final int DETAIL_IMAGE = 4;
    public static final int DETAIL_AUTHOR = 5;
    public static final int DETAIL_SAME = 6;

    private ArrayList<Detail> mDetails;
    private OnItemSameClick mOnItemSameClick;
    private DisplayImageOptions mDisplayImageOptions;

    public DetailRecyclerViewAdapter(ArrayList<Detail> details) {
        this.mDetails = details;
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getItemViewType(int position) {
        return mDetails.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DETAIL_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_detail_title, parent, false);
            return new TitleViewHolder(view);
        } else if (viewType == DETAIL_AUTHOR) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_detail_author, parent, false);
            return new AuthorViewholder(view);
        } else if (viewType == DETAIL_IMAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_detail_image, parent, false);
            return new ImageViewHolder(view);
        } else if (viewType == DETAIL_TEXT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_detail_text, parent, false);
            return new TextViewHolder(view);
        } else if (viewType == DETAIL_DATE_TIME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_detail_date_time, parent, false);
            return new DateTimeViewHolder(view);
        } else if (viewType == DETAIL_INTRODUCE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_detail_introduce, parent, false);
            return new IntroduceViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_detail_news_same, parent, false);
            return new SameViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (DETAIL_TITLE == mDetails.get(position).getType()) {
            TitleViewHolder titleDateViewHolder = (TitleViewHolder) holder;
            titleDateViewHolder.tvTitle.setText(mDetails.get(position).getTitle());
        } else if (DETAIL_AUTHOR == mDetails.get(position).getType()) {
            AuthorViewholder authorViewholder = (AuthorViewholder) holder;
            authorViewholder.tvAuthor.setText(mDetails.get(position).getAuthor());
        } else if (DETAIL_IMAGE == mDetails.get(position).getType()) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            ImageLoader.getInstance().displayImage(mDetails.get(position).getUrlImage(), imageViewHolder.imgDetail, mDisplayImageOptions);
        } else if (DETAIL_TEXT == mDetails.get(position).getType()) {
            TextViewHolder textViewHolder = (TextViewHolder) holder;
            textViewHolder.tvInformation.setText("  "+ Html.fromHtml("<p style=\"text-align: justify; text-justify: inter-word\">" + mDetails.get(position).getInformation().trim() + "</p>"));
        } else if (DETAIL_DATE_TIME == mDetails.get(position).getType()) {
            DateTimeViewHolder dateTimeViewHolder = (DateTimeViewHolder) holder;
            dateTimeViewHolder.tvDateTime.setText(mDetails.get(position).getDate());
        } else if (DETAIL_INTRODUCE == mDetails.get(position).getType()) {
            IntroduceViewHolder introduceViewHolder = (IntroduceViewHolder) holder;
            introduceViewHolder.tvIntroduce.setText("   " + mDetails.get(position).getInformation());
        } else {
            SameViewHolder sameViewHolder = (SameViewHolder) holder;
            sameViewHolder.tvNewsSame.setText(">  " + mDetails.get(position).getInformation());
        }
    }

    @Override
    public int getItemCount() {
        return mDetails.size();
    }

    public void setOnItemSameClick(OnItemSameClick onItemSameClick) {
        mOnItemSameClick = onItemSameClick;
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }

    class DateTimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvDateTime;

        public DateTimeViewHolder(View itemView) {
            super(itemView);
            tvDateTime = (TextView) itemView.findViewById(R.id.tvDateTime);
        }
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        TextView tvInformation;

        public TextViewHolder(View itemView) {
            super(itemView);
            tvInformation = (TextView) itemView.findViewById(R.id.tvInformation);
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDetail;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imgDetail = (ImageView) itemView.findViewById(R.id.imgDetail);
        }
    }

    class AuthorViewholder extends RecyclerView.ViewHolder {
        TextView tvAuthor;

        public AuthorViewholder(View itemView) {
            super(itemView);
            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
        }
    }

    class SameViewHolder extends RecyclerView.ViewHolder {

        TextView tvNewsSame;

        public SameViewHolder(View itemView) {
            super(itemView);
            tvNewsSame = (TextView) itemView.findViewById(R.id.tvSameSame);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemSameClick != null) {
                        mOnItemSameClick.onClick(getPosition());
                    }
                }
            });
        }
    }

    class IntroduceViewHolder extends RecyclerView.ViewHolder {
        TextView tvIntroduce;

        public IntroduceViewHolder(View itemView) {
            super(itemView);
            tvIntroduce = (TextView) itemView.findViewById(R.id.tvIntroduce);
        }
    }

    public interface OnItemSameClick {
        void onClick(int position);
    }
}
