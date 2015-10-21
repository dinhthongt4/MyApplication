package com.example.thongdt.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thongdt.myapplication.R;
import com.example.thongdt.myapplication.objects.Menu;

import java.util.ArrayList;

/**
 * Created by thongdt on 14/10/2015.
 */
public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Menu> mMenus;
    private OnItemClickListener mOnItemClickListener;
    private static final int COLOR_SELECTED = 0xff726E7E;

    public MenuRecyclerViewAdapter(ArrayList<Menu> menus) {
        this.mMenus = menus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_drawer_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(mMenus.get(position).getTitle());
        if (mMenus.get(position).isSelected()) {
            holder.rlItemView.setBackgroundColor(COLOR_SELECTED);
        }
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        RelativeLayout rlItemView;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvMenu);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClick(getPosition());
                    }
                }
            });
            rlItemView = (RelativeLayout) itemView;
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
