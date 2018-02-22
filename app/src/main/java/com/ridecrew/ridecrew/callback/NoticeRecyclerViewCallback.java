package com.ridecrew.ridecrew.callback;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public interface NoticeRecyclerViewCallback {
    void deleteVisible(ImageView imageView);
    void deleteFucntion(int position);
}
