package com.ridecrew.ridecrew.presenter;

import com.ridecrew.ridecrew.callback.NoticeRecyclerViewCallback;

import java.util.ArrayList;

import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public interface NoticePresenter {
    void loadAllNoticeData();
    void getNoticeData(ApiResult<Notice> apiResult);
    void addNoticeData(Notice notice);
    void deleteNoticeData(Long noticeId);
    void modifyNoticeData(Long noticeId, Notice notice);

    interface View {
        void getNoticeData(ApiResult<Notice> apiResult);
        void getAllNoticeData(ApiResult<ArrayList<Notice>> apiResult);
        void showToast(String text);
    }
}
