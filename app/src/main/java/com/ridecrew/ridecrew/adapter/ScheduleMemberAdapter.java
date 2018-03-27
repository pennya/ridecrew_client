package com.ridecrew.ridecrew.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.callback.ScheduleMemberRecyclerViewCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import Entity.Member;
import Entity.ScheduleMember;
import de.hdodenhof.circleimageview.CircleImageView;

import static util.UtilsApp.dpToPx;
import static util.UtilsApp.pxToDp;

/**
 * Created by kim on 2018. 1. 11..
 */

public class ScheduleMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private ArrayList<ScheduleMember> mItemLists;
    private ScheduleMemberRecyclerViewCallback mCallback;
    private Activity context;
    private Queue<Integer> scheduleDeleteQueue;
    private boolean listVisible[];
    private HashMap<Integer, ArrayList<Member>> membersMap;

    public ScheduleMemberAdapter(Activity context, ScheduleMemberRecyclerViewCallback callback) {
        this.context = context;
        mCallback = callback;
        mItemLists = new ArrayList<>();
        scheduleDeleteQueue = new LinkedList<>();
        membersMap = new HashMap<>();
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

            viewHolder.btnShowList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!listVisible[holder.getAdapterPosition()]) {
                        // 스케줄에 해당되는 모든 데이터 가져오기
                        int currentPosition = holder.getAdapterPosition();
                        mCallback.showMembers(currentPosition,
                                mItemLists.get(currentPosition).getSchedule().getId());
                    } else {
                        listVisible[holder.getAdapterPosition()] = false;
                        notifyItemChanged(holder.getAdapterPosition());
                    }
                }
            });

            if(listVisible[holder.getAdapterPosition()]) {
                viewHolder.mLists.setVisibility(View.VISIBLE);

                ArrayList<Member> lists = null;
                lists = membersMap.get(holder.getAdapterPosition());

                LinearLayout root = viewHolder.mLists;
                root.removeAllViews();

                for(Member member : lists) {

                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    final CircleImageView circleImageView = new CircleImageView(context);

                    // placeHolder의 크기를 가져와서 profileImage를 같은 크기로 지정
                    // 사이즈를 구해오기위해 ImageVIew 객체를 생성하기때문에 리소스의 낭비가 있지만
                    // 지금 바로 해상도 별로 어떻게 Resize하는지 파악이 안되어있기 때문에 이와같이 작성
                    // 나중에 해상도별 사이즈 구하는 방법을 찾아보기
                    final ImageView imageView = new ImageView(context);
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_default_user_128_128));
                    imageView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

                    RequestOptions requestOptions = new RequestOptions()
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_default_user_128_128)
                            .circleCrop()
                            .override(imageView.getMeasuredWidth(), imageView.getMeasuredHeight());

                    String imagePath = member.getImageUrl();
                    Glide.with(context)
                            .load(imagePath)
                            .apply(requestOptions)
                            .into(circleImageView);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("PACKRIDING", "circleImageView : " + circleImageView.getWidth() + "," + circleImageView.getHeight());
                            Log.e("PACKRIDING", "imageView : " + imageView.getWidth() + "," + imageView.getHeight());
                        }
                    }, 2000);

                    TextView nickName = new TextView(context);
                    nickName.setText(member.getNickName());

                    linearLayout.addView(circleImageView);
                    linearLayout.addView(nickName);

                    root.addView(linearLayout);
                }

            } else {
                viewHolder.mLists.setVisibility(View.GONE);
            }

            viewHolder.btnShowList.setCompoundDrawablesWithIntrinsicBounds(
                    null,null,
                    ContextCompat.getDrawable(context, R.drawable.ic_expand_button), null);

            viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long scheduleId = mItemLists.get(holder.getAdapterPosition()).getSchedule().getId();
                    long memberId = mItemLists.get(holder.getAdapterPosition()).getMember().getId();
                    mCallback.cancelSchedule(holder.getAdapterPosition(), scheduleId, memberId);
                }
            });

            viewHolder.mTitle.setText(mItemLists.get(holder.getAdapterPosition()).getSchedule().getTitle());
            viewHolder.mAuthor.setText(mItemLists.get(holder.getAdapterPosition()).getMember().getNickName());

            try {
                String startTime =  mItemLists.get(holder.getAdapterPosition()).getSchedule().getStartTime();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date dt = sdf.parse(startTime);
                viewHolder.mStartTime.setText(new SimpleDateFormat("hh:mm aa").format(dt));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItemLists.size();
    }

    public void setmItemLists(ArrayList<ScheduleMember> mItemLists) {
        this.mItemLists = mItemLists;
        listVisible = new boolean[mItemLists.size()];
    }

    public void addDeletePosition(int itemPosition) {
        scheduleDeleteQueue.offer(itemPosition);
    }

    public void deletePositionValidate() {
        while(scheduleDeleteQueue.peek() != null) {
            Integer itemPosition = scheduleDeleteQueue.poll();
            if(itemPosition == null)
                return;

            mItemLists.remove(itemPosition.intValue());
            notifyDataSetChanged();

            if(mItemLists.size() == 0)
                mCallback.noScheduleValidate();
        }
    }

    public void setMemberLists(int currentItemPosition, ArrayList<Member> members) {
        membersMap.put(currentItemPosition, members);
        listVisible[currentItemPosition] = true;
        notifyItemChanged(currentItemPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected ViewGroup rootView;
        protected CardView mCardView;
        protected TextView mTitle;
        protected TextView mStartTime;
        protected TextView mAuthor;
        protected Button btnShowList;
        protected Button btnCancel;
        protected LinearLayout mLists;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (ViewGroup)itemView.findViewById(R.id.rootView);
            mCardView = (CardView)itemView.findViewById(R.id.card_view_activity_paricaption);
            mTitle = (TextView)itemView.findViewById(R.id.tv_activity_paricaption_title);
            mStartTime = (TextView)itemView.findViewById(R.id.tv_activity_paricaption_start_time);
            mAuthor = (TextView)itemView.findViewById(R.id.tv_activity_paricaption_author);
            btnShowList = (Button)itemView.findViewById(R.id.btn_activity_particapation_list);
            btnCancel = (Button)itemView.findViewById(R.id.btn_activity_particapation_cancel);
            mLists = (LinearLayout)itemView.findViewById(R.id.ll_activity_particapation_list);
        }
    }
}