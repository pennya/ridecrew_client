package com.ridecrew.ridecrew.presenter;

import Entity.ApiResult;
import Entity.Schedule;
import Entity.ScheduleMember;

/**
 * Created by KJH on 2018-01-02.
 */

public interface ScheduleEnrollPresenter {

    void scheduleEnroll(Schedule schedule);
    void addScheduleMember(ScheduleMember scheduleMember);

    interface View {
        void moveActivity();
        void showToast(String text);
        void addScheduleMember(ApiResult<Schedule> schedule);
    }
}
