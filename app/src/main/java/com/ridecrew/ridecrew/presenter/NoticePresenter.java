package com.ridecrew.ridecrew.presenter;

import java.util.List;

import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public interface NoticePresenter {
    void loadAllNoticeData(String data);

    interface View {

        void getAllNoticeData(ApiResult<List<Notice>> apiResult);

    }
}
