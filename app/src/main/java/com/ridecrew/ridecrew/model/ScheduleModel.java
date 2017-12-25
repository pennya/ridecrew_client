package com.ridecrew.ridecrew.model;

import android.util.Log;

import com.ridecrew.ridecrew.callback.ScheduleModelCallback;

import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;
import network.DummyService;
import network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kim on 2017. 12. 25..
 */

public class ScheduleModel {

    private ScheduleModelCallback mCallback;

    public ScheduleModel(ScheduleModelCallback callback) {
        mCallback = callback;
    }

    public void requestScheduleList() {
        DummyService service = NetworkManager.getIntance().getRetrofit(DummyService.class);
        Call<ApiResult<List<Schedule>>> scheduleListCall = service.getAllSchedules();
        scheduleListCall.enqueue(new Callback<ApiResult<List<Schedule>>>() {
            @Override
            public void onResponse(Call<ApiResult<List<Schedule>>> call, Response<ApiResult<List<Schedule>>> response) {
                if( response.isSuccessful() && response.code() == 200) {
                    ApiResult<List<Schedule>> result = response.body();
                    mCallback.getAllScheduleData(result);
                }
            }

            @Override
            public void onFailure(Call<ApiResult<List<Schedule>>> call, Throwable t) {
                Log.d(Define.Define.TAG, "requestScheduleList fail");
                Log.d(Define.Define.TAG, t.getMessage());
            }
        });
    }
}
