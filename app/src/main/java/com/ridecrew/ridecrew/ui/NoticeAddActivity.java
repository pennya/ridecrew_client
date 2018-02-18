package com.ridecrew.ridecrew.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ridecrew.ridecrew.NoticeFragment;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.adapter.NoticeRecyclerViewAdapter;
import com.ridecrew.ridecrew.callback.NoticeModelCallback;
import com.ridecrew.ridecrew.presenter.NoticePresenter;
import com.ridecrew.ridecrew.presenter.NoticePresenterImpl;
import java.util.ArrayList;
import Entity.ApiResult;
import Entity.Notice;

/**
 * Created by JooHyeong on 2018. 2. 10..
 */

public class NoticeAddActivity extends BaseToolbarActivity implements View.OnClickListener, NoticePresenter.View {
    public static final int ADD_DATA = 1;
    public static final int REQUEST_PERMISSIONS_REQUEST_CODE = 99;

    private EditText mTitle, mContent;
    private Button mButton;
    private NoticePresenter mPresenter;
    private Spinner mSpinner;

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
        return R.string.app_name;
    }

    @Override
    public void onClick(View view) {
        if (mTitle.getText().length() == 0 || mContent.getText().length() == 0) {
            showToast("내용을 입력해주세요");
        } else {
            mPresenter.addNoticeData(Notice.builder()
                    .setTitle(mTitle.getText().toString())
                    .setContent(mContent.getText().toString())
                    .setType(mSpinner.getSelectedItemPosition())
            );
            setResult(RESULT_OK);
            overridePendingTransition(R.anim.slide_in_right,R.anim.fade_back);
            showToast("등록 완료");
            finish();
        }
    }
    @Override
    public void getAllNoticeData(ApiResult<ArrayList<Notice>> apiResult) {
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void layoutInit() {
        mButton = (Button) findViewById(R.id.btn_activity_notice_add_button);
        mTitle = (EditText) findViewById(R.id.edt_activity_notice_add_title);
        mContent = (EditText) findViewById(R.id.edt_activity_notice_add_content);
        mButton.setOnClickListener(this);
        mSpinner = (Spinner)findViewById(R.id.spin_activity_notice_type);
    }
    private void setDefaultSetting() {
        mPresenter = new NoticePresenterImpl(this);
        ArrayAdapter<String>spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.type));
        mSpinner.setAdapter(spinnerAdapter);
    }
}
