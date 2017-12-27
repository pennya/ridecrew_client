package com.ridecrew.ridecrew.presenter;

import com.ridecrew.ridecrew.callback.ScheduleModelCallback;
import com.ridecrew.ridecrew.model.ScheduleModel;

import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;

/**
 * Created by kim on 2017. 12. 25..
 */

public class SchedulePresenterImpl implements SchedulePresenter, ScheduleModelCallback{

    private ScheduleModel mModel;
    private SchedulePresenter.View mView;

    public SchedulePresenterImpl(SchedulePresenter.View view) {
        mView = view;
        mModel = new ScheduleModel(this);
    }

    @Override
    public void loadAllScheduleData(String date) {
        mModel.requestScheduleList(date);
    }

    @Override
    public void getAllScheduleData(ApiResult<List<Schedule>> apiResult) {
        mView.getAllScheduleData(apiResult);
    }
}
