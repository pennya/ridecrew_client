package com.ridecrew.ridecrew.presenter;

import com.ridecrew.ridecrew.callback.ScheduleMemberModelCallback;
import com.ridecrew.ridecrew.model.ScheduleMemberModel;

import java.util.List;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.ScheduleMember;

/**
 * Created by kim on 2018. 1. 11..
 */

public class ScheduleMemberPresenterImpl implements ScheduleMemberPresenter, ScheduleMemberModelCallback{

    private ScheduleMemberPresenter.View view;
    private ScheduleMemberModel model;

    public ScheduleMemberPresenterImpl(ScheduleMemberPresenter.View view) {
        this.view = view;
        model = new ScheduleMemberModel(this);
    }

    @Override
    public void add(ScheduleMember scheduleMember) {
        model.addScheduleMember(scheduleMember);
    }

    @Override
    public void delete(Long scheduleId, Long memberId) {
        model.deleteScheduleMember(scheduleId, memberId);
    }

    @Override
    public void getScheduleMemberListByMemberId(Long memberId) {
        model.getScheduleMemberListByMemberId(memberId);
    }

    @Override
    public void getAddNetworkResponse(ApiResult<ScheduleMember> member, int status) {
        view.showToast("참가하기 완료");
    }

    @Override
    public void getDeleteNetworkResponse(ApiResult<Void> member, int status) {
        view.deleteComplete();
    }

    @Override
    public void getNetworkResponseEx(ApiResult<List<ScheduleMember>> member, int status) {
        view.getScheduleMemberList(member);
    }

    @Override
    public void getNetworkResponse(String msg, ApiErrorCode code) {
        switch(code) {
            default:
                view.showToast(msg);
                break;
        }
    }
}
