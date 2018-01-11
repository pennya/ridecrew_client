package com.ridecrew.ridecrew.callback;

import java.util.List;

import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public interface NoticeModelCallback {
    void getAllNoticeData(ApiResult<List<Notice>> apiResult);
}
