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

import static Define.DefineValue.NORMAL_LOGIN_COMPLETE;
import static Define.DefineValue.SIGNUP_COMPLETE;
import static Define.DefineValue.SIGNUP_MODIFY;
import static Define.DefineValue.SNS_LOGIN_COMPLETE;

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
        MemberService userService = NetworkManager.getInstance().getRetrofit(MemberService.class);

        Call<ApiResult<Member>> LoginCall = userService.loginMember(email, pw);
        LoginCall.enqueue(new Callback<ApiResult<Member>>() {
            @Override
            public void onResponse(Call<ApiResult<Member>> call, Response<ApiResult<Member>> response) {
                if(response.isSuccessful()) {

                    ApiResult<Member> result = response.body();
                    if( result.isSuccess()) {
                        mLoginCallback.getNetworkResponse(result, NORMAL_LOGIN_COMPLETE);
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

    public void requestSnsLogin(Member member) {
        MemberService userService = NetworkManager.getInstance().getRetrofit(MemberService.class);
        Call<ApiResult<Member>> LoginCall = userService.snsLogin(member);
        LoginCall.enqueue(new Callback<ApiResult<Member>>() {
            @Override
            public void onResponse(Call<ApiResult<Member>> call, Response<ApiResult<Member>> response) {
                if(response.isSuccessful()) {

                    ApiResult<Member> result = response.body();
                    if( result.isSuccess()) {
                        mLoginCallback.getNetworkResponse(result, SNS_LOGIN_COMPLETE);
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

    public void requestSignUp(Member member)
    {
        final int memberType = member.getMemberType();
        MemberService memberService = NetworkManager.getInstance().getRetrofit(MemberService.class);

        Call<ApiResult<Member>> signUpCall = memberService.signUpMember(member);
        signUpCall.enqueue(new Callback<ApiResult<Member>>() {
            @Override
            public void onResponse(Call<ApiResult<Member>> call, Response<ApiResult<Member>> response) {
                if(response.isSuccessful()) {
                    ApiResult<Member> result = response.body();
                    if(result.isSuccess())
                        mLoginCallback.getNetworkResponse(result, SIGNUP_COMPLETE);
                    else {
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

    public void requestUpdateInfo(long id, Member member) {
        MemberService service = NetworkManager.getInstance().getRetrofit(MemberService.class);
        Call<ApiResult<Member>> updateCall = service.updateMember(id,member);
        updateCall.enqueue(new Callback<ApiResult<Member>>() {
            @Override
            public void onResponse(Call<ApiResult<Member>> call, Response<ApiResult<Member>> response) {
                ApiResult<Member> result = response.body();
                if(result.isSuccess()) {
                    mLoginCallback.getNetworkResponse(result,SIGNUP_MODIFY);
                } else {
                    mLoginCallback.getNetworkResponse(result.getError().getMessage(),result.getError().getCode());
                }
            }

            @Override
            public void onFailure(Call<ApiResult<Member>> call, Throwable t) {
                mLoginCallback.getNetworkResponse(t.getMessage(),ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }
}