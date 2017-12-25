package com.ridecrew.ridecrew.callback;

import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;

/**
 * Created by kim on 2017. 12. 25..
 */

public interface ScheduleModelCallback {
    void getAllScheduleData(ApiResult<List<Schedule>> apiResult);
}
