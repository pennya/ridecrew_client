package com.ridecrew.ridecrew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.presenter.LoginPresenter;
import com.ridecrew.ridecrew.presenter.LoginPresenterImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Random;

import Define.DefineValue;
import Entity.Member;
import util.DeviceUuidFactory;

import static util.UtilsApp.requestFocus;

public class LoginActivity extends BaseToolbarActivity implements LoginPresenter.View, View.OnClickListener{

    private LoginPresenter mPresenter;
    private Button mSubmit;
    private EditText mEmail, mPassword;
    private TextInputLayout mInputLayoutEmail, mInputlayoutPassword;
    private TextView mEnroll;

    /**
     * Facebook login
     */
    private Button btnFbLogin;
    private CallbackManager callbackManager;

    /**
     * Google login
     */
    private Button btnGgLogin;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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

            case R.id.facebook_login_button:
                loginFacebook();
                break;

            case R.id.google_login_button:
                
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
        btnFbLogin = (Button) findViewById(R.id.facebook_login_button);
        btnGgLogin = (Button) findViewById(R.id.google_login_button);

        mSubmit.setOnClickListener(this);
        mEnroll.setOnClickListener(this);
        btnFbLogin.setOnClickListener(this);
        btnGgLogin.setOnClickListener(this);

        mEmail.addTextChangedListener(new LoginTextWatcher(mEmail));
        mPassword.addTextChangedListener(new LoginTextWatcher(mPassword));
    }

    private void setDefaultSettings() {
        mPresenter = new LoginPresenterImpl(this, this);
        callbackManager = CallbackManager.Factory.create();
    }

    private void loginFacebook() {

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("ridecrew_KJH", "1 : " + loginResult.getAccessToken().getUserId());

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                String fb_id, name, email, picture;
                                int gender;
                                try {
                                    fb_id = object.getString("id");
                                    name = object.getString("name");
                                    email = object.getString("email");
                                    gender = object.getString("gender").equals("male") ? 0 : 1;

                                    DeviceUuidFactory duf = new DeviceUuidFactory(getApplicationContext());
                                    Member member = Member.builder()
                                            .setNickName(name)
                                            .setEmail(fb_id + "_" + email)
                                            .setPwd(mPassword.getText().toString().trim())
                                            .setSex(gender)
                                            .setDeviceId(String.valueOf(new Random().nextInt()))//.setDeviceId(duf.getDeviceUuid().toString())
                                            .setMemberType(2) ;

                                    mPresenter.actionSnsLogin(member);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("ridecrew_KJH","onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("ridecrew_KJH","onError");
            }
        });
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