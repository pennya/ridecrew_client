package com.ridecrew.ridecrew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import util.DeviceUuidFactory;

public class SignUpActivity extends BaseToolbarActivity implements SignUpPresenter.View, View.OnClickListener, Spinner.OnItemSelectedListener{

    private SignUpPresenter mPresenter;
    private Button mSubmit;
    private EditText mNickName;
    private EditText mEmail;
    private EditText mPassword;
    private Spinner mSex;
    private int sexType; /* man is 0, woman is 1*/

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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
                        .setNickName(mNickName.getText().toString())
                        .setEmail(mEmail.getText().toString())
                        .setPwd(mPassword.getText().toString())
                        .setSex(sexType)
                        .setDeviceId(String.valueOf(new Random().nextInt()))//.setDeviceId(duf.getDeviceUuid().toString())
                        .setMemberType(1) /* member is 1, non member is 0*/ ;

                mPresenter.actionJoinMember(member);
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
        mNickName = (EditText)findViewById(R.id.edt_activity_sign_up_nickname);
        mEmail = (EditText)findViewById(R.id.edt_activity_sign_up_email);
        mPassword = (EditText)findViewById(R.id.edt_activity_sign_up_password);
        mSex = (Spinner)findViewById(R.id.spinner_activity_sign_up_sex);

        mSubmit.setOnClickListener(this);
        mSex.setOnItemSelectedListener(this);
    }

    private void setDefaultSetting() {
        sexType = 0;
    }
}
