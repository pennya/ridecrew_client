package com.ridecrew.ridecrew.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.presenter.LoginPresenter;
import com.ridecrew.ridecrew.presenter.LoginPresenterImpl;

import Define.DefineValue;

public class LoginActivity extends BaseToolbarActivity implements LoginPresenter.View, View.OnClickListener{

    LoginPresenter mPresenter;
    private Button mSubmit;
    private EditText mEmail;
    private EditText mPassword;
    private TextView mEnroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInit();
        setDefaultSettings();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_name;
    }

    @Override
    public void moveActivity() {
        setResult(DefineValue.MY_PAGE_FRAGMENT_REQEUST_CODE, null);
        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
        finish();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_activitiy_login_submit:
                mPresenter.actionLogin(mEmail.getText().toString(), mPassword.getText().toString());
                break;

            case R.id.tv_activity_login_enroll:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
                break;

        }
    }

    private void layoutInit() {
        mEmail = (EditText)findViewById(R.id.edt_activity_login_email);
        mPassword = (EditText)findViewById(R.id.edt_activity_login_pwd);
        mSubmit = (Button) findViewById(R.id.btn_activitiy_login_submit);
        mEnroll = (TextView) findViewById(R.id.tv_activity_login_enroll);

        mSubmit.setOnClickListener(this);
        mEnroll.setOnClickListener(this);
    }

    private void setDefaultSettings() {
        mPresenter = new LoginPresenterImpl(this, this);
    }
}