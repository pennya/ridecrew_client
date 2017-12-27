package com.ridecrew.ridecrew;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import Dummy.DummyModel;

/**
 * Created by kim on 2017. 12. 8..
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ExampleDummyModelTest {

    @Test
    public void networkTest() throws Exception{
        DummyModel dummy = new DummyModel();
        dummy.requestUserList();
        dummy.requestScheduleList();
    }
}
