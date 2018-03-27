package com.ridecrew.ridecrew.adapter;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.callback.ScheduleMemberRecyclerViewCallback;

import java.util.ArrayList;

import Entity.ScheduleMember;

/**
 * Created by kim on 2018. 1. 11..
 */

public class ScheduleMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private ArrayList<ScheduleMember> mItemLists;
    private ScheduleMemberRecyclerViewCallback mCallback;
    private Activity context;
    private boolean listVisible[];
    private ValueAnimator animator[];
    private int expandedFrameHeight[];

    public ScheduleMemberAdapter(Activity context, ScheduleMemberRecyclerViewCallback callback) {
        this.context = context;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final int itemPosition = position;

        if ( holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;

            //
            viewHolder.btnShowList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!listVisible[holder.getAdapterPosition()]) {
                        listVisible[holder.getAdapterPosition()] = true;
                        notifyItemChanged(holder.getAdapterPosition());
                    } else {
                        listVisible[holder.getAdapterPosition()] = false;
                        notifyItemChanged(holder.getAdapterPosition());
                    }
                }
            });

            if(listVisible[holder.getAdapterPosition()]) {
                viewHolder.mLists.setText("TEST\nTEST\nTEST\nTEST");
                viewHolder.mLists.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mLists.setText("");
                viewHolder.mLists.setVisibility(View.GONE);
            }



            //
            viewHolder.btnShowList.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(context, R.drawable.ic_action_arrow_down),
                    null, null, null);



            //
            viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.cancelSchedule(holder.getAdapterPosition());
                }
            });



            //
            viewHolder.mTitle.setText(mItemLists.get(holder.getAdapterPosition()).getSchedule().getTitle());
            viewHolder.mDestination.setText(mItemLists.get(holder.getAdapterPosition()).getSchedule().getDescriptions());
            viewHolder.mAuthor.setText(mItemLists.get(holder.getAdapterPosition()).getMember().getNickName());
        }
    }

    @Override
    public int getItemCount() {
        return mItemLists.size();
    }



    public void setmItemLists(ArrayList<ScheduleMember> mItemLists) {
        this.mItemLists = mItemLists;
        listVisible = new boolean[mItemLists.size()];
        expandedFrameHeight = new int[mItemLists.size()];
        animator = new ValueAnimator[mItemLists.size()];
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected ViewGroup rootView;
        protected CardView mCardView;
        protected TextView mTitle;
        protected TextView mDestination;
        protected TextView mAuthor;
        protected Button btnShowList;
        protected Button btnCancel;
        protected TextView mLists;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (ViewGroup)itemView.findViewById(R.id.rootView);
            mCardView = (CardView)itemView.findViewById(R.id.card_view_activity_paricaption);
            mTitle = (TextView)itemView.findViewById(R.id.tv_activity_paricaption_title);
            mDestination = (TextView)itemView.findViewById(R.id.tv_activity_paricaption_destination);
            mAuthor = (TextView)itemView.findViewById(R.id.tv_activity_paricaption_author);
            btnShowList = (Button)itemView.findViewById(R.id.btn_activity_particapation_list);
            btnCancel = (Button)itemView.findViewById(R.id.btn_activity_particapation_cancel);
            mLists = (TextView)itemView.findViewById(R.id.tv_activity_particapation_list);
        }
    }
}
