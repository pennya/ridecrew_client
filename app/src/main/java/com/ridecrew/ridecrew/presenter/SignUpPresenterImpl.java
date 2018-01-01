package com.ridecrew.ridecrew.presenter;

import android.content.Context;

import com.ridecrew.ridecrew.callback.SignUpCallback;
import com.ridecrew.ridecrew.model.SignUpModel;

import Entity.ApiErrorCode;
import Entity.Member;

/**
 * Created by KJH on 2017-12-30.
 */

public class SignUpPresenterImpl implements SignUpPresenter , SignUpCallback {

    private SignUpPresenter.View view;
    private SignUpModel signUpModel;
    private Context context;

    public SignUpPresenterImpl(Context context, SignUpPresenter.View view){
        this.context = context;
        this.view = view;
        signUpModel = new SignUpModel(this);
    }

    @Override
    public void actionJoinMember(Member member) {
        signUpModel.requestSignUp(member);
    }

    @Override
    public void getNetworkResponse(int code) {
        if( code == 200) {
            view.moveActivity();
        }
    }

    @Override
    public void getNetworkResponse(String text, ApiErrorCode code) {
        switch(code) {
            case DUPLICATE_LOGIN_ID:
                view.showToast(text);
                break;

            case DUPLICATE_DEVICE_ID:
                view.showToast(text);
                break;
        }
    }
}