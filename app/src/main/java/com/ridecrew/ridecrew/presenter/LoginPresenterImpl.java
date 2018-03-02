package com.ridecrew.ridecrew.presenter;

import android.app.Activity;
import android.util.Log;

import com.ridecrew.ridecrew.callback.LoginCallback;
import com.ridecrew.ridecrew.model.LoginModel;

import Define.DefineValue;
import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Member;
import util.SharedUtils;

import static Define.DefineValue.FACEBOOK_LOGIN_COMPLETE;
import static Define.DefineValue.NORMAL_LOGIN;
import static Define.DefineValue.NORMAL_LOGIN_COMPLETE;
import static Define.DefineValue.SIGNUP_COMPLETE;

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
    public void actionJoinMember(Member member) {
        loginModel.requestSignUp(member);
    }

    @Override
    public void actionSnsLogin(Member member) {
        loginModel.requestSnsLogin(member);
    }

    @Override
    public void getNetworkResponse(ApiResult<Member> member, int status) {

        switch(status)
        {
            case NORMAL_LOGIN_COMPLETE:
                setMemberProperties(member.getData());
                view.moveActivity();
                break;

            case FACEBOOK_LOGIN_COMPLETE:
                setMemberProperties(member.getData());
                view.moveActivity();
                break;

            case SIGNUP_COMPLETE:
                if(member.getData().getMemberType() != NORMAL_LOGIN) {
                    setMemberProperties(member.getData());
                }
                view.moveActivity();
                break;
        }
    }

    @Override
    public void getNetworkResponse(String msg, ApiErrorCode code) {
        switch(code) {
            case INCORRECT_LOGIN_ID_AND_PASSWORD:
                view.showToast(msg);
                break;
            case DUPLICATE_LOGIN_ID:
                view.showToast(msg);
                break;

            case DUPLICATE_DEVICE_ID:
                view.showToast(msg);
                break;

            case INCORRECT_LOGIN_ID:
                view.showToast(msg);
                break;

            default:
                view.showToast(msg);
                break;
        }
    }

    private void setMemberProperties(Member member) {
        Member memberInfo = member;
        SharedUtils.setLongValue(context, DefineValue.LOGIN_ID_PK, memberInfo.getId());
        SharedUtils.setStringValue(context, DefineValue.CURRENT_LOGIN_ID, memberInfo.getEmail());
        SharedUtils.setStringValue(context, DefineValue.NICKNAME, memberInfo.getNickName());
        SharedUtils.setStringValue(context, DefineValue.DEVICE_ID, memberInfo.getDeviceId());
        SharedUtils.setBooleanValue(context, DefineValue.IS_LOGIN, true);
        SharedUtils.setIntValue(context, DefineValue.MEMBER_TYPE, memberInfo.getMemberType());
    }
}