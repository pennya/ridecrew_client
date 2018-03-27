package com.ridecrew.ridecrew.model;

import com.ridecrew.ridecrew.callback.ScheduleMemberModelCallback;

import java.util.List;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.ScheduleMember;
import network.NetworkManager;
import network.ScheduleService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kim on 2018. 1. 11..
 */

public class ScheduleMemberModel {
    private ScheduleMemberModelCallback callback;

    public ScheduleMemberModel(ScheduleMemberModelCallback callback) {
        this.callback = callback;
    }

    public void addScheduleMember(ScheduleMember scheduleMember) {
        ScheduleService service = NetworkManager.getInstance().getRetrofit(ScheduleService.class);
        Call<ApiResult<ScheduleMember>> addCall = service.addScheduleMember(scheduleMember);
        addCall.enqueue(new Callback<ApiResult<ScheduleMember>>() {
            @Override
            public void onResponse(Call<ApiResult<ScheduleMember>> call, Response<ApiResult<ScheduleMember>> response) {
                if( response.isSuccessful() && response.code() == 200) {
                    ApiResult<ScheduleMember> result = response.body();
                    if( result.isSuccess()) {
                        callback.getAddNetworkResponse(result, 200);
                    } else {
                        callback.getNetworkResponse(result.getError().getMessage(), result.getError().getCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResult<ScheduleMember>> call, Throwable t) {
                callback.getNetworkResponse(t.getMessage(), ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }

    public void deleteScheduleMember(Long scheduleId, Long memberId) {
        ScheduleService service = NetworkManager.getInstance().getRetrofit(ScheduleService.class);
        Call<ApiResult<Void>> deleteCall = service.deleteScheduleMember(scheduleId, memberId);
        deleteCall.enqueue(new Callback<ApiResult<Void>>() {
            @Override
            public void onResponse(Call<ApiResult<Void>> call, Response<ApiResult<Void>> response) {
                if( response.isSuccessful() && response.code() == 200) {
                    ApiResult<Void> result = response.body();
                    if( result.isSuccess()) {
                        callback.getDeleteNetworkResponse(result, 200);
                    } else {
                        callback.getNetworkResponse(result.getError().getMessage(), result.getError().getCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResult<Void>> call, Throwable t) {
                callback.getNetworkResponse(t.getMessage(), ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }

    public void getScheduleMemberListByMemberId(Long memberId) {
        ScheduleService service = NetworkManager.getInstance().getRetrofit(ScheduleService.class);
        Call<ApiResult<List<ScheduleMember>>> findCall = service.getAllScheduleMemberByMemberId(memberId);
        findCall.enqueue(new Callback<ApiResult<List<ScheduleMember>>>() {
            @Override
            public void onResponse(Call<ApiResult<List<ScheduleMember>>> call, Response<ApiResult<List<ScheduleMember>>> response) {
                if( response.isSuccessful() && response.code() == 200) {
                    ApiResult<List<ScheduleMember>> result = response.body();
                    if( result.isSuccess()) {
                        callback.getNetworkResponseEx(result, 200);
                    } else {
                        callback.getNetworkResponse(result.getError().getMessage(), result.getError().getCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResult<List<ScheduleMember>>> call, Throwable t) {
                callback.getNetworkResponse(t.getMessage(), ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }

    public void getScheduleMemberListByScheduleId(Long scheduleId) {
        ScheduleService service = NetworkManager.getInstance().getRetrofit(ScheduleService.class);
        Call<ApiResult<List<ScheduleMember>>> findCall = service.getAllScheduleMemberByScheduleId(scheduleId);
        findCall.enqueue(new Callback<ApiResult<List<ScheduleMember>>>() {
            @Override
            public void onResponse(Call<ApiResult<List<ScheduleMember>>> call, Response<ApiResult<List<ScheduleMember>>> response) {
                if( response.isSuccessful() && response.code() == 200) {
                    ApiResult<List<ScheduleMember>> result = response.body();
                    if( result.isSuccess()) {
                        callback.getNetworkResponseEx(result, 201);
                    } else {
                        callback.getNetworkResponse(result.getError().getMessage(), result.getError().getCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResult<List<ScheduleMember>>> call, Throwable t) {
                callback.getNetworkResponse(t.getMessage(), ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }

}
