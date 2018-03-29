package com.ridecrew.ridecrew.callback;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Schedule;
import Entity.ScheduleMember;

/**
 * Created by KJH on 2018-01-02.
 */

public interface ScheduleEnrollCallback {
    void getNetworkResponse(ApiResult<Schedule> result, int status);
    void getNetworkResponse(String msg, ApiErrorCode code);
    void getNetworkResponseEx(ApiResult<ScheduleMember> result, int status);
}
