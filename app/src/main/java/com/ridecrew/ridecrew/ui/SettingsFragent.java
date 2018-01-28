package com.ridecrew.ridecrew.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.ridecrew.ridecrew.MainActivity;
import com.ridecrew.ridecrew.R;

import Define.DefineValue;
import Entity.Member;
import Entity.MemberSingleton;
import util.SharedUtils;
import util.UtilsApp;

import static android.app.Activity.RESULT_OK;

/**
 * Created by KIM on 2018-01-02.
 */

public class SettingsFragent extends PreferenceFragmentCompat {

    private Preference prefVersion, prefNotification, prefLogon, prefPaticipation;

    public SettingsFragent() {

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        prefVersion = findPreference("prefVersion");
        prefNotification = findPreference("prefNotification");
        prefLogon = findPreference("prefLogon");
        prefPaticipation = findPreference("prefPaticipation");

        setInitialConfiguration();

        String versionName = UtilsApp.getAppVersionName(getActivity());
        int versionCode = UtilsApp.getAppVersionCode(getActivity());
        prefVersion.setTitle(getResources().getString(R.string.app_name) + " v" + versionName + " (" + versionCode + ")");

        prefLogon.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (!SharedUtils.getBooleanValue(getActivity(), DefineValue.IS_LOGIN)) {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), DefineValue.MY_PAGE_FRAGMENT_REQEUST_CODE);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), DefineValue.MY_PAGE_FRAGMENT_REQEUST_CODE);
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    prefLogon.setTitle("로그인");
                    prefLogon.setSummary("print your login id");
                    SharedUtils.prefClear(getActivity());

                }
                return false;
            }
        });

        prefPaticipation.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), ParticapationListActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == DefineValue.MY_PAGE_FRAGMENT_REQEUST_CODE && resultCode == RESULT_OK) {
            Long loginIdPk = SharedUtils.getLongValue(getActivity(), DefineValue.LOGIN_ID_PK);
            String nickName = SharedUtils.getStringValue(getActivity(), DefineValue.NICKNAME);
            String currentLoginId = SharedUtils.getStringValue(getActivity(), DefineValue.CURRENT_LOGIN_ID);
            String deviceId = SharedUtils.getStringValue(getActivity(), DefineValue.DEVICE_ID);
            prefLogon.setTitle("로그아웃");
            prefLogon.setSummary(currentLoginId + " / " + nickName);

            MemberSingleton ms = MemberSingleton.getInstance();
            Member member = Member.builder()
                    .setEmail(currentLoginId)
                    .setId(loginIdPk)
                    .setDeviceId(deviceId);
            ms.setMember(member);
        }
    }

    private void setInitialConfiguration() {
        Long loginIdPk = SharedUtils.getLongValue(getActivity(), DefineValue.LOGIN_ID_PK);
        String nickName = SharedUtils.getStringValue(getActivity(), DefineValue.NICKNAME);
        String currentLoginId = SharedUtils.getStringValue(getActivity(), DefineValue.CURRENT_LOGIN_ID);
        String deviceId = SharedUtils.getStringValue(getActivity(), DefineValue.DEVICE_ID);
        if(SharedUtils.getBooleanValue(getActivity(), DefineValue.IS_LOGIN)) {
            prefLogon.setTitle("로그아웃");
            prefLogon.setSummary(currentLoginId + " / " + nickName);

            MemberSingleton ms = MemberSingleton.getInstance();
            Member member = Member.builder()
                    .setEmail(currentLoginId)
                    .setId(loginIdPk)
                    .setDeviceId(deviceId);
            ms.setMember(member);
        } else {
            prefLogon.setTitle("로그인");
        }

        // Pre-Lollipop devices
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            prefNotification.setEnabled(false);
            prefNotification.setDefaultValue(true);
        }
    }
}
