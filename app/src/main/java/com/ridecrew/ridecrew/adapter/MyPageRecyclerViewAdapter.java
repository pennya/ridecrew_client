package com.ridecrew.ridecrew.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.callback.MyPageRecyclerViewCallback;

import java.util.ArrayList;

import Entity.Schedule;

/**
 * Created by KIM on 2017-12-27.
 */

public class MyPageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Schedule> mItemLists;
    private MyPageRecyclerViewCallback mCallback;

    public MyPageRecyclerViewAdapter(MyPageRecyclerViewCallback callback) {
        mCallback = callback;
        mItemLists = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mypage_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int itemPosition = position;

        if ( holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.title.setText(mItemLists.get(itemPosition).getTitle());
            viewHolder.date.setText(mItemLists.get(itemPosition).getDate());
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.delete(mItemLists.get(itemPosition).getId());
                }
            });
            viewHolder.modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.modify(mItemLists.get(itemPosition).getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItemLists.size();
    }

    public void setArrayList(ArrayList<Schedule> itemLists) {
        this.mItemLists = itemLists;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;
        private Button delete;
        private Button modify;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.tv_fragment_mypage_title);
            date = (TextView)itemView.findViewById(R.id.tv_fragment_mypage_date);
            delete = (Button)itemView.findViewById(R.id.btn_fragment_mypage_delete);
            modify = (Button)itemView.findViewById(R.id.btn_fragment_mypage_modify);
        }
    }
}
