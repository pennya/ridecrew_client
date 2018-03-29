package com.ridecrew.ridecrew.model;

import com.ridecrew.ridecrew.callback.ScheduleEnrollCallback;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Schedule;
import Entity.ScheduleMember;
import network.NetworkManager;
import network.ScheduleService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KJH on 2018-01-02.
 */

public class ScheduleEnrollModel {

    private ScheduleEnrollCallback mCallback;

    public ScheduleEnrollModel(ScheduleEnrollCallback callback) {
        mCallback = callback;
    }

    public void scheduleEnroll(Schedule schedule) {
        ScheduleService scheduleService = NetworkManager.getInstance().getRetrofit(ScheduleService.class);
        Call<ApiResult<Schedule>> addCall = scheduleService.addSchedule(schedule);
        addCall.enqueue(new Callback<ApiResult<Schedule>>() {
            @Override
            public void onResponse(Call<ApiResult<Schedule>> call, Response<ApiResult<Schedule>> response) {
                if(response.isSuccessful()) {
                    ApiResult<Schedule> result = response.body();
                    if(result.isSuccess()) {
                        mCallback.getNetworkResponse(result, 200);
                    } else {
                        mCallback.getNetworkResponse(result.getError().getMessage(), result.getError().getCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResult<Schedule>> call, Throwable t) {
                mCallback.getNetworkResponse(t.getMessage(), ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }

    public void addSchedumeMember(ScheduleMember scheduleMember) {
        ScheduleService service = NetworkManager.getInstance().getRetrofit(ScheduleService.class);
        Call<ApiResult<ScheduleMember>> addCall = service.addScheduleMember(scheduleMember);
        addCall.enqueue(new Callback<ApiResult<ScheduleMember>>() {
            @Override
            public void onResponse(Call<ApiResult<ScheduleMember>> call, Response<ApiResult<ScheduleMember>> response) {
                if( response.isSuccessful() && response.code() == 200) {
                    ApiResult<ScheduleMember> result = response.body();
                    if( result.isSuccess()) {
                        mCallback.getNetworkResponseEx(result, 200);
                    } else {
                        mCallback.getNetworkResponse(result.getError().getMessage(), result.getError().getCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResult<ScheduleMember>> call, Throwable t) {
                mCallback.getNetworkResponse(t.getMessage(), ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }
}
