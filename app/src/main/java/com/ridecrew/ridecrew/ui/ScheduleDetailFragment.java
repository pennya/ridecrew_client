package com.ridecrew.ridecrew.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ridecrew.ridecrew.R;

import Entity.Schedule;

/**
 * Created by KIM on 2018-01-03.
 */

public class ScheduleDetailFragment extends DialogFragment {

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

        layoutInit(scheduleDetailView);
        setDefaultSetting();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(scheduleDetailView)
                .setNeutralButton("ok", null);

        return dialogBuilder.create();
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
    }
}
