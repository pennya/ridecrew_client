package com.ridecrew.ridecrew.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.callback.GalleryCallback;
import com.ridecrew.ridecrew.ui.GalleryFragment;

import java.util.ArrayList;

import Define.DefineValue;
import Entity.Gallery;
import util.SharedUtils;

/**
 * Created by KJH on 2018-01-16.
 */

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<Gallery> items;
    public Activity context;
    private GalleryCallback callback;

    public GalleryAdapter(Activity context, GalleryCallback callback) {
        this.context = context;
        this.callback = callback;
        items = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if( holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            if(SharedUtils.getBooleanValue(context, DefineValue.IS_LOGIN)) {
                viewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callback.like(items.get(viewHolder.getAdapterPosition()));
                    }
                });

                viewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callback.share(items.get(viewHolder.getAdapterPosition()));
                    }
                });
            }

            RequestOptions requestOptions = new RequestOptions()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_default_user_128_128)
                    .circleCrop();

            String imagePath = items.get(position).getMember().getImageUrl();
            Glide.with(context)
                    .load(imagePath)
                    .apply(requestOptions)
                    .into(viewHolder.userIcon);


            // main image
            requestOptions = new RequestOptions()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            imagePath = items.get(position).getImageUrl();
            Glide.with(context)
                    .load(imagePath)
                    .apply(requestOptions)
                    .into(viewHolder.mainImage);

            if( items.get(position).getMember() == null)
                viewHolder.userId.setText("이름없음");
            else
                viewHolder.userId.setText(items.get(position).getMember().getNickName());

            viewHolder.likeCount.setText("" + items.get(position).getLikeCount());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected CardView cardView;
        protected CircularImageView userIcon;
        protected TextView userId;
        protected ImageView likeIcon;
        protected TextView likeCount;
        protected ImageView mainImage;
        protected Button btnLike;
        protected Button btnShare;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_fragment_gallery_item);
            userIcon = (CircularImageView) itemView.findViewById(R.id.iv_fragment_gallery_item_user_icon);
            userId = (TextView) itemView.findViewById(R.id.tv_fragment_gallery_item_user_id);
            likeIcon = (ImageView) itemView.findViewById(R.id.iv_fragment_gallery_item_like_icon);
            likeCount = (TextView) itemView.findViewById(R.id.tv_fragment_gallery_item_like_count);
            mainImage = (ImageView) itemView.findViewById(R.id.iv_fragment_gallery_item_main_image);
            btnLike = (Button) itemView.findViewById(R.id.btn_fragment_gallery_item_like);
            btnShare = (Button) itemView.findViewById(R.id.btn_fragment_gallery_item_share);
        }
    }
}