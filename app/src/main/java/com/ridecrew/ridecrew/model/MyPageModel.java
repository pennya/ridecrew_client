package com.ridecrew.ridecrew.model;

import android.util.Log;

import com.ridecrew.ridecrew.callback.MyPageModelCallback;

import java.util.List;

import Define.DefineValue;
import Entity.ApiResult;
import Entity.Schedule;
import network.MyPageService;
import network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KIM on 2017-12-27.
 */

public class MyPageModel {

    private MyPageModelCallback mCallback;

    public MyPageModel(MyPageModelCallback callback) {
        mCallback = callback;
    }

    public void requestScheduleList(Long memberId) {
        MyPageService service = NetworkManager.getIntance().getRetrofit(MyPageService.class);
        Call<ApiResult<List<Schedule>>> scheduleListCall = service.getAllSchedules(memberId);
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
                Log.d(DefineValue.TAG, "requestScheduleList fail");
                Log.d(DefineValue.TAG, t.getMessage());
            }
        });
    }
}
