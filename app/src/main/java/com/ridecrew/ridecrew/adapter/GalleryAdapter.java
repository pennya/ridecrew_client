package com.ridecrew.ridecrew.adapter;

import android.app.Activity;
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
import com.ridecrew.ridecrew.ui.GalleryFragment;

import java.util.ArrayList;

/**
 * Created by KJH on 2018-01-16.
 */

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<GalleryFragment.GalleryEntity> items;
    public Activity context;

    public GalleryAdapter(Activity context) {
        this.context = context;
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
            ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            viewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            viewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            viewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            RequestOptions requestOptions = new RequestOptions()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            String imagePath = items.get(position).getImageUrl();
            Glide.with(context)
                    .load(imagePath)
                    .apply(requestOptions)
                    .into(viewHolder.mainImage);

            viewHolder.userId.setText(items.get(position).getUserId());
            viewHolder.likeCount.setText(items.get(position).getLikeCount() + "");
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
        protected Button btnMore;

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
            btnMore = (Button) itemView.findViewById(R.id.btn_fragment_gallery_item_more);
        }
    }
}