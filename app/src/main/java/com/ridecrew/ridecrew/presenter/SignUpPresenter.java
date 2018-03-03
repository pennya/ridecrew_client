package com.ridecrew.ridecrew.presenter;

import Entity.Member;

/**
 * Created by KJH on 2017-12-30.
 */

public interface SignUpPresenter {
    void actionJoinMember(Member member);

    interface View{
        void moveActivity();
        void showToast(String text);
    }
}
