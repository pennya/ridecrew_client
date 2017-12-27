package com.ridecrew.ridecrew.presenter;

import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;

/**
 * Created by kim on 2017. 12. 25..
 */

public interface SchedulePresenter {

    void loadAllScheduleData(String date);

    interface View {

        void getAllScheduleData(ApiResult<List<Schedule>> apiResult);

    }
}
