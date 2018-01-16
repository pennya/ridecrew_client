package com.ridecrew.ridecrew.model;


import android.telecom.Call;

import com.ridecrew.ridecrew.callback.NoticeModelCallback;

import java.util.ArrayList;

import Entity.Notice;


/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public class NoticeModel {
    private NoticeModelCallback mCallback;

    public NoticeModel(NoticeModelCallback callback) {
        mCallback = callback;
    }

    public void requestNoticeList(String data) {

    }
}
