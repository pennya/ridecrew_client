package com.ridecrew.ridecrew.adapter;

import android.animation.ValueAnimator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.callback.NoticeRecyclerViewCallback;

import java.util.ArrayList;
import java.util.Collections;

import Entity.Notice;


/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public class NoticeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Notice> mItemLists;
    private ArrayList<Boolean> mExpands;
    private NoticeRecyclerViewCallback mCallback;
    private int mOriginalHeight;

    public NoticeRecyclerViewAdapter(NoticeRecyclerViewCallback callback) {
        mCallback = callback;
        mItemLists = new ArrayList<>();
        mExpands = new ArrayList<>();
        mOriginalHeight = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notice_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final int itemPosition = position;
        mExpands.addAll(Collections.nCopies(mItemLists.size(),Boolean.FALSE));

        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            if(mExpands.get(itemPosition) == false) {
                viewHolder.mConstraintLayout.setVisibility(View.VISIBLE);
                viewHolder.mConstraintLayout.setEnabled(false);
            }
            viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View view) {
                    mCallback.showItem(itemPosition);
                    if (mOriginalHeight == 0) {
                        mOriginalHeight = view.getHeight();
                    }
                    ValueAnimator valueAnimator;
                    //card view가 접혀있을 때 펼치는 애니메이션
                    if(mExpands.get(itemPosition)==false) {
                        viewHolder.mContents.setVisibility(view.VISIBLE);
                        viewHolder.mImgArrow.setImageResource(R.drawable.ic_action_arrow_up);
                        viewHolder.mCreateDateTime.setVisibility(view.VISIBLE);
                        valueAnimator = ValueAnimator.ofInt(mOriginalHeight,mOriginalHeight+(int)(mOriginalHeight*3.0));
                        mExpands.set(itemPosition,true);
                    } else {    //card view가 펼쳐져 있을 때 접는 애니메이션
                        viewHolder.mImgArrow.setImageResource(R.drawable.ic_action_arrow_down);
                        valueAnimator = ValueAnimator.ofInt(mOriginalHeight + (int) (mOriginalHeight * 3.0), mOriginalHeight);
                        Animation animation = new AlphaAnimation(1.00f, 1.00f);
                        animation.setDuration(100);
                        mExpands.set(itemPosition,false);

                        animation.setAnimationListener(new Animation.AnimationListener() {

                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                viewHolder.mConstraintLayout.setVisibility(View.VISIBLE);
                                viewHolder.mConstraintLayout.setEnabled(true);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        viewHolder.mConstraintLayout.startAnimation(animation);
                    }
                    valueAnimator.setDuration(500);
                    valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Integer value = (Integer) animation.getAnimatedValue();
                            view.getLayoutParams().height = value.intValue();
                            view.requestLayout();
                        }
                    });
                    valueAnimator.start();
                }
            });
            viewHolder.mTitle.setText(mItemLists.get(itemPosition).getTitle());
            viewHolder.mContents.setText(mItemLists.get(itemPosition).getContent());
            viewHolder.mCreateDateTime.setText(mItemLists.get(itemPosition).getCreatedDateTime());
            viewHolder.mImgArrow.setImageResource(R.drawable.ic_action_arrow_down);
        }
    }

    @Override
    public int getItemCount() {
        return mItemLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView mCardView;
        protected int mType;
        protected TextView mTitle;
        protected TextView mContents;
        protected TextView mImgUrl;
        protected TextView mWebUrl;
        protected TextView mCreateDateTime;
        protected TextView mLastModifiedDateTime;
        protected ImageView mImgArrow;
        protected ConstraintLayout mConstraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view_fragment_notice_recycler_view);
            mTitle = (TextView) itemView.findViewById(R.id.tv_fragment_notice_recycler_view_title);
            mContents = (TextView) itemView.findViewById(R.id.tv_fragment_notice_recycler_view_contents);
            mCreateDateTime = (TextView)itemView.findViewById(R.id.tv_fragment_notice_recycler_view_createdDateTime);
            mImgArrow = (ImageView) itemView.findViewById(R.id.img_arrow);
            mConstraintLayout = (ConstraintLayout)itemView.findViewById(R.id.cl_fragment_notice_layout);
        }
    }

    public ArrayList<Notice> getmItemLists() {
        return mItemLists;
    }

    public void setmItemLists(ArrayList<Notice> mItemLists) {
        this.mItemLists = mItemLists;
    }

}
