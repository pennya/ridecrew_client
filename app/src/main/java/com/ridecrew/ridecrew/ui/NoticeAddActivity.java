package com.ridecrew.ridecrew.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.presenter.NoticePresenter;
import com.ridecrew.ridecrew.presenter.NoticePresenterImpl;
import java.util.ArrayList;

import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 2. 10..
 */

public class NoticeAddActivity extends BaseToolbarActivity implements NoticePresenter.View {
    private Activity context;
    private EditText mTitle, mContent;
    private Button mEnroll;
    private Button mModifyButton;
    private NoticePresenter mPresenter;
    private Spinner mSpinner;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutInit();
        setDefaultSetting();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_notice_add;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_no_title;
    }

    @Override
    public void getNoticeData(ApiResult<Notice> apiResult) {
        Notice n = apiResult.getData();
        Intent intent = new Intent();
        intent.putExtra("data", n);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void getAllNoticeData(ApiResult<ArrayList<Notice>> apiResult) {

    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void layoutInit() {
        mEnroll = (Button) findViewById(R.id.btn_activity_notice_add_button);
        mModifyButton = (Button) findViewById(R.id.btn_activity_notice_notify_button);
        mTitle = (EditText) findViewById(R.id.edt_activity_notice_add_title);
        mContent = (EditText) findViewById(R.id.edt_activity_notice_add_content);
        mSpinner = (Spinner) findViewById(R.id.spin_activity_notice_type);
    }

    private void setDefaultSetting() {
        mPresenter = new NoticePresenterImpl(this);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.type));
        mSpinner.setAdapter(spinnerAdapter);
        Intent intent = new Intent(this.getIntent());
        id = intent.getLongExtra("id",-1);
        boolean flag = intent.getBooleanExtra("flag",false);
        if(flag) {
            mEnroll.setVisibility(View.GONE);
            mModifyButton.setVisibility(View.VISIBLE);
        } else {
            mEnroll.setVisibility(View.VISIBLE);
            mModifyButton.setVisibility(View.GONE);
        }

        //등록 버튼
        mEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTitle.getText().length() == 0 || mContent.getText().length() == 0) {
                    showToast("내용을 입력해주세요");
                } else {
                    addData(mTitle, mContent, mSpinner.getSelectedItemPosition());
                }
            }
        });
        //수정 버튼
        mModifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTitle.getText().length() == 0 || mContent.getText().length() == 0) {
                    showToast("내용을 입력해주세요");
                } else {
                    modifyData(mTitle, mContent, mSpinner.getSelectedItemPosition());
                }
            }
        });
    }

    //공지 추가
    private void addData(TextView title, TextView content, int type) {
        Notice notice = Notice.builder()
                .setTitle(title.getText().toString())
                .setContent(content.getText().toString())
                .setType(type);
        mPresenter.addNoticeData(notice);
    }

    //수정할 공지내용
    private void modifyData(TextView title, TextView content, int type) {
        Notice notice = Notice.builder()
                .setTitle(title.getText().toString())
                .setContent(content.getText().toString())
                .setType(type);
        mPresenter.modifyNoticeData(id,notice);
    }
}
