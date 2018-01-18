package com.ridecrew.ridecrew.callback;

import Entity.ApiErrorCode;
import Entity.ApiResult;

/**
 * Created by KIM on 2018-01-16.
 */

public interface ModelCallback<S, M> {
    void getSingleNetworkResponse(ApiResult<S> result, int status);
    void getMultiNetworkResponse(ApiResult<M> result, int status);
    void getVoidNetworkResponse(ApiResult<Void> result, int status);
    void getErrorNetworkResponse(String msg, ApiErrorCode code);
}
