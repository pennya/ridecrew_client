package com.ridecrew.ridecrew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.presenter.SignUpPresenter;
import com.ridecrew.ridecrew.presenter.SignUpPresenterImpl;

import java.util.Random;

import Entity.Member;
import Entity.MemberSingleton;
import util.DeviceUuidFactory;

import static util.UtilsApp.requestFocus;

public class SignUpActivity extends BaseToolbarActivity implements SignUpPresenter.View, View.OnClickListener, Spinner.OnItemSelectedListener{

    private SignUpPresenter mPresenter;
    private Button mSubmit, mModify;
    private EditText mNickName, mEmail, mPassword, mPasswordCheck;
    private TextInputLayout mInputLayoutNickName, mInputLayoutEmail, mInputLayoutPassword, mInputLayoutPasswordCheck;
    private Spinner mSex;
    private int sexType; /* man is 0, woman is 1*/
    private boolean modify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInit();
        setDefaultSetting();
        mPresenter = new SignUpPresenterImpl(this, this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_name;
    }

    @Override
    public void moveActivity() {
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
            case R.id.btn_activity_sign_up_submit:
                DeviceUuidFactory duf = new DeviceUuidFactory(this);

                Member member = Member.builder()
                        .setNickName(mNickName.getText().toString().trim())
                        .setEmail(mEmail.getText().toString().trim())
                        .setPwd(mPassword.getText().toString().trim())
                        .setSex(sexType)
                        .setDeviceId(String.valueOf(new Random().nextInt()))//.setDeviceId(duf.getDeviceUuid().toString())
                        .setMemberType(1) /* member is 1, non member is 0*/ ;

                mPresenter.actionJoinMember(member);
                break;

            case R.id.btn_activity_modify_submit:

                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if( ((String)adapterView.getItemAtPosition(position)).equals("남자"))
            sexType = 0;
        else
            sexType = 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void layoutInit() {
        mSubmit = (Button)findViewById(R.id.btn_activity_sign_up_submit);
        mModify = (Button)findViewById(R.id.btn_activity_modify_submit);
        mNickName = (EditText)findViewById(R.id.edt_activity_sign_up_nickname);
        mEmail = (EditText)findViewById(R.id.edt_activity_sign_up_email);
        mPassword = (EditText)findViewById(R.id.edt_activity_sign_up_password);
        mPasswordCheck = (EditText)findViewById(R.id.edt_activity_sign_up_password_check);
        mSex = (Spinner)findViewById(R.id.spinner_activity_sign_up_sex);
        mInputLayoutNickName = (TextInputLayout)findViewById(R.id.til_activity_sign_up_nickname_layout);
        mInputLayoutEmail = (TextInputLayout)findViewById(R.id.til_activity_sign_up_email_layout);
        mInputLayoutPassword = (TextInputLayout)findViewById(R.id.til_activity_sign_up_password_layout);
        mInputLayoutPasswordCheck = (TextInputLayout)findViewById(R.id.til_activity_sign_up_password_check_layout);

        mSubmit.setOnClickListener(this);
        mModify.setOnClickListener(this);
        mSex.setOnItemSelectedListener(this);

        mNickName.addTextChangedListener(new SignUpTextWatcher(mNickName));
        mEmail.addTextChangedListener(new SignUpTextWatcher(mEmail));
        mPassword.addTextChangedListener(new SignUpTextWatcher(mPassword));
        mPasswordCheck.addTextChangedListener(new SignUpTextWatcher(mPasswordCheck));
    }

    private void setDefaultSetting() {
        sexType = 0;
        mPasswordCheck.setEnabled(false);
        //개인정보 수정
        Intent intent = new Intent(this.getIntent());
        modify = intent.getBooleanExtra("modify",false);
        if(modify) {
            mEmail.setVisibility(View.GONE);
            mInputLayoutEmail.setVisibility(View.GONE);
            mModify.setVisibility(View.VISIBLE);
            mSubmit.setVisibility(View.GONE);
        }
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

    private boolean validateNickName() {
        if (mNickName.getText().toString().trim().isEmpty()) {
            mInputLayoutNickName.setError("별명을 입력해주세요.");
            requestFocus(this, mNickName);
            return false;
        } else {
            mInputLayoutNickName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (mPassword.getText().toString().trim().isEmpty()) {
            mInputLayoutPassword.setError("비밀번호를 입력해주세요.");
            mPasswordCheck.setEnabled(false);
            requestFocus(this, mPassword);
            return false;
        } else {
            mInputLayoutPassword.setErrorEnabled(false);
            mPasswordCheck.setEnabled(true);
        }

        return true;
    }

    private boolean validatePasswordCheck() {
        String passwordCheck = mPasswordCheck.getText().toString().trim();
        if (passwordCheck.isEmpty() || !isValidPassword(passwordCheck)) {
            mInputLayoutPasswordCheck.setError("비밀번호가 일치하지 않습니다.");
            requestFocus(this, mInputLayoutPasswordCheck);
            return false;
        } else {
            mInputLayoutPasswordCheck.setErrorEnabled(false);
        }

        return true;
    }

    private boolean isValidPassword(String passwordCheck) {
        String password = mPassword.getText().toString().trim();
        return !password.isEmpty() && password.equals(passwordCheck);
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private class SignUpTextWatcher implements TextWatcher {

        private View view;

        public SignUpTextWatcher(View view) {
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
                case R.id.edt_activity_sign_up_nickname:
                    validateNickName();
                    break;
                case R.id.edt_activity_sign_up_email:
                    validateEmail();
                    break;
                case R.id.edt_activity_sign_up_password:
                    validatePassword();
                    break;
                case R.id.edt_activity_sign_up_password_check:
                    validatePasswordCheck();
                    break;
            }
        }
    }
}
