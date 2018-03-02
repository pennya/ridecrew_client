package com.ridecrew.ridecrew.adapter;

import android.animation.ValueAnimator;
import android.app.Activity;
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
    private ArrayList<Integer> mHeightList;
    private NoticeRecyclerViewCallback mCallback;
    private int mOriginalHeight;
    private ArrayList<Boolean> mFlag;
    private Activity context;
    private int mPosition;
    private boolean mModifyFlag;
    private long id;


    public NoticeRecyclerViewAdapter(NoticeRecyclerViewCallback callback, Activity context) {
        this.context = context;
        mCallback = callback;
        mItemLists = new ArrayList<>();
        mExpands = new ArrayList<>();
        mHeightList = new ArrayList<>();
        mOriginalHeight = 0;
        mFlag = new ArrayList<>();
        mModifyFlag = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notice_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final int itemPosition = position;
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            //초기 화면
            viewHolder.mConstraintLayout.setVisibility(View.VISIBLE);
            viewHolder.mImgType.setVisibility(View.VISIBLE);
            viewHolder.mConstraintLayout.setEnabled(false);
            mCallback.deleteVisible(viewHolder.mDelete);
            //onScrolled이벤트 발생 시에 속성값 유지
            if (mFlag.get(itemPosition).booleanValue()) {
                viewHolder.mConstraintLayout.setVisibility(View.VISIBLE);
                viewHolder.mImgType.setVisibility(View.VISIBLE);
                viewHolder.mConstraintLayout.setEnabled(false);
                viewHolder.mContents.setVisibility(View.VISIBLE);
                mFlag.set(itemPosition, true);
            }
            viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View view) {
                    //수정기능이 활성화일 때
                    if (mModifyFlag) {
                        id = mItemLists.get(itemPosition).getId();
                        mPosition = position;
                        mCallback.modifyFunction(context,id);
                        return;
                    }
                    viewHolder.mContents.setVisibility(view.VISIBLE);
                    // mOriginalHeight 초기화 작업이 이루어 지지 않으면 리스트마다 크기가 다름
                    if (mHeightList.get(itemPosition) == 0) {
                        mOriginalHeight = view.getHeight();
                        viewHolder.mContents.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                        mHeightList.set(itemPosition, viewHolder.mContents.getMeasuredHeight());
                    }

                    //card view가 접혀있을 때 펼치는 애니메이션
                    viewHolder.mConstraintLayout.setVisibility(View.VISIBLE);
                    ValueAnimator valueAnimator;
                    if (mExpands.get(itemPosition) == false) {
                        viewHolder.mImgArrow.setImageResource(R.drawable.ic_action_arrow_up);
                        valueAnimator = ValueAnimator.ofInt(mOriginalHeight, mOriginalHeight + mHeightList.get(itemPosition) + 30);
                        mExpands.set(itemPosition, true);
                    } else {    //card view가 펼쳐져 있을 때 접는 애니메이션
                        viewHolder.mImgArrow.setImageResource(R.drawable.ic_action_arrow_down);
                        valueAnimator = ValueAnimator.ofInt(mOriginalHeight + mHeightList.get(itemPosition) + 30, mOriginalHeight);
                        Animation animation = new AlphaAnimation(1.00f, 1.00f);
                        animation.setDuration(140);
                        mExpands.set(itemPosition, false);

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
                    valueAnimator.setDuration(100);
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
            switch (mItemLists.get(itemPosition).getType()) {
                case 0: //공지
                    viewHolder.mImgType.setImageResource(R.drawable.ic_type_notice);
                    break;
                case 1: //이벤트
                    viewHolder.mImgType.setImageResource(R.drawable.ic_type_event);
                    break;
                case 2: //업데이트
                    viewHolder.mImgType.setImageResource(R.drawable.ic_type_update);
                    break;
            }
            viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.deleteFucntion(itemPosition);
                }
            });
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
        protected TextView mCreateDateTime;
        protected TextView mImgUrl;
        protected TextView mWebUrl;
        protected TextView mLastModifiedDateTime;
        protected ImageView mImgArrow;
        protected ImageView mImgType;
        protected ConstraintLayout mConstraintLayout;
        protected ImageView mDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view_fragment_notice_recycler_view);
            mTitle = (TextView) itemView.findViewById(R.id.tv_fragment_notice_recycler_view_title);
            mContents = (TextView) itemView.findViewById(R.id.tv_fragment_notice_recycler_view_contents);
            mCreateDateTime = (TextView) itemView.findViewById(R.id.tv_fragment_notice_recycler_view_createdDateTime);
            mImgArrow = (ImageView) itemView.findViewById(R.id.img_arrow);
            mConstraintLayout = (ConstraintLayout) itemView.findViewById(R.id.cl_fragment_notice_layout);
            mImgType = (ImageView) itemView.findViewById(R.id.img_type_notice);
            mDelete = (ImageView) itemView.findViewById(R.id.img_delete);
        }
    }

    public ArrayList<Notice> getmItemLists() {
        return mItemLists;
    }


    public void setmItemLists(ArrayList<Notice> mItemLists) {
        this.mItemLists = mItemLists;
        mExpands.addAll(Collections.nCopies(mItemLists.size(), Boolean.FALSE));
        mFlag.addAll(Collections.nCopies(mItemLists.size(), Boolean.FALSE));
        mHeightList.addAll(Collections.nCopies(mItemLists.size(), 0));
    }

    public ArrayList<Boolean> getterExpand() { return mExpands; }

    public void setterFlag(ArrayList<Boolean> flag) {
        this.mFlag = flag;
    }

    public int getterPosition() { return mPosition; }

    public void setterModifyFlag(boolean flag) {this.mModifyFlag = flag;}
}

