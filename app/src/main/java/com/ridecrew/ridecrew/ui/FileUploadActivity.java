package com.ridecrew.ridecrew.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.ridecrew.ridecrew.BuildConfig;
import com.ridecrew.ridecrew.R;

import java.io.File;

import Define.DefineValue;
import Entity.Member;
import Entity.MemberSingleton;

import static Define.DefineValue.PARTICAPATION_LIST_REQUEST_CODE;

public class FileUploadActivity extends BaseToolbarActivity  implements View.OnClickListener {

    public static final int PICK_FROM_ALBUM = 1;
    public static final int TAKE_PICTURE = 0;
    public static final int REQUEST_PERMISSIONS_REQUEST_CODE = 99;

    // AWS S3
    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonS3 s3;
    private TransferUtility transferUtility;
    private TransferObserver observer;

    private Button btnUpload, btnSelect;
    private ImageView imageview;
    private File f;

    private String imagePath;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkPermissions() && Build.VERSION.SDK_INT >= 23) {
            requestPermissions();
        }

        Member member = MemberSingleton.getInstance().getMember();
        if(member == null || member.getId() == null) {
            startActivityForResult(new Intent(this, LoginActivity.class), PARTICAPATION_LIST_REQUEST_CODE);
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
            return;
        }

        initLayout();
        setDefaultSettings();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_file_upload;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_no_title;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_file_upload_upload_to_server:
                //Log.d("KJH", f.getAbsolutePath());
                observer = transferUtility.upload(
                        "ridecrew",
                        "images/" + f.getName(),
                        f
                );
                observer.setTransferListener(new TransferListener() {
                    @Override
                    public void onStateChanged(int id, TransferState state) {
                        if(state.name().startsWith("COMPLETE")) {
                            String finalUrl = "https://s3.ap-northeast-2.amazonaws.com/"
                                    + observer.getBucket()
                                    + "/" + observer.getKey();


                            Intent intent = new Intent();
                            intent.putExtra("imageUrl", finalUrl);
                            setResult(RESULT_OK, intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
                            finish();
                        }
                    }

                    @Override
                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                        if(bytesTotal == 0)
                            return;

                        int percentage = (int) (bytesCurrent/bytesTotal * 100);
                        //Log.d("KJH", "percentage : " + percentage);
                    }

                    @Override
                    public void onError(int id, Exception ex) {
                        //Log.d("KJH", ex.getMessage());
                    }
                });
                break;
            case R.id.btn_activity_file_upload_select:
                selectImage();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PARTICAPATION_LIST_REQUEST_CODE:
                initLayout();
                setDefaultSettings();
                break;

            case PICK_FROM_ALBUM:
                mImageUri = data.getData();

                // absolute path로 해야 가능
                // E/UploadTask: Failed to upload: 8 due to Unable to execute HTTP request: Write error: ssl=0x78effcc240: I/O error during system call, Connection reset by peer
                imagePath = getPathFromUri(mImageUri);
                f = new File(imagePath);
                imageview.setImageURI(mImageUri);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE:
                if( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {

                } else {
                    new AlertDialog.Builder(this)
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

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private String getPathFromUri(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null );
        cursor.moveToNext();
        String path = cursor.getString( cursor.getColumnIndex( "_data" ) );
        cursor.close();

        return path;
    }


    private void initLayout() {
        btnUpload = (Button) findViewById(R.id.btn_activity_file_upload_upload_to_server);
        btnSelect = (Button) findViewById(R.id.btn_activity_file_upload_select);
        imageview = (ImageView) findViewById(R.id.iv_activity_file_upload_image);

        btnUpload.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
    }

    private void setDefaultSettings() {
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

        transferUtility = new TransferUtility(s3, getApplicationContext());
    }

    private void startPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE} , REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProviceRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

        if( shouldProviceRationale ) {
            new AlertDialog.Builder(this)
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
