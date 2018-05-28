package com.ridecrew.ridecrew;

import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ridecrew.ridecrew.ui.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by KIM on 2018-05-28.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginEspressoTest {
    @Rule
    public ActivityTestRule<LoginActivity> mLoginRule = new ActivityTestRule(LoginActivity.class);

    @Test
    public void viewTest() throws Exception{
        // 로그인 버튼 화면에 보이는지
        onView(withId(R.id.btn_activitiy_login_submit))
                .check(matches(isDisplayed()));

        // 로그인 수행
        onView(withId(R.id.edt_activity_login_email))
                .perform(
                        ViewActions.click(),
                        ViewActions.typeText("rlawlgns2@naver.com")
                ).check(matches(withText("rlawlgns2@naver.com")));
        onView(withId(R.id.edt_activity_login_pwd))
                .perform(
                        ViewActions.click(),
                        ViewActions.typeText("1")
                ).check(matches(withText("1")));

        onView(withId(R.id.btn_activitiy_login_submit))
                .perform(ViewActions.click());
    }

}
