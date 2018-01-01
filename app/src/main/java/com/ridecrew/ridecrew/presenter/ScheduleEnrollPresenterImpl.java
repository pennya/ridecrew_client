package com.ridecrew.ridecrew.presenter;

import com.ridecrew.ridecrew.callback.ScheduleEnrollCallback;
import com.ridecrew.ridecrew.model.ScheduleEnrollModel;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Schedule;

/**
 * Created by KJH on 2018-01-02.
 */

public class ScheduleEnrollPresenterImpl implements ScheduleEnrollPresenter, ScheduleEnrollCallback {

    private ScheduleEnrollPresenter.View mView;
    private ScheduleEnrollModel mModel;

    public ScheduleEnrollPresenterImpl(ScheduleEnrollPresenter.View view) {
        mView = view;
        mModel = new ScheduleEnrollModel(this);
    }

    @Override
    public void scheduleEnroll(Schedule schedule) {
        mModel.scheduleEnroll(schedule);
    }

    @Override
    public void getNetworkResponse(ApiResult<Schedule> schedule, int status) {
        mView.moveActivity();
    }

    @Override
    public void getNetworkResponse(String msg, ApiErrorCode code) {
        mView.showToast(msg);
    }
}
