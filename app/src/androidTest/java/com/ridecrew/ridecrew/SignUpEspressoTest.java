package com.ridecrew.ridecrew;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ridecrew.ridecrew.ui.SignUpActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by KIM on 2018-05-28.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignUpEspressoTest {

    @Rule
    public ActivityTestRule<SignUpActivity> activity = new ActivityTestRule(SignUpActivity.class);

    @Test
    public void viewTest() throws Exception {
        String[] array = activity.getActivity().getResources()
                .getStringArray(R.array.sex);

        Espresso.onView(withId(R.id.spinner_activity_sign_up_sex))
                .perform(ViewActions.click());

        Espresso.onData(is(array[0]))
                .perform(ViewActions.click());

        Espresso.onData(is(array[0]))
                .check(matches(withText("남자")));

        Espresso.onView(withId(R.id.edt_activity_sign_up_nickname))
                .perform(
                        ViewActions.click(),
                        ViewActions.typeText("KIM"),
                        closeSoftKeyboard()
                );

        Espresso.onView(withId(R.id.edt_activity_sign_up_email))
                .perform(
                        ViewActions.click(),
                        ViewActions.typeText("r011@naver.com"),
                        closeSoftKeyboard()
                );

        Espresso.onView(withId(R.id.edt_activity_sign_up_password))
                .perform(
                        ViewActions.click(),
                        ViewActions.typeText("1"),
                        closeSoftKeyboard()
                );

        Espresso.onView(withId(R.id.edt_activity_sign_up_password_check))
                .perform(
                        ViewActions.click(),
                        ViewActions.typeText("1"),
                        closeSoftKeyboard()
                );

        Espresso.onView(withId(R.id.btn_activity_sign_up_submit))
                .perform(ViewActions.click());

    }

}
