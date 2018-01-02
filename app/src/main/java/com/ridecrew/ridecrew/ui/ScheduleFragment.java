package com.ridecrew.ridecrew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.adapter.ScheduleRecyclerViewApdater;
import com.ridecrew.ridecrew.callback.ScheduleRecyclerViewCallback;
import com.ridecrew.ridecrew.presenter.SchedulePresenter;
import com.ridecrew.ridecrew.presenter.SchedulePresenterImpl;
import com.ridecrew.ridecrew.ui.custom.ItemClickDecorator;
import com.ridecrew.ridecrew.ui.custom.ItemDecorator;
import com.ridecrew.ridecrew.ui.custom.SaturdayDecorator;
import com.ridecrew.ridecrew.ui.custom.SundayDecorator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Define.DefineValue;
import Entity.ApiResult;
import Entity.MemberSingleton;
import Entity.Schedule;
import Entity.ScheduleDefaultEntitiy;
import util.SharedUtils;

/**
 * Created by kim on 2017. 12. 25..
 */

public class ScheduleFragment extends Fragment implements ScheduleRecyclerViewCallback, SchedulePresenter.View, OnMonthChangedListener, OnDateSelectedListener, View.OnClickListener {

    private ItemClickDecorator itemClickDecorator;

    private MaterialCalendarView mCalendarView;
    private RecyclerView mRecyclerView;

    private ScheduleRecyclerViewApdater mRecyclerViewAdapter;
    private SchedulePresenter mPresenter;
    private List<Schedule> mScheduleLists;
    private final SimpleDateFormat DATE_FORMATTER;
    private ImageButton mEnroll;
    private CalendarDay mSelectedDate;

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
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        mSelectedDate = date;
        itemClickDecorator.setDate(mSelectedDate.getDate());
        widget.invalidateDecorators();

        ArrayList<Schedule> scheduleLists = new ArrayList<>();
        for(Schedule schedule : mScheduleLists) {
            String strDate = calendarDayToString(date);
            if(schedule.getDate().equals(strDate)) {
                scheduleLists.add(schedule);
            }
        }

        mRecyclerViewAdapter.setArrayList(scheduleLists);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        mRecyclerViewAdapter.clear();
        loadData();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_fragment_schedule_enroll:
                if( mSelectedDate == null) {
                    Toast.makeText(getActivity(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }

                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                String selectedDate = DATE_FORMATTER.format(mSelectedDate.getDate());

                Date date = new Date(mSelectedDate.getYear(), mSelectedDate.getMonth(), mSelectedDate.getDay() - 1);
                String dayOfWeek = simpledateformat.format(date);

                ScheduleDefaultEntitiy sde = new ScheduleDefaultEntitiy();
                sde.setMember(MemberSingleton.getInstance().getMember());
                sde.setDate(selectedDate);
                sde.setDayOfWeek(dayOfWeek);

                Intent intent = new Intent(getActivity(), ScheduleEnrollActivity.class);
                intent.putExtra("ScheduleDefaultEntitiy", sde);
                startActivityForResult(intent, DefineValue.SCHEDULE_FRAGMENT_REQEUST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( resultCode == DefineValue.SCHEDULE_FRAGMENT_REQEUST_CODE ) {
            loadData();
        }
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

        mCalendarView.addDecorators(
                new ItemDecorator(getActivity(), dates),
                itemClickDecorator,
                new SaturdayDecorator(),
                new SundayDecorator()
                );
    }

    @Override
    public void showItem(int position) {
        Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
    }

    private void layoutInit(View view) {
        mCalendarView = (MaterialCalendarView) view.findViewById(R.id.cv_fragment_schedule_calender);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_schedule_recycler_view);

        mEnroll = (ImageButton) view.findViewById(R.id.btn_fragment_schedule_enroll);
        mEnroll.setOnClickListener(this);
    }

    private void setDefaultSetting(View view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewAdapter = new ScheduleRecyclerViewApdater(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setHasFixedSize(true);

        mCalendarView.setOnDateChangedListener(this);
        mCalendarView.setOnMonthChangedListener(this);

        itemClickDecorator = new ItemClickDecorator(getActivity());
        if(SharedUtils.getBooleanValue(getActivity(), DefineValue.IS_LOGIN)) {
            mEnroll.setVisibility(View.VISIBLE);
        } else {
            mEnroll.setVisibility(View.GONE);
        }

        mPresenter = new SchedulePresenterImpl(this);
    }

    private void loadData() {

        CalendarDay calendarDay = mCalendarView.getCurrentDate();
        String yearAndMonth = DATE_FORMATTER.format(calendarDay.getDate()).substring(0, 7);  /* yyyy-mm-dd */

        mPresenter.loadAllScheduleData(yearAndMonth);
    }

    private String calendarDayToString(CalendarDay date) {
        return DATE_FORMATTER.format(date.getDate());
    }
}
