package com.ridecrew.ridecrew.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ridecrew.ridecrew.R;

/**
 * Created by kim on 2018. 3. 5..
 */

public class SignInAgreeActivity extends BaseToolbarActivity {

    private TextView tvContent1, tvContent2;
    private CheckBox cbCheck1, cbCheck2;
    private Button btnSubmit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInit();
        setDefaultSettings();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_signin_agree;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_no_title;
    }

    private void layoutInit() {
        tvContent1 = (TextView)findViewById(R.id.tv_content_1);
        tvContent2 = (TextView)findViewById(R.id.tv_content_2);
        cbCheck1 = (CheckBox) findViewById(R.id.btn_agree_1);
        cbCheck2 = (CheckBox) findViewById(R.id.btn_agree_2);
        btnSubmit = (Button) findViewById(R.id.btn_fragment_signin_agree_submit);

        cbCheck1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbCheck1.isChecked() && cbCheck2.isChecked()) {
                    btnSubmit.setClickable(true);
                    btnSubmit.setBackgroundColor(Color.parseColor("#33B5E5"));
                } else {
                    btnSubmit.setClickable(false);
                    btnSubmit.setBackgroundColor(Color.GRAY);
                }
            }
        });

        cbCheck2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbCheck1.isChecked() && cbCheck2.isChecked()) {
                    btnSubmit.setClickable(true);
                    btnSubmit.setBackgroundColor(Color.parseColor("#33B5E5"));
                } else {
                    btnSubmit.setClickable(false);
                    btnSubmit.setBackgroundColor(Color.GRAY);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, getIntent());
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
            }
        });
    }

    private void setDefaultSettings() {
        tvContent1.setMovementMethod(new ScrollingMovementMethod());
        tvContent2.setMovementMethod(new ScrollingMovementMethod());

        btnSubmit.setClickable(false);
        btnSubmit.setBackgroundColor(Color.GRAY);
    }
}
