package com.ridecrew.ridecrew.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.presenter.LoginPresenter;
import com.ridecrew.ridecrew.presenter.LoginPresenterImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Random;

import Entity.Member;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import util.DeviceUuidFactory;
import util.RxEditTextObser;
import util.UtilsApp;

import static Define.DefineValue.GOOGLE_SIGN_IN_REQUEST_CODE;
import static Define.DefineValue.SIGN_IN_AGREEMENT_REQUEST_CODE;
import static util.UtilsApp.requestFocus;

public class LoginActivity extends BaseToolbarActivity implements LoginPresenter.View, View.OnClickListener{

    private LoginPresenter mPresenter;
    private Button mSubmit;
    private EditText mEmail, mPassword;
    private TextInputLayout mInputLayoutEmail, mInputlayoutPassword;
    private TextView mEnroll;
    private ProgressDialog mDialog;
    private PublishSubject<String> emailSource;
    private PublishSubject<String> pwSource;
    /**
     * Facebook login
     */
    private Button btnFbLogin;
    private CallbackManager callbackManager;

    /**
     * Google login
     */
    private Button btnGgLogin;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

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
        return R.string.app_no_title;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result != null) {
                if(result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                } else {
                    Toast.makeText(this, result.getStatus() + "", Toast.LENGTH_SHORT).show();
                    Log.e("PACKRIDING", result.getStatus() + "");
                }
            }
        }

        if(requestCode == SIGN_IN_AGREEMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            int type = data.getIntExtra("type", -1);
            switch (type) {
                case R.id.tv_activity_login_enroll:
                    Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
                    break;

                case R.id.facebook_login_button:
                    loginFacebook();
                    break;

                case R.id.google_login_button:
                    loginGoogle();
                    break;

                default:
                    Toast.makeText(this, "비허가 진입", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public void moveActivity() {
        mDialog.dismiss();

        setResult(RESULT_OK, null);
        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
        finish();
    }

    @Override
    public void showToast(String text) {
        if(mDialog.isShowing())
            mDialog.dismiss();

        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_activitiy_login_submit:
                mDialog.show();
                mPresenter.actionLogin(mEmail.getText().toString().trim(), mPassword.getText().toString().trim());
                break;

            case R.id.tv_activity_login_enroll:
                signInAgree(R.id.tv_activity_login_enroll);
                break;

            case R.id.facebook_login_button:
                signInAgree(R.id.facebook_login_button);
                break;

            case R.id.google_login_button:
                signInAgree(R.id.google_login_button);
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

        emailSource = RxEditTextObser.create(mEmail);
        pwSource = RxEditTextObser.create(mPassword);

        Observable<Boolean> emailValidSignal = emailSource.map(email -> {
            email = email.trim();
            return UtilsApp.isValidEmail(email);
        });
        emailValidSignal.subscribe(sig -> {
            if(sig) {
                mInputLayoutEmail.setErrorEnabled(false);
            } else {
                mInputLayoutEmail.setError("이메일 형식에 일치하지 않습니다.");
                requestFocus(this, mEmail);
            }
        });

        Observable<Boolean> pwValidSignal = pwSource.map(pw -> {
            pw = pw.trim();
            return UtilsApp.isValidPassword(pw);
        });
        pwValidSignal.subscribe(sig -> {
            if(sig) {
                mInputlayoutPassword.setErrorEnabled(false);
            } else {
                mInputlayoutPassword.setError("영문(대소문자 구분), 숫자, 특수문자 조합 6~20자리를 입력해주세요.");
                requestFocus(this, mPassword);
            }
        });
    }

    private void setDefaultSettings() {
        mPresenter = new LoginPresenterImpl(this, this);
        callbackManager = CallbackManager.Factory.create();

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("로딩중..");
        mDialog.setCancelable(false);

        setGoogleLogin();
    }

    private void signInAgree(int type) {
        Intent signInAgreeIntent = new Intent(this, SignInAgreeActivity.class);
        signInAgreeIntent.putExtra("type", type);

        startActivityForResult(signInAgreeIntent, SIGN_IN_AGREEMENT_REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
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
                                mDialog.show();

                                String fb_id, name, picture;
                                int gender;
                                try {
                                    fb_id = object.getString("id");
                                    name = object.getString("name");
                                    //gender = object.getString("gender").equals("male") ? 0 : 1;
                                    picture = object.getJSONObject("picture").getJSONObject("data").getString("url");

                                    DeviceUuidFactory duf = new DeviceUuidFactory(getApplicationContext());
                                    Member member = Member.builder()
                                            .setNickName(name)
                                            .setEmail("fb_" + fb_id)
                                            .setPwd(mPassword.getText().toString().trim())
                                            .setSex(1)
                                            .setDeviceId(String.valueOf(new Random().nextInt()))//.setDeviceId(duf.getDeviceUuid().toString())
                                            .setMemberType(2)
                                            .setImageUrl(picture);

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

    private void setGoogleLogin() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_request_token_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    private void loginGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDialog.show();

                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            String uid, name;
                            Uri picture;

                            uid = user.getUid();
                            name = user.getDisplayName();
                            picture = user.getPhotoUrl();

                            DeviceUuidFactory duf = new DeviceUuidFactory(getApplicationContext());
                            Member member = Member.builder()
                                    .setNickName(name)
                                    .setEmail("gg_" + uid)
                                    .setPwd(mPassword.getText().toString().trim())
                                    .setDeviceId(String.valueOf(new Random().nextInt()))//.setDeviceId(duf.getDeviceUuid().toString())
                                    .setMemberType(3)
                                    .setImageUrl(picture.toString());

                            mPresenter.actionSnsLogin(member);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("PACKRIDING", task.toString());
                            Log.e("PACKRIDING", task.getException().getMessage());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
    }
}