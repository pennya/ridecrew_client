package com.ridecrew.ridecrew.callback;

import java.util.ArrayList;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public interface NoticeModelCallback {
    void getNoticeData(ApiResult<Notice> apiResult);
    void getAllNoticeData(ApiResult<ArrayList<Notice>> apiResult);
    void getDeleteNetworkResponse(ApiResult<Void> notice, int status);
    void getNetWorkResponse(String msg, ApiErrorCode code);
}
