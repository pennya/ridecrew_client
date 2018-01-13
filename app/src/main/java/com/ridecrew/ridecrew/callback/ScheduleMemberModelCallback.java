package com.ridecrew.ridecrew.callback;

import java.util.List;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.ScheduleMember;

/**
 * Created by kim on 2018. 1. 11..
 */

public interface ScheduleMemberModelCallback {
    void getAddNetworkResponse(ApiResult<ScheduleMember> member, int status);
    void getDeleteNetworkResponse(ApiResult<Void> member, int status);
    void getNetworkResponseEx(ApiResult<List<ScheduleMember>> member, int status);
    void getNetworkResponse(String msg, ApiErrorCode code);
}
