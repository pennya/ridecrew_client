package com.ridecrew.ridecrew.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.presenter.ScheduleEnrollPresenter;
import com.ridecrew.ridecrew.presenter.ScheduleEnrollPresenterImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Define.DefineValue;
import Entity.Schedule;
import Entity.ScheduleDefaultEntitiy;

public class ScheduleEnrollActivity extends BaseToolbarActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePicker.OnTimeChangedListener, ScheduleEnrollPresenter.View {

    private ScheduleEnrollPresenter mPresenter;
    private ImageButton mDatePicker;
    private TimePicker mStartTime;
    private TimePicker mEndTime;

    private EditText mDateText;
    private EditText mTitleText;
    private EditText mStartPointText;
    private EditText mEndPointText;
    private EditText mWayPointText;
    private EditText mDescriptionsText;

    private Button mEnroll;
    private String strStartTime;
    private String strEndTime;
    private String strSelectedDate;
    private String strSelectedDayOfWeek;
    private ScheduleDefaultEntitiy mDefaultEntity;

    private Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInit();
        setDefaultSetting();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_schedule_enroll;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_name;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_schedule_enroll_datepicker:
                new DatePickerDialog(this, this, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.btn_activity_schedule_enroll_submit:
                scheduleSubmit();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(year, monthOfYear, dayOfMonth - 1);
        String dayOfWeek = dayOfWeekFormat.format(date);

        CalendarDay caldenDay = CalendarDay.from(year, monthOfYear, dayOfMonth);
        strSelectedDate = dateFormat.format(caldenDay.getDate());
        mDateText.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth + " " + dayOfWeek);
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
        switch (timePicker.getId()) {
            case R.id.tp_activity_schedule_enroll_start_time:
                strStartTime = hour + ":" + minute;
                break;

            case R.id.tp_activity_schedule_enroll_end_time:
                strEndTime = hour + ":" + minute;
                break;
        }
    }

    @Override
    public void moveActivity() {
        setResult(DefineValue.SCHEDULE_FRAGMENT_REQEUST_CODE);
        finish();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void layoutInit() {
        mDatePicker = (ImageButton) findViewById(R.id.btn_activity_schedule_enroll_datepicker);
        mStartTime = (TimePicker) findViewById(R.id.tp_activity_schedule_enroll_start_time);
        mEndTime = (TimePicker) findViewById(R.id.tp_activity_schedule_enroll_end_time);

        mDateText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_date);
        mTitleText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_title);
        mStartPointText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_start_point);
        mEndPointText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_end_point);
        mWayPointText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_way_point);
        mDescriptionsText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_descriptions);

        mEnroll = (Button) findViewById(R.id.btn_activity_schedule_enroll_submit);

        mDateText.setKeyListener(null);
        mStartTime.setOnTimeChangedListener(this);
        mEndTime.setOnTimeChangedListener(this);

        mDatePicker.setOnClickListener(this);
        mEnroll.setOnClickListener(this);

    }

    private void setDefaultSetting() {
        Intent intent = getIntent();
        mDefaultEntity = (ScheduleDefaultEntitiy) intent.getSerializableExtra("ScheduleDefaultEntitiy") ;

        strSelectedDate = mDefaultEntity.getDate();
        strSelectedDayOfWeek = mDefaultEntity.getDayOfWeek();
        mDateText.setText(strSelectedDate + " " + strSelectedDayOfWeek);

        mPresenter = new ScheduleEnrollPresenterImpl(this);

        //For test
        mTitleText.setText("1");
        mStartPointText.setText("1");
        mEndPointText.setText("1");
        mDescriptionsText.setText("1");
        strStartTime = "11:11";
        strEndTime = "22:22";
    }

    private void scheduleSubmit() {
        Schedule schedule = Schedule.builder()
                            .setMember(mDefaultEntity.getMember())
                            .setDate(strSelectedDate)
                            .setTitle(mTitleText.getText().toString())
                            .setStartPoint(mStartPointText.getText().toString())
                            .setEndPoint(mEndPointText.getText().toString())
                            .setStartTime(strStartTime)
                            .setEndTime(strEndTime)
                            .setDescriptions(mDescriptionsText.getText().toString())
                            .setStatus(1)
                            .setStartSpot(null)
                            .setEndSpot(null);

        mPresenter.scheduleEnroll(schedule);
    }

}
