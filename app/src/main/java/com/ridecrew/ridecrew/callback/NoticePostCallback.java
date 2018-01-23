package com.ridecrew.ridecrew.callback;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 1. 23..
 */

public interface NoticePostCallback {
    void getNetworkResponse(ApiResult<Notice> notice, int status);
    void getNetworkResponse(String msg, ApiErrorCode code);
}
