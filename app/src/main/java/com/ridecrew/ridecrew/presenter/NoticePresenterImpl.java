package com.ridecrew.ridecrew.presenter;

import android.app.Activity;

import com.ridecrew.ridecrew.callback.NoticeModelCallback;
import com.ridecrew.ridecrew.model.NoticeModel;
import com.ridecrew.ridecrew.ui.NoticeAddActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public class NoticePresenterImpl implements NoticePresenter, NoticeModelCallback {
    private NoticeModel mModel;
    private NoticePresenter.View mView;

    public NoticePresenterImpl(NoticePresenter.View view) {
        this.mView = view;
        this.mModel = new NoticeModel(this);
    }

    @Override
    public void loadAllNoticeData() { mModel.requestNoticeList(); }

    @Override
    public void addNoticeData(Notice itemList) {
        mModel.addNoticeList(itemList);
    }

    @Override
    public void deleteNoticeData(Long noticeId) {
        mModel.deleteNoticeList(noticeId);
    }

    @Override
    public void getNoticeData(ApiResult<Notice> apiResult) {
        mView.getNoticeData(apiResult);
    }

    @Override
    public void getAllNoticeData(ApiResult<ArrayList<Notice>> apiResult) {
        Collections.sort(apiResult.getData(), new AscendingObj());
        mView.getAllNoticeData(apiResult);
    }

    @Override
    public void getDeleteNetworkResponse(ApiResult<Void> notice, int status) {
        mView.showToast("삭제 완료");
    }

    @Override
    public void getNetWorkResponse(String msg, ApiErrorCode code) {
        switch (code) {
            default:
                mView.showToast(msg);
                break;
        }
    }
    class AscendingObj implements Comparator<Notice> {

        @Override
        public int compare(Notice o1, Notice o2) {
            //생성시간 오름차순
            return o2.getCreatedDateTime().compareTo(o1.getCreatedDateTime());
        }
    }

}
