package com.ridecrew.ridecrew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.adapter.MyPageRecyclerViewAdapter;
import com.ridecrew.ridecrew.callback.MyPageRecyclerViewCallback;
import com.ridecrew.ridecrew.presenter.MyPagePresenter;
import com.ridecrew.ridecrew.presenter.MyPagePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;

public class MyPageFragment extends Fragment implements View.OnClickListener {

    private TextView mNickName;
    private TextView mEnroll;
    private TextView mMemberPreference;
    private TextView mAppPreference;
    private Button mLogon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        layoutInit(view);
        setDefaultSetting();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fragment_mypage_enroll_join_list:
                getActivity().startActivity(new Intent(getActivity(), EnrollAndJoinListActivity.class));
                break;

            case R.id.tv_fragment_mypage_member_preference:
                break;

            case R.id.tv_fragment_mypage_app_preference:
                break;

            case R.id.btn_fragment_mypage_login_logout:
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }

    private void layoutInit(View view) {
        mNickName = (TextView) view.findViewById(R.id.tv_fragment_mypage_nickname);
        mEnroll = (TextView) view.findViewById(R.id.tv_fragment_mypage_enroll_join_list);
        mMemberPreference = (TextView) view.findViewById(R.id.tv_fragment_mypage_member_preference);
        mAppPreference = (TextView) view.findViewById(R.id.tv_fragment_mypage_app_preference);
        mLogon = (Button) view.findViewById(R.id.btn_fragment_mypage_login_logout);

        mEnroll.setOnClickListener(this);
        mMemberPreference.setOnClickListener(this);
        mAppPreference.setOnClickListener(this);
        mLogon.setOnClickListener(this);
    }

    private void setDefaultSetting() {
        mLogon.setText("로그인");
    }
}
