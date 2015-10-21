package com.example.thongdt.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thongdt.myapplication.R;
import com.example.thongdt.myapplication.objects.InformationGame;

import java.util.ArrayList;

/**
 * Created by thongdt on 15/10/2015.
 */
public class DataRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<InformationGame> mInformationGames;
    private boolean mIsBackgroundDefault;


    public DataRecyclerViewAdapter(ArrayList<InformationGame> informationGames) {
        this.mInformationGames = informationGames;
    }

    @Override
    public int getItemViewType(int position) {
        return mInformationGames.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_header_data, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_data, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mInformationGames.get(position).getType() == 0) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.tvTitle.setText(mInformationGames.get(position).getTitle());
        } else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvDate.setText(mInformationGames.get(position).getDate());
            itemViewHolder.tvHomeClub.setText(mInformationGames.get(position).getNameHomeClub());
            itemViewHolder.tvAwayClub.setText(mInformationGames.get(position).getNameAwayClub());
            itemViewHolder.tvTime.setText(mInformationGames.get(position).getTime());

            if (mIsBackgroundDefault) {
                itemViewHolder.rlItem.setBackgroundColor(0xffD8D8D8);
                mIsBackgroundDefault = false;
            } else {
                itemViewHolder.rlItem.setBackgroundColor(0xffffffff);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mInformationGames.size();
    }



    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        TextView tvHomeClub;
        TextView tvAwayClub;
        TextView tvTime;
        RelativeLayout rlItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rlItem);
            tvDate = (TextView) itemView.findViewById(R.id.tvDatePlay);
            tvHomeClub = (TextView) itemView.findViewById(R.id.tvHomeClub);
            tvAwayClub = (TextView) itemView.findViewById(R.id.tvAwayClub);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitleHeader);
        }
    }

}
