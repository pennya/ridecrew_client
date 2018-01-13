package com.ridecrew.ridecrew.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.callback.ScheduleMemberRecyclerViewCallback;

import java.util.ArrayList;

import Entity.ScheduleMember;

/**
 * Created by kim on 2018. 1. 11..
 */

public class ScheduleMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public ArrayList<ScheduleMember> mItemLists;
    private ScheduleMemberRecyclerViewCallback mCallback;

    public ScheduleMemberAdapter(ScheduleMemberRecyclerViewCallback callback) {
        mCallback = callback;
        mItemLists = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_particapation_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int itemPosition = position;

        if ( holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    mCallback.showItem(itemPosition);
                }
            });

            viewHolder.mTitle.setText(mItemLists.get(itemPosition).getSchedule().getTitle());
            viewHolder.mDestination.setText(mItemLists.get(itemPosition).getSchedule().getDescriptions());
            viewHolder.mAuthor.setText(mItemLists.get(itemPosition).getMember().getNickName());
        }
    }

    @Override
    public int getItemCount() {
        return mItemLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView mCardView;
        protected TextView mTitle;
        protected TextView mDestination;
        protected TextView mAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.card_view_activity_paricaption);
            mTitle = (TextView)itemView.findViewById(R.id.tv_activity_paricaption_title);
            mDestination = (TextView)itemView.findViewById(R.id.tv_activity_paricaption_destination);
            mAuthor = (TextView)itemView.findViewById(R.id.tv_activity_paricaption_author);
        }
    }
}
