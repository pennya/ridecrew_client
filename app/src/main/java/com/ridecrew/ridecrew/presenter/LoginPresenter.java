package com.ridecrew.ridecrew.presenter;

/**
 * Created by KJH on 2017-12-30.
 */

public interface LoginPresenter {

    void actionLogin(String id, String pw);

    interface View{
        void moveActivity();
        void showToast(String text);
    }
}