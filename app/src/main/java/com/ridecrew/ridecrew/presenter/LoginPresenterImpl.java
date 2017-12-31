package com.ridecrew.ridecrew.presenter;

import android.app.Activity;

import com.ridecrew.ridecrew.callback.LoginCallback;
import com.ridecrew.ridecrew.model.LoginModel;

import Define.Define;
import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Member;
import util.SharedUtils;

/**
 * Created by KJH on 2017-12-30.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginCallback {
    private LoginPresenter.View view;
    private Activity activity;
    private LoginModel loginModel;

    public LoginPresenterImpl(Activity activity, LoginPresenter.View view){
        this.activity = activity;
        this.view = view;
        this.loginModel = new LoginModel(activity, this);
    }

    @Override
    public void actionLogin(String id, String pw) {
        loginModel.requestLogin(id, pw);
    }

    @Override
    public void getNetworkResponse(ApiResult<Member> member, int status) {

        if(status == 200){
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

    private void savePref(int pk, String id, String pw) {

        SharedUtils.setIntValue(activity, Define.ID_PK, pk);
        SharedUtils.setStringValue(activity, Define.LOGIN_ID, id);
        SharedUtils.setStringValue(activity, Define.LOGIN_PASSWORD, pw);

        SharedUtils.setBooleanValue(activity, Define.IS_LOGIN, true);
        SharedUtils.setStringValue(activity, Define.CURRENT_LOGIN_ID, id);
    }
}