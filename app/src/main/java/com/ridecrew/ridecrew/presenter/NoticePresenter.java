package com.ridecrew.ridecrew.presenter;

import java.util.List;

import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public interface NoticePresenter {
    void loadAllScheduleData(String date);

    interface View {

        void getAllScheduleData(ApiResult<List<Notice>> apiResult);

    }
}
