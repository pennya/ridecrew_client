package com.ridecrew.ridecrew.model;


import com.ridecrew.ridecrew.callback.NoticeModelCallback;

import java.util.ArrayList;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public class NoticeModel {
    private NoticeModelCallback mCallback;


    public NoticeModel(NoticeModelCallback callback) {
        mCallback = callback;
    }
    public void requestNoticeList(String data) {
        ArrayList<String> notices = new ArrayList<>();
        notices.add(0,"aaaa");
    }
}
