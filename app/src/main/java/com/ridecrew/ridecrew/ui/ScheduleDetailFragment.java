package com.ridecrew.ridecrew.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.presenter.ScheduleMemberPresenter;
import com.ridecrew.ridecrew.presenter.ScheduleMemberPresenterImpl;

import java.util.List;

import Define.DefineValue;
import Entity.ApiResult;
import Entity.Member;
import Entity.MemberSingleton;
import Entity.Schedule;
import Entity.ScheduleDefaultEntitiy;
import Entity.ScheduleMember;
import util.SharedUtils;
import util.UtilsApp;

/**
 * Created by KIM on 2018-01-03.
 */

public class ScheduleDetailFragment extends DialogFragment implements View.OnClickListener, ScheduleMemberPresenter.View {

    private ScheduleMemberPresenter presenter;
    private Schedule mCurrentSchedule;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mDate;
    private TextView mStartTime;
    private TextView mEndTime;
    private TextView mStartSpot;
    private TextView mEndSpot;
    private TextView mDescription;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater dialogInfalter = getActivity().getLayoutInflater();
        View scheduleDetailView = dialogInfalter.inflate(R.layout.fragment_schedule_detail, null);

        scheduleDetailView.findViewById(R.id.btn_fragment_schedule_detail_join).setOnClickListener(this);
        scheduleDetailView.findViewById(R.id.btn_fragment_schedule_detail_modify).setOnClickListener(this);
        scheduleDetailView.findViewById(R.id.btn_fragment_schedule_detail_cancel).setOnClickListener(this);

        layoutInit(scheduleDetailView);
        setDefaultSetting();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(scheduleDetailView);

        return dialogBuilder.create();
    }

    @Override
    public void moveActivity() {
        //nothing
    }

    @Override
    public void showToast(String text) {
        UtilsApp.ShowDialog(getActivity(), text);
    }

    @Override
    public void getScheduleMemberList(ApiResult<List<ScheduleMember>> lists) {
        //nothing
    }

    public void setSchedule(Schedule schedule) {
        mCurrentSchedule = schedule;
    }

    private void layoutInit(View view) {
        mTitle = (TextView)view.findViewById(R.id.tv_fragment_schedule_detail_title);
        mAuthor = (TextView)view.findViewById(R.id.tv_fragment_schedule_detail_author);
        mDate = (TextView)view.findViewById(R.id.tv_fragment_schedule_detail_date);
        mStartTime = (TextView)view.findViewById(R.id.tv_fragment_schedule_detail_start_time);
        mEndTime = (TextView)view.findViewById(R.id.tv_fragment_schedule_detail_end_time);
        mStartSpot = (TextView)view.findViewById(R.id.tv_fragment_schedule_detail_start_spot);
        mEndSpot = (TextView)view.findViewById(R.id.tv_fragment_schedule_detail_end_spot);
        mDescription = (TextView)view.findViewById(R.id.tv_fragment_schedule_detail_descriptions);
    }

    private void setDefaultSetting() {
        mTitle.setText(mCurrentSchedule.getTitle());
        mAuthor.setText(mCurrentSchedule.getMember().getNickName());
        mDate.setText(mCurrentSchedule.getDate());
        mStartTime.setText(mCurrentSchedule.getStartTime());
        mEndTime.setText(mCurrentSchedule.getEndTime());
        mStartSpot.setText(mCurrentSchedule.getStartSpot());
        mEndSpot.setText(mCurrentSchedule.getEndSpot());
        mDescription.setText(mCurrentSchedule.getDescriptions());

        presenter = new ScheduleMemberPresenterImpl(this);
    }

    //다이어로그창에서 참가하기, 수정하기, 취소 터치했을 때 이벤트
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_fragment_schedule_detail_join:
                //로그인 된 상태에서는 해당 계정의 정보를 보냄
                if(SharedUtils.getBooleanValue(getContext(), DefineValue.IS_LOGIN)) {
                    presenter.add(ScheduleMember.builder()
                                                .setMember(MemberSingleton.getInstance().getMember())
                                                .setSchedule(mCurrentSchedule));
                }
                //로그인이 안되어있는 경우 로그인 창으로 이동
                else {
                    Intent intent = new Intent(getContext(),LoginActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                break;
            case R.id.btn_fragment_schedule_detail_cancel:
                getDialog().dismiss();
                break;
        }
    }
}
