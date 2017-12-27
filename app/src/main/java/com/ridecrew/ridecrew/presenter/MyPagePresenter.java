package com.ridecrew.ridecrew.presenter;

import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;

/**
 * Created by KIM on 2017-12-27.
 */

public interface MyPagePresenter {
    void loadAllScheduleData(Long memberId);

    interface View {

        void getAllScheduleData(ApiResult<List<Schedule>> apiResult);

    }
}
