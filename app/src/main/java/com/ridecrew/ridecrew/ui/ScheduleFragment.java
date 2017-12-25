package com.ridecrew.ridecrew.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.adapter.RecyclerViewApdater;
import com.ridecrew.ridecrew.callback.ScheduleRecyclerViewCallback;
import com.ridecrew.ridecrew.presenter.SchedulePresenter;
import com.ridecrew.ridecrew.presenter.SchedulePresenterImpl;
import com.ridecrew.ridecrew.ui.custom.EventDecorator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;

/**
 * Created by kim on 2017. 12. 25..
 */

public class ScheduleFragment extends Fragment implements ScheduleRecyclerViewCallback, SchedulePresenter.View, OnMonthChangedListener, OnDateSelectedListener {

    private MaterialCalendarView mCalendarView;
    private RecyclerView mRecyclerView;
    private RecyclerViewApdater mRecyclerViewAdapter;
    private SchedulePresenter mPresenter;
    private List<Schedule> mScheduleLists;
    private final SimpleDateFormat DATE_FORMATTER;

    public ScheduleFragment() {
        DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_layout, container, false);

        layoutInit(view);
        setDefaultSetting(view);
        loadData();

        return view;
    }

    @Override
    public void getAllScheduleData(ApiResult<List<Schedule>> apiResult) {
        mScheduleLists = apiResult.getData();
        ArrayList<CalendarDay> dates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        for(Schedule schedule : apiResult.getData()) {
            try {
                calendar.setTime(DATE_FORMATTER.parse(schedule.getDate()));
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        mCalendarView.addDecorator(new EventDecorator(Color.RED, dates));
    }

    @Override
    public void showItem(int position) {
        Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        ArrayList<Schedule> scheduleLists = new ArrayList<>();
        for(Schedule schedule : mScheduleLists) {
            if(schedule.getDate().equals(date.getYear() + "-" + (date.getMonth() + 1)  + "-" + date.getDay())) {
                scheduleLists.add(schedule);
            }
        }

        mRecyclerViewAdapter.setArrayList(scheduleLists);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        mRecyclerViewAdapter.clear();
    }

    private void layoutInit(View view) {
        mCalendarView = (MaterialCalendarView) view.findViewById(R.id.cv_fragment_schedule_calender);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_schedule_recycler_view);
    }

    private void setDefaultSetting(View view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewAdapter = new RecyclerViewApdater(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setHasFixedSize(true);

        mCalendarView.setOnDateChangedListener(this);
        mCalendarView.setOnMonthChangedListener(this);
    }

    private void loadData() {
        mPresenter = new SchedulePresenterImpl(this);
        mPresenter.loadAllScheduleData();
    }

}
