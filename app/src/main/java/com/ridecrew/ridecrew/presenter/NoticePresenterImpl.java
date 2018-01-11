package com.ridecrew.ridecrew.presenter;

import com.ridecrew.ridecrew.callback.NoticeModelCallback;
import com.ridecrew.ridecrew.model.NoticeModel;

import java.util.List;

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
    public void loadAllScheduleData(String date) {

    }

    @Override
    public void getAllNoticeData(ApiResult<List<Notice>> apiResult) {

    }
}
