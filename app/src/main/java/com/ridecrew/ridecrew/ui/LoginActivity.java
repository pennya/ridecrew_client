package com.ridecrew.ridecrew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.presenter.LoginPresenter;
import com.ridecrew.ridecrew.presenter.LoginPresenterImpl;

import Define.DefineValue;

import static util.UtilsApp.requestFocus;

public class LoginActivity extends BaseToolbarActivity implements LoginPresenter.View, View.OnClickListener{

    LoginPresenter mPresenter;
    private Button mSubmit;
    private EditText mEmail, mPassword;
    private TextInputLayout mInputLayoutEmail, mInputlayoutPassword;
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
        setResult(RESULT_OK, null);
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
                mPresenter.actionLogin(mEmail.getText().toString().trim(), mPassword.getText().toString().trim());
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
        mInputLayoutEmail = (TextInputLayout) findViewById(R.id.til_activity_login_email_layout);
        mInputlayoutPassword = (TextInputLayout) findViewById(R.id.til_activity_login_pwd_layout);

        mSubmit.setOnClickListener(this);
        mEnroll.setOnClickListener(this);

        mEmail.addTextChangedListener(new LoginTextWatcher(mEmail));
        mPassword.addTextChangedListener(new LoginTextWatcher(mPassword));
    }

    private void setDefaultSettings() {
        mPresenter = new LoginPresenterImpl(this, this);
    }

    private boolean validateEmail() {
        String email = mEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            mInputLayoutEmail.setError("이메일 형식에 일치하지 않습니다.");
            requestFocus(this, mEmail);
            return false;
        } else {
            mInputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (mPassword.getText().toString().trim().isEmpty()) {
            mInputlayoutPassword.setError("비밀번호를 입력해주세요.");
            requestFocus(this, mPassword);
            return false;
        } else {
            mInputlayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private class LoginTextWatcher implements TextWatcher {

        private View view;

        public LoginTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.edt_activity_login_email:
                    validateEmail();
                    break;

                case R.id.edt_activity_login_pwd:
                    validatePassword();
                    break;
            }
        }
    }
}