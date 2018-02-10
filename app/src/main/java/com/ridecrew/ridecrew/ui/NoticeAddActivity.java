package com.ridecrew.ridecrew.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ridecrew.ridecrew.NoticeFragment;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.presenter.NoticePresenter;

import java.util.ArrayList;

import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 2. 10..
 */

public class NoticeAddActivity extends BaseToolbarActivity implements View.OnClickListener, NoticePresenter.View{

    protected EditText mTitle, mContent;
    private Button mButton;
    private NoticePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutInit();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_notice_add;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_name;
    }

    @Override
    public void onClick(View view) {
        //mPresenter.addNoticeData();
        Toast.makeText(this, "등록 완료", Toast.LENGTH_SHORT).show();
    }

    private void layoutInit() {
        mButton = (Button)findViewById(R.id.btn_activity_notice_add_button);
        mTitle = (EditText)findViewById(R.id.edt_activity_notice_add_title);
        mContent = (EditText)findViewById(R.id.edt_activity_notice_add_content);

        mButton.setOnClickListener(this);
    }

    @Override
    public void getAllNoticeData(ApiResult<ArrayList<Notice>> apiResult) {

    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, "등록 완료", Toast.LENGTH_SHORT).show();
    }
}