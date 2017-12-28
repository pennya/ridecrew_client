package com.ridecrew.ridecrew.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.ridecrew.ridecrew.R;

/**
 * 로그인 팝업
 */
public class LoginDialogActivity extends Activity {

    // Widget Button Declare
    Button btnLoginDialog;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_dialog);
    }
}