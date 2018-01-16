package com.ridecrew.ridecrew.model;

import android.util.Log;
import com.ridecrew.ridecrew.callback.NoticeModelCallback;

import java.util.ArrayList;
import java.util.List;
import Define.DefineValue;
import Entity.ApiResult;
import Entity.Notice;
import network.NetworkManager;
import network.NoticeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public class NoticeModel {
    private NoticeModelCallback mCallback;

    public NoticeModel(NoticeModelCallback callback) {
        mCallback = callback;
    }

    public void requestNoticeList() {
        NoticeService service = NetworkManager.getIntance().getRetrofit(NoticeService.class);
        Call<ApiResult<ArrayList<Notice>>> noticeListCall = service.getAllNoticeData();
        noticeListCall.enqueue(new Callback<ApiResult<ArrayList<Notice>>>() {

            @Override
            public void onResponse(Call<ApiResult<ArrayList<Notice>>> call, Response<ApiResult<ArrayList<Notice>>> response) {
                if(response.isSuccessful() && response.code() == 200) {
                    ApiResult<ArrayList<Notice>> result = response.body();
                    mCallback.getAllNoticeDate(result);
                }
            }

            @Override
            public void onFailure(Call<ApiResult<ArrayList<Notice>>> call, Throwable t) {
                Log.d(DefineValue.TAG,"requestNoticeList falil");
                Log.d(DefineValue.TAG,t.getMessage());
            }
        });
    }
}
