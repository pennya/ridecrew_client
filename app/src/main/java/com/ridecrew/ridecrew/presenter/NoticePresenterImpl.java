package com.ridecrew.ridecrew.presenter;

import com.ridecrew.ridecrew.callback.NoticeModelCallback;
import com.ridecrew.ridecrew.model.NoticeModel;
import com.ridecrew.ridecrew.ui.NoticeAddActivity;

import java.util.ArrayList;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public class NoticePresenterImpl extends NoticeAddActivity implements NoticePresenter, NoticeModelCallback {
    private NoticeModel mModel;
    private NoticePresenter.View mView;

    public NoticePresenterImpl(NoticePresenter.View view) {
        mView = view;
        mModel = new NoticeModel(this);
    }

    @Override
    public void loadAllNoticeData() { mModel.requestNoticeList(); }

    @Override
    public void addNoticeData(ArrayList<Notice> itemList) { mModel.addNoticeList(Notice.builder().setTitle("title").setContent("contents")); }

    @Override
    public void deleteNoticeData(Long noticeId) {
        mModel.deleteNoticeList(noticeId);
    }

    @Override
    public void getAllNoticeDate(ApiResult<ArrayList<Notice>> apiResult) {
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

}
