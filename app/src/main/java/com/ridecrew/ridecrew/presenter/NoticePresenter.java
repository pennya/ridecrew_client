package com.ridecrew.ridecrew.presenter;

import java.util.ArrayList;

import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public interface NoticePresenter {
    void loadAllNoticeData();
    void addNoticeData(ArrayList<Notice> itemList);
    void deleteNoticeData(Long noticeId);
    interface View {
        void getAllNoticeData(ApiResult<ArrayList<Notice>> apiResult);
        void showToast(String text);
    }
}
