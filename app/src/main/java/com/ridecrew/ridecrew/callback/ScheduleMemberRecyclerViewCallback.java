package com.ridecrew.ridecrew.callback;

/**
 * Created by kim on 2018. 1. 11..
 */

public interface ScheduleMemberRecyclerViewCallback {
    void showItem(int position);
    void cancelSchedule(int itemPosition, long scheduleId, long memberId);
    void noScheduleValidate();
}
