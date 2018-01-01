package com.ridecrew.ridecrew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ridecrew.ridecrew.R;

import Define.DefineValue;
import Entity.Member;
import Entity.MemberSingleton;
import util.SharedUtils;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == DefineValue.MY_PAGE_FRAGMENT_REQEUST_CODE) {
            loadFromSharedPreference();
        }
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
                if (!SharedUtils.getBooleanValue(getActivity(), DefineValue.IS_LOGIN)) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, DefineValue.MY_PAGE_FRAGMENT_REQEUST_CODE);
                } else {
                    mNickName.setText("");
                    mLogon.setText("로그인");
                    SharedUtils.prefClear(getActivity());
                }
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
        loadFromSharedPreference();
    }

    private void loadFromSharedPreference() {
        // Shared Preference 저장 정보 확인
        // 저장된게 있으면 MemberSingleton으로 계속 유지
        Long loginIdPk = SharedUtils.getLongValue(getActivity(), DefineValue.LOGIN_ID_PK);
        String nickName = SharedUtils.getStringValue(getActivity(), DefineValue.NICKNAME);
        String currentLoginId = SharedUtils.getStringValue(getActivity(), DefineValue.CURRENT_LOGIN_ID);
        String deviceId = SharedUtils.getStringValue(getActivity(), DefineValue.DEVICE_ID);
        if(SharedUtils.getBooleanValue(getActivity(), DefineValue.IS_LOGIN)) {
            mNickName.setText(nickName);
            mLogon.setText("로그아웃");

            MemberSingleton ms = MemberSingleton.getInstance();
            Member member = Member.builder()
                    .setEmail(currentLoginId)
                    .setId(loginIdPk)
                    .setDeviceId(deviceId);
            ms.setMember(member);
        } else {
            mLogon.setText("로그인");
        }
    }
}
