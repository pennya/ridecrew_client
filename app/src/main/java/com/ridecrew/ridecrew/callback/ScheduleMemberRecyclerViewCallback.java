package com.ridecrew.ridecrew.callback;

import Entity.Schedule;

/**
 * Created by kim on 2018. 1. 11..
 */

public interface ScheduleMemberRecyclerViewCallback {
    void showItem(int position);
    void showItem(Schedule schedule);
    void cancelSchedule(int itemPosition, long scheduleId, long memberId);
    void noScheduleValidate();
    void showMembers(int itemPosition, long scheduleId);
}
