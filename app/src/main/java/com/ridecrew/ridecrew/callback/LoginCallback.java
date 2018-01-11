package com.ridecrew.ridecrew.callback;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Member;

/**
 * Created by KJH on 2017-12-30.
 */

public interface LoginCallback {
    void getNetworkResponse(ApiResult<Member> member, int status);
    void getNetworkResponse(String msg, ApiErrorCode code);
}
