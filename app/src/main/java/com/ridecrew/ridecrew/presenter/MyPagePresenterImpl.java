package com.ridecrew.ridecrew.presenter;

import com.ridecrew.ridecrew.callback.MyPageModelCallback;
import com.ridecrew.ridecrew.model.MyPageModel;

import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;

/**
 * Created by KIM on 2017-12-27.
 */

public class MyPagePresenterImpl implements MyPagePresenter, MyPageModelCallback{

    private MyPageModel mModel;
    private MyPagePresenter.View mView;

    public MyPagePresenterImpl(MyPagePresenter.View view) {
        mView = view;
        mModel = new MyPageModel(this);
    }

    @Override
    public void loadAllScheduleData(Long memberId) {
        mModel.requestScheduleList(memberId);
    }

    @Override
    public void getAllScheduleData(ApiResult<List<Schedule>> apiResult) {
        mView.getAllScheduleData(apiResult);
    }
}
