package com.ridecrew.ridecrew.model;

import android.content.Context;

import com.ridecrew.ridecrew.callback.LoginCallback;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Member;
import network.MemberService;
import network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KJH on 2017-12-30.
 */

public class LoginModel {
    private LoginCallback mLoginCallback;

    private Context mContext;

    public LoginModel(Context context, LoginCallback loginCallback){
        mContext = context;
        mLoginCallback = loginCallback;
    }

    public void requestLogin(String email, String pw){
        MemberService userService = NetworkManager.getIntance().getRetrofit(MemberService.class);

        Call<ApiResult<Member>> LoginCall = userService.loginMember(email, pw);
        LoginCall.enqueue(new Callback<ApiResult<Member>>() {
            @Override
            public void onResponse(Call<ApiResult<Member>> call, Response<ApiResult<Member>> response) {
                if(response.isSuccessful()) {

                    ApiResult<Member> result = response.body();
                    if( result.isSuccess()) {
                        mLoginCallback.getNetworkResponse(result, 200);
                    } else {
                        mLoginCallback.getNetworkResponse(result.getError().getMessage(), result.getError().getCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResult<Member>> call, Throwable t) {
                mLoginCallback.getNetworkResponse(t.getMessage(), ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }
}