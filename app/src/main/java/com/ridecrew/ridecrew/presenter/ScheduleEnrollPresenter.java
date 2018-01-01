package com.ridecrew.ridecrew.presenter;

import Entity.Schedule;

/**
 * Created by KJH on 2018-01-02.
 */

public interface ScheduleEnrollPresenter {

    void scheduleEnroll(Schedule schedule);

    interface View {
        void moveActivity();
        void showToast(String text);
    }
}
