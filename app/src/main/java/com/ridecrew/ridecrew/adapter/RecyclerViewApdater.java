package com.ridecrew.ridecrew.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.callback.ScheduleRecyclerViewCallback;

import java.util.ArrayList;

import Entity.Schedule;

/**
 * Created by kim on 2017. 12. 25..
 */

public class RecyclerViewApdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Schedule> mItemLists;
    private ScheduleRecyclerViewCallback mCallback;

    public RecyclerViewApdater(ScheduleRecyclerViewCallback callback) {
        mCallback = callback;
        mItemLists = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_schedule_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int itemPosition = position;

        if ( holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    mCallback.showItem(itemPosition);
                }
            });

            viewHolder.mTitle.setText(mItemLists.get(itemPosition).getTitle());
            viewHolder.mDestination.setText(mItemLists.get(itemPosition).getDescriptions());
            viewHolder.mAuthor.setText(mItemLists.get(itemPosition).getMember().getNickName());
        }
    }

    @Override
    public int getItemCount() {
        return mItemLists.size();
    }

    public void setArrayList(ArrayList<Schedule> itemLists) {
        this.mItemLists = itemLists;
    }

    public void clear() {
        mItemLists.clear();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mDestination;
        private TextView mAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView)itemView.findViewById(R.id.tv_fragment_schedule_recycler_view_title);
            mDestination = (TextView)itemView.findViewById(R.id.tv_fragment_schedule_recycler_view_destination);
            mAuthor = (TextView)itemView.findViewById(R.id.tv_fragment_schedule_recycler_view_author);
        }
    }
}
