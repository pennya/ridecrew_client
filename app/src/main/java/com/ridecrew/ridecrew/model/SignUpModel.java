package com.ridecrew.ridecrew.model;

import com.ridecrew.ridecrew.callback.SignUpCallback;

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

public class SignUpModel {

    private SignUpCallback mSignUpCallback;

    public SignUpModel(SignUpCallback signUpCallback){
        this.mSignUpCallback = signUpCallback;
    }

    public void requestSignUp(Member member)
    {
        MemberService memberService = NetworkManager.getInstance().getRetrofit(MemberService.class);

        Call<ApiResult<Member>> signUpCall = memberService.signUpMember(member);
        signUpCall.enqueue(new Callback<ApiResult<Member>>() {
            @Override
            public void onResponse(Call<ApiResult<Member>> call, Response<ApiResult<Member>> response) {
                if(response.isSuccessful()) {
                    ApiResult<Member> result = response.body();
                    if(result.isSuccess())
                        mSignUpCallback.getNetworkResponse( 200);
                    else {
                        mSignUpCallback.getNetworkResponse(result.getError().getMessage(), result.getError().getCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResult<Member>> call, Throwable t) {
                mSignUpCallback.getNetworkResponse(t.getMessage(), ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }

    public void updateMember(long id, Member member) {

    }
}
