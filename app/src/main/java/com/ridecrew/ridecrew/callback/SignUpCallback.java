package com.ridecrew.ridecrew.callback;

import Entity.ApiErrorCode;

/**
 * Created by KJH on 2017-12-30.
 */

public interface SignUpCallback {
    void getNetworkResponse(int code);
    void getNetworkResponse(String text, ApiErrorCode code);
}