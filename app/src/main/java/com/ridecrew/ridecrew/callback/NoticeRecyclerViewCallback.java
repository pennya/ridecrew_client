package com.ridecrew.ridecrew.callback;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public interface NoticeRecyclerViewCallback {
    void deleteVisible(ImageView imageView);
    void deleteFucntion(int position);
    void modifyFunction(Activity context,long id);
}
