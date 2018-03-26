package com.ridecrew.ridecrew.presenter;

import java.util.List;

import Entity.ApiResult;
import Entity.ScheduleMember;

/**
 * Created by kim on 2018. 1. 11..
 */

public interface ScheduleMemberPresenter {

    void add(ScheduleMember scheduleMember);
    void delete(Long scheduleId, Long memberId);
    void getScheduleMemberListByMemberId(Long memberId);

    interface View {
        void moveActivity();
        void showToast(String text);
        void getScheduleMemberList(ApiResult<List<ScheduleMember>> result);
        void deleteComplete();
    }
}
