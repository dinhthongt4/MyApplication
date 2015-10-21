package com.example.thongdt.myapplication.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thongdt.myapplication.R;

/**
 * Created by thongdt on 12/10/2015.
 */
public class HeaderBar extends RelativeLayout implements View.OnClickListener {

    private RelativeLayout mRlContain;
    private ImageView mImgMenu;
    private TextView mTvNews;
    private TextView mTvData;
    private TextView mTvLiveScore;

    private OnItemClickListener mOnItemClickListener;

    public HeaderBar(Context context) {
        super(context);
        init(context);
    }

    public HeaderBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mRlContain = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.custom_header_bar, this, false);
        addView(mRlContain);

        mImgMenu = (ImageView) mRlContain.findViewById(R.id.imgMenu);
        mImgMenu.setOnClickListener(this);

        mTvNews = (TextView) mRlContain.findViewById(R.id.tvNews);
        mTvNews.setOnClickListener(this);

        mTvData = (TextView) mRlContain.findViewById(R.id.tvData);
        mTvData.setOnClickListener(this);

        mTvLiveScore = (TextView) mRlContain.findViewById(R.id.tvLiveScore);
        mTvLiveScore.setOnClickListener(this);

        onClick(1);
    }

    private void onClick(int position) {


        if (position == 0) {

            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(0);
            }

        } else if (position == 1) {
            setDefaultHeaderBar();
            mTvNews.setTextColor(getResources().getColor(R.color.textview_header_bar_selected));
            mTvNews.setBackgroundColor(getResources().getColor(R.color.bg_header_bar_selected));

            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(1);
            }
        } else if (position == 2) {
            setDefaultHeaderBar();
            mTvData.setTextColor(getResources().getColor(R.color.textview_header_bar_selected));
            mTvData.setBackgroundColor(getResources().getColor(R.color.bg_header_bar_selected));
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(2);
            }
        } else if (position == 3) {
            setDefaultHeaderBar();
            mTvLiveScore.setTextColor(getResources().getColor(R.color.textview_header_bar_selected));
            mTvLiveScore.setBackgroundColor(getResources().getColor(R.color.bg_header_bar_selected));

            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(3);
            }
        }
    }

    private void setDefaultHeaderBar() {
        mTvData.setBackgroundColor(getResources().getColor(R.color.bg_header_bar_default));
        mTvData.setTextColor(getResources().getColor(R.color.textview_header_bar_default));

        mTvNews.setBackgroundColor(getResources().getColor(R.color.bg_header_bar_default));
        mTvNews.setTextColor(getResources().getColor(R.color.textview_header_bar_default));

        mTvLiveScore.setBackgroundColor(getResources().getColor(R.color.bg_header_bar_default));
        mTvLiveScore.setTextColor(getResources().getColor(R.color.textview_header_bar_default));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setBackgroundMenuDefault() {
        mImgMenu.setImageResource(R.drawable.ic_menu);
    }

    public void setBackgroundMenuSelected() {
        mImgMenu.setImageResource(R.drawable.ic_menu_selected);
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgMenu) {
            onClick(0);
        } else if (v.getId() == R.id.tvNews) {
            onClick(1);
        } else if (v.getId() == R.id.tvData) {
            onClick(2);
        } else if (v.getId() == R.id.tvLiveScore) {
            onClick(3);
        }
    }
}
