package com.ridecrew.ridecrew.model;


import com.ridecrew.ridecrew.callback.NoticePostCallback;

import java.util.ArrayList;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Notice;
import network.NetworkManager;
import network.NoticeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JooHyeong on 2018. 1. 23..
 */

public class NoticePostModel {

    private NoticePostCallback mCallback;

    public NoticePostModel(NoticePostCallback callback)  { mCallback = callback;}


    public void noticePost(Notice notice) {
        NoticeService noticeService = NetworkManager.getIntance().getRetrofit(NoticeService.class);
        Call<ApiResult<Notice>> addCall = noticeService.addNotice(notice);
        addCall.enqueue(new Callback<ApiResult<Notice>>() {
            @Override
            public void onResponse(Call<ApiResult<Notice>> call, Response<ApiResult<Notice>> response) {
                if(response.isSuccessful()) {
                    ApiResult<Notice> result = response.body();
                    if(result.isSuccess()) {
                        mCallback.getNetworkResponse(result,200);
                    } else {
                        mCallback.getNetworkResponse(result.getError().getMessage(),result.getError().getCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResult<Notice>> call, Throwable t) {
                mCallback.getNetworkResponse(t.getMessage(),ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }
}
