package com.ridecrew.ridecrew.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.ridecrew.ridecrew.R;

import Entity.Schedule;

/**
 * Created by KIM on 2018-01-03.
 */

public class ScheduleDetailFragment extends DialogFragment {

    private Schedule mCurrentSchedule;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater dialogInfalter = getActivity().getLayoutInflater();
        View scheduleDetailView = dialogInfalter.inflate(R.layout.fragment_schedule_detail, null);

        layoutInit(scheduleDetailView);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(scheduleDetailView)
                .setTitle(mCurrentSchedule.getTitle())
                .setNeutralButton("ok", null);

        return dialogBuilder.create();
    }

    public void setSchedule(Schedule schedule) {
        mCurrentSchedule = schedule;
    }

    private void layoutInit(View view) {

    }
}
