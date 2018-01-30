package com.ridecrew.ridecrew.presenter;

import com.ridecrew.ridecrew.callback.NoticeModelCallback;
import com.ridecrew.ridecrew.model.NoticeModel;

import java.util.ArrayList;

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
        mView = view;
        mModel = new NoticeModel(this);
    }

    @Override
    public void loadAllNoticeData() { mModel.requestNoticeList(); }

    @Override
    public void addNoticeData(ArrayList<Notice> itemLists) {
        mModel.addNoticeList( Notice.builder() ); }

    @Override
    public void getAllNoticeDate(ApiResult<ArrayList<Notice>> apiResult) {
        mView.getAllNoticeData(apiResult);
    }
}
