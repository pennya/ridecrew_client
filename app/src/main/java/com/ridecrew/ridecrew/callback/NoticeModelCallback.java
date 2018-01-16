package com.ridecrew.ridecrew.callback;

import java.util.ArrayList;

import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public interface NoticeModelCallback {
    void getAllNoticeDate(ApiResult<ArrayList<Notice>> apiResult);
}
