package com.ridecrew.ridecrew.presenter;

import Entity.Member;

/**
 * Created by KJH on 2017-12-30.
 */

public interface LoginPresenter {
    void actionJoinMember(Member member);
    void actionLogin(String id, String pw);
    void actionSnsLogin(Member member);

    interface View{
        void moveActivity();
        void showToast(String text);
    }
}