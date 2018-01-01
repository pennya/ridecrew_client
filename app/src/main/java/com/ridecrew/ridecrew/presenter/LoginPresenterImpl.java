package com.ridecrew.ridecrew.presenter;

import android.app.Activity;

import com.ridecrew.ridecrew.callback.LoginCallback;
import com.ridecrew.ridecrew.model.LoginModel;

import Define.DefineValue;
import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Member;
import util.SharedUtils;

/**
 * Created by KJH on 2017-12-30.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginCallback {
    private LoginPresenter.View view;
    private Activity context;
    private LoginModel loginModel;

    public LoginPresenterImpl(Activity context, LoginPresenter.View view){
        this.context = context;
        this.view = view;
        this.loginModel = new LoginModel(context, this);
    }

    @Override
    public void actionLogin(String id, String pw) {
        loginModel.requestLogin(id, pw);
    }

    @Override
    public void getNetworkResponse(ApiResult<Member> member, int status) {

        if(status == 200){
            Member memberInfo = member.getData();

            SharedUtils.setLongValue(context, DefineValue.LOGIN_ID_PK, memberInfo.getId());
            SharedUtils.setStringValue(context, DefineValue.CURRENT_LOGIN_ID, memberInfo.getEmail());
            SharedUtils.setStringValue(context, DefineValue.NICKNAME, memberInfo.getNickName());
            SharedUtils.setStringValue(context, DefineValue.DEVICE_ID, memberInfo.getDeviceId());
            SharedUtils.setBooleanValue(context, DefineValue.IS_LOGIN, true);

            view.moveActivity();
        }
    }

    @Override
    public void getNetworkResponse(String msg, ApiErrorCode code) {
        switch(code) {
            case INCORRECT_LOGIN_ID_AND_PASSWORD:
                view.showToast(msg);
                break;
        }
    }
}