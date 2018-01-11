package com.ridecrew.ridecrew.callback;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Schedule;

/**
 * Created by KJH on 2018-01-02.
 */

public interface ScheduleEnrollCallback {
    void getNetworkResponse(ApiResult<Schedule> schedule, int status);
    void getNetworkResponse(String msg, ApiErrorCode code);
}
