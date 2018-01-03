package com.ridecrew.ridecrew.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ridecrew.ridecrew.R;

/**
 * 로그인 팝업
 */
public class LoginDialogActivity extends Activity implements View.OnClickListener{
    static final int[] BUTTONS = {
            R.id.btnLogin,
            R.id.btnCancel
    };
    TextView txtEnlist;
    EditText et;
    String sfName = "myFile";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_dialog);

        et = (EditText)findViewById(R.id.txtUserId);

        SharedPreferences sf = getSharedPreferences(sfName,0);
        String str = sf.getString("name","");
        et.setText(str);

        //dialog size
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int)(display.getWidth() * 0.9);
        int height = (int)(display.getHeight()*0.6);
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        //button, textview setOnClickListener
        for(int btnId : BUTTONS) {
            Button btn = (Button)findViewById(btnId);
            btn.setOnClickListener(this);
        }
        txtEnlist = (TextView)findViewById(R.id.txtEnlist);
        txtEnlist.setOnClickListener(this);
    }

    protected void onStop() {
        // Activity 가 종료되기 전에 저장한다
        // SharedPreferences 에 설정값(특별히 기억해야할 사용자 값)을 저장하기
        super.onStop();
        SharedPreferences sf = getSharedPreferences(sfName, 0);
        SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
        String str = et.getText().toString(); // 사용자가 입력한 값
        editor.putString("name", str); // 입력
        editor.putString("xx", "xx"); // 입력
        editor.commit(); // 파일에 최종 반영함
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnLogin:
                Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.txtEnlist:
                Intent enlist = new Intent(this,EnlistDialogActivity.class);

        }
    }
}