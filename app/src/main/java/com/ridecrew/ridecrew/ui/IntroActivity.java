package com.ridecrew.ridecrew.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.ridecrew.ridecrew.MainActivity;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.callback.HttpTaskCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import util.UpdateTask;

/**
 * Created by JooHyeong on 2018. 2. 22..
 */

public class IntroActivity extends Activity implements HttpTaskCallback {
    /** 로딩 화면이 떠있는 시간(밀리초단위)  **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // App Hash Key ( only test )
        // getHashKey();

        // Facebook App Event logging
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(isConnectingToInternet()) {
                    // 인터넷 연결되어있으면 업데이트 체크
                    checkUpdate();
                }else {
                    finishDialog();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void update() {
        updateDialog();
    }

    @Override
    public void next() {
        Intent mainIntent = new Intent(IntroActivity.this,MainActivity.class);
        IntroActivity.this.startActivity(mainIntent);
        IntroActivity.this.finish();
    }

    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("ridecrew", "key_hash=" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo  = connectivityManager.getActiveNetworkInfo();
            if ( (networkInfo != null) && (networkInfo.isAvailable() == true) )
            {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                {
                    if ( (networkInfo.getState() == NetworkInfo.State.CONNECTED) || (networkInfo.getState() == NetworkInfo.State.CONNECTING) )
                    {
                        return true;
                    }
                }else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    if ( (networkInfo.getState() == NetworkInfo.State.CONNECTED) || (networkInfo.getState() == NetworkInfo.State.CONNECTING) )
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void finishDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setMessage("서버에 연결할 수 없습니다. 인터넷 연결 상태를 확인하시고 다시 시도해주세요.");
        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        ad.show();
    }

    private void checkUpdate() {
        UpdateTask asnyc = new UpdateTask();
        asnyc.setInit(this, this);
        asnyc.execute();
    }

    private void updateDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setMessage("업데이트가 필요합니다. 업데이트를 누르면 앱스토어로 연결됩니다. ");
        ad.setPositiveButton("업데이트", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.ridecrew.ridecrew"));
                startActivity(intent);
            }
        });
        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        ad.show();
    }
}