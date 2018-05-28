package com.ridecrew.ridecrew.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ridecrew.ridecrew.BuildConfig;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.presenter.LoginPresenter;
import com.ridecrew.ridecrew.presenter.LoginPresenterImpl;

import java.io.File;
import java.util.Random;

import Define.DefineValue;
import Entity.Member;
import Entity.MemberSingleton;
import io.reactivex.subjects.PublishSubject;
import util.DeviceUuidFactory;
import util.RealPathUtil;
import util.RxEditTextObser;
import util.SharedUtils;
import util.UtilsApp;

import static com.ridecrew.ridecrew.ui.FileUploadActivity.PICK_FROM_ALBUM;
import static com.ridecrew.ridecrew.ui.FileUploadActivity.REQUEST_PERMISSIONS_REQUEST_CODE;
import static com.ridecrew.ridecrew.ui.FileUploadActivity.TAKE_PICTURE;
import static util.UtilsApp.requestFocus;

public class SignUpActivity extends BaseToolbarActivity implements LoginPresenter.View, View.OnClickListener, Spinner.OnItemSelectedListener {

    private LoginPresenter mPresenter;
    private Button mSubmit, mModify;
    private EditText mNickName, mEmail, mPassword, mPasswordCheck;
    private TextInputLayout mInputLayoutNickName, mInputLayoutEmail, mInputLayoutPassword, mInputLayoutPasswordCheck;
    private Spinner mSex;
    private int sexType; /* man is 0, woman is 1*/
    private ImageView mProfile;
    private boolean modify;
    private File f;
    private String url;
    private boolean mButtonFlag;
    // AWS S3
    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonS3 s3;
    private TransferUtility transferUtility;
    private TransferObserver observer;
    private PublishSubject<String> nickName, email, pw, pwCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutInit();
        setDefaultSetting();
        mPresenter = new LoginPresenterImpl(this, this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_no_title;
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
        switch (view.getId()) {
            case R.id.btn_activity_sign_up_submit:
                mButtonFlag = true;
                awsUpload();
                break;

            case R.id.btn_activity_modify_submit:
                if (!mNickName.getText().toString().isEmpty()) {
                    mButtonFlag = false;
                    awsUpload();
                } else {
                    showToast("모든 정보를 입력해주세요");
                }
                break;

            case R.id.iv_activity_sign_up_profile:
                final CharSequence[] choice = {"앨범에서 선택", "직접 촬영", "프로필 삭제"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("프로필 지정");
                builder.setItems(choice, (dialog, btn) -> {
                    switch(btn) {
                        case 0:
                            if (!checkPermissions() && Build.VERSION.SDK_INT >= 23) {
                                requestPermissions();
                            } else {
                                pickImage();
                            }
                            break;
                        case 1:
                            takePicture();
                            break;
                        case 2:
                            url = null;
                            mProfile.setImageResource(R.drawable.user);
                            showToast("프로필삭제 완료");
                            break;
                    }
                });
                builder.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_FROM_ALBUM) {
                //이미지 선택 안하고 뒤로가기 누를 시
                if (data == null) {
                    return;
                }
                Uri uri = data.getData();
                String realPath = RealPathUtil.getRealPath(this, uri);
                f = new File(realPath);
                mProfile.setImageURI(uri);
            } else if (requestCode == TAKE_PICTURE) {
                if (data == null) {
                    return;
                }
                Uri uri = data.getData();
                String realPath = RealPathUtil.getRealPath(this, uri);
                f = new File(realPath);
                mProfile.setImageURI(uri);
            }
        } catch (NullPointerException e) {
            //선택된 이미지가 로컬에 존재하지 않을 때 ex) google photo가 연동되어 있을 때
            showToast("해당 파일이 사진첩에 저장되어 있지 않습니다.");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (((String) adapterView.getItemAtPosition(position)).equals("남자"))
            sexType = 0;
        else
            sexType = 1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    new android.support.v7.app.AlertDialog.Builder(this)
                            .setTitle("알림")
                            .setMessage("저장소 권한이 필요합니다. 환경 설정에서 저장소 권한을 허가해주세요.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package",
                                            BuildConfig.APPLICATION_ID, null);
                                    intent.setData(uri);
                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }
                            })
                            .create()
                            .show();
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    public void awsUpload() {
        if(f == null) {
            if(!mButtonFlag) {
                MemberSingleton.getInstance().getMember()
                        .setNickName(mNickName.getText().toString().trim())
                        .setPwd(mPassword.getText().toString().trim())
                        .setImageUrl(url);
                mPresenter.actionUpdateMember(MemberSingleton.getInstance().getMember().getId(), MemberSingleton.getInstance().getMember());
            } else if(mButtonFlag) {
                Member member = Member.builder()
                        .setNickName(mNickName.getText().toString().trim())
                        .setEmail(mEmail.getText().toString().trim())
                        .setPwd(mPassword.getText().toString().trim())
                        .setSex(sexType)
                        .setDeviceId(String.valueOf(new Random().nextInt()))//.setDeviceId(duf.getDeviceUuid().toString())
                        .setMemberType(1)
                        .setImageUrl(url);
                mPresenter.actionJoinMember(member);
            }
            return;
        }

        try {
            observer = transferUtility.upload(
                    "ridecrew",
                    "profile/" + f.getName(), f);
        } catch(NullPointerException e) {   //이미지를 변경하지 않고 수정 혹은 가입을 할 때
            Toast.makeText(this, "file not found!", Toast.LENGTH_SHORT).show();
            return;
        }
        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state.name().startsWith("COMPLETE")) {
                    String finalUrl = "https://s3.ap-northeast-2.amazonaws.com/"
                            + observer.getBucket()
                            + "/" + observer.getKey();
                    //수정버튼이 클릭됐을 때
                    if (!mButtonFlag) {
                        MemberSingleton.getInstance().getMember()
                                .setNickName(mNickName.getText().toString().trim())
                                .setImageUrl(finalUrl);
                        mPresenter.actionUpdateMember(MemberSingleton.getInstance().getMember().getId(), MemberSingleton.getInstance().getMember());
                    }
                    //등록버튼이 클릭됐을 때
                    else {
                        Member member = Member.builder()
                                .setNickName(mNickName.getText().toString().trim())
                                .setEmail(mEmail.getText().toString().trim())
                                .setPwd(mPassword.getText().toString().trim())
                                .setSex(sexType)
                                .setDeviceId(String.valueOf(new Random().nextInt()))//.setDeviceId(duf.getDeviceUuid().toString())
                                .setMemberType(1)
                                .setImageUrl(finalUrl);
                        mPresenter.actionJoinMember(member);
                    }
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(int id, Exception ex) {

            }
        });
    }

    private void layoutInit() {
        mSubmit = (Button) findViewById(R.id.btn_activity_sign_up_submit);
        mModify = (Button) findViewById(R.id.btn_activity_modify_submit);
        mNickName = (EditText) findViewById(R.id.edt_activity_sign_up_nickname);
        mEmail = (EditText) findViewById(R.id.edt_activity_sign_up_email);
        mPassword = (EditText) findViewById(R.id.edt_activity_sign_up_password);
        mPasswordCheck = (EditText) findViewById(R.id.edt_activity_sign_up_password_check);
        mSex = (Spinner) findViewById(R.id.spinner_activity_sign_up_sex);
        mInputLayoutNickName = (TextInputLayout) findViewById(R.id.til_activity_sign_up_nickname_layout);
        mInputLayoutEmail = (TextInputLayout) findViewById(R.id.til_activity_sign_up_email_layout);
        mInputLayoutPassword = (TextInputLayout) findViewById(R.id.til_activity_sign_up_password_layout);
        mInputLayoutPasswordCheck = (TextInputLayout) findViewById(R.id.til_activity_sign_up_password_check_layout);
        mProfile = (ImageView) findViewById(R.id.iv_activity_sign_up_profile);

        mSubmit.setOnClickListener(this);
        mModify.setOnClickListener(this);
        mSex.setOnItemSelectedListener(this);
        mProfile.setOnClickListener(this);

        nickName = RxEditTextObser.create(mNickName);
        email = RxEditTextObser.create(mEmail);
        pw = RxEditTextObser.create(mPassword);
        pwCheck = RxEditTextObser.create(mPasswordCheck);

        nickName.map(nickName -> !nickName.isEmpty())
                .subscribe(sig -> {
                    if(sig) {
                        mInputLayoutNickName.setErrorEnabled(false);
                    } else {
                        mInputLayoutNickName.setError("별명을 입력해주세요.");
                        requestFocus(this, mNickName);
                    }
                });

        email.map(email -> UtilsApp.isValidEmail(email))
                .subscribe(sig -> {
                    if (sig) {
                        mInputLayoutEmail.setErrorEnabled(false);
                    } else {
                        mInputLayoutEmail.setError("이메일 형식에 일치하지 않습니다.");
                        requestFocus(this, mEmail);
                    }
                });

        pw.map(pw -> UtilsApp.isValidPassword(pw))
                .subscribe(sig -> {
                    if(sig) {
                        mInputLayoutPassword.setErrorEnabled(false);
                    } else {
                        mInputLayoutPassword.setError("영문(대소문자 구분), 숫자, 특수문자 조합 6~20자리를 입력해주세요.");
                        requestFocus(this, mPassword);
                    }
                });

        pwCheck.map(pwCheck -> UtilsApp.isValidPassword(pwCheck)
                && mPassword.getText().toString().equals(pwCheck))
                .subscribe(sig -> {
                   if(sig) {
                       mInputLayoutPasswordCheck.setErrorEnabled(false);
                   } else {
                       mInputLayoutPasswordCheck.setError("비밀번호가 일치하지 않습니다.");
                       requestFocus(this, mPasswordCheck);
                   }
                });

        if (SharedUtils.getStringValue(this, DefineValue.PROFILE_URL) != null) {
            url = SharedUtils.getStringValue(this, DefineValue.PROFILE_URL);

            RequestOptions requestOptions = new RequestOptions()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(this)
                    .load(url)
                    .apply(requestOptions)
                    .into(mProfile);
        }
    }

    private void setDefaultSetting() {
        // Amazon Cognito 인증 공급자를 초기화합니다
        credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "ap-northeast-2:7ea4f3e2-4c1e-4875-adaa-f9b128e73e37", // 자격 증명 풀 ID
                Regions.AP_NORTHEAST_2 // 리전
        );

        s3 = new AmazonS3Client(credentialsProvider);
        // S3 버킷의 Region 이 서울일 경우 아래와 같습니다.
        s3.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));
        s3.setEndpoint("s3.ap-northeast-2.amazonaws.com");
        sexType = 0;
        transferUtility = new TransferUtility(s3, getApplicationContext());
        //개인정보 수정
        Intent intent = getIntent();
        modify = intent.getBooleanExtra("modify", false);
        if (modify) {
            mModify.setVisibility(View.VISIBLE);
            mNickName.setText(MemberSingleton.getInstance().getMember().getNickName());

            mEmail.setVisibility(View.GONE);
            mInputLayoutEmail.setVisibility(View.GONE);
            mSubmit.setVisibility(View.GONE);
            mSex.setVisibility(View.GONE);
            mPassword.setVisibility(View.GONE);
            mPasswordCheck.setVisibility(View.GONE);
        }
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE} , REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProviceRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

        if( shouldProviceRationale ) {
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle("알림")
                    .setMessage("저장소 권한이 필요합니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startPermissionRequest();
                        }
                    })
                    .create()
                    .show();
        } else {
            startPermissionRequest();
        }
    }
}