package com.ridecrew.ridecrew.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.callback.NoticeRecyclerViewCallback;
import java.util.ArrayList;

import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public class NoticeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Notice> mItemLists;
    private NoticeRecyclerViewCallback mCallback;

    public NoticeRecyclerViewAdapter(NoticeRecyclerViewCallback callback) {
        mCallback = callback;
        mItemLists = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notice_recyclerview_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int itemPosition = position;

        if(holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder)holder;
            viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    mCallback.showItem(itemPosition);
                    viewHolder.mContents.setText(mItemLists.get(itemPosition).getContent());
                    viewHolder.mImgUp.setImageResource(R.drawable.ic_action_arrow_up);
                    viewHolder.mImgDown.setVisibility(View.INVISIBLE);
                }
            });
            viewHolder.mTitle.setText(mItemLists.get(itemPosition).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mItemLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView mCardView;
        protected TextView mTitle;
        protected TextView mContents;
        private ImageView mImgUp;
        private ImageView mImgDown;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.card_view_fragment_notice_recycler_view);
            mTitle = (TextView)itemView.findViewById(R.id.tv_fragment_notice_recycler_view_title);
            mContents = (TextView)itemView.findViewById(R.id.tv_fragment_notice_recycler_view_contents);
            mImgUp = (ImageView)itemView.findViewById(R.id.img_arrow_up);
            mImgDown = (ImageView)itemView.findViewById(R.id.img_arrow_down);
        }
    }

    public ArrayList<Notice> getmItemLists() {
        return mItemLists;
    }

    public void setmItemLists(ArrayList<Notice> mItemLists) {
        this.mItemLists = mItemLists;
    }

}
