package com.ridecrew.ridecrew.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.presenter.ScheduleEnrollPresenter;
import com.ridecrew.ridecrew.presenter.ScheduleEnrollPresenterImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Define.DefineValue;
import Entity.LocationInfo;
import Entity.Schedule;
import Entity.ScheduleDefaultEntitiy;
import util.UtilsApp;

import static util.UtilsApp.requestFocus;

public class ScheduleEnrollActivity extends BaseToolbarActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener, ScheduleEnrollPresenter.View
{

    private ScheduleEnrollPresenter mPresenter;
    private ImageButton mBtnDatePicker;

    // 날짜 제목 출발지 도착지 상세설명
    private TextInputLayout mInputLayoutDate, mInputLayoutTitle, mInputLayoutStartSpot,
            mInputLayoutEndSpot, mInputLayoutDescription;
    private EditText mDateText, mTitleText, mStartSpotText, mEndSpotText, mDescriptionsText,
            mStartTimeText, mEndTimeText;

    private Button mEnroll;
    private String strSelectedDate, strSelectedDayOfWeek;
    private ScheduleDefaultEntitiy mDefaultEntity;

    private Calendar mCalendar = Calendar.getInstance();

    private int currentTimePickerId;

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
        return R.string.app_no_title;
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

            case R.id.edt_activity_schedule_enroll_start_time:
                showHourPicker("시작시간");
                currentTimePickerId = R.id.edt_activity_schedule_enroll_start_time;
                break;

            case R.id.edt_activity_schedule_enroll_end_time:
                showHourPicker("종료시간");
                currentTimePickerId = R.id.edt_activity_schedule_enroll_end_time;
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
    public void moveActivity() {
        UtilsApp.ShowDialog(this, "스케줄 등록완료");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void layoutInit() {
        mBtnDatePicker = (ImageButton) findViewById(R.id.btn_activity_schedule_enroll_datepicker);

        mInputLayoutDate = (TextInputLayout) findViewById(R.id.edt_activity_schedule_enroll_date_layout);
        mInputLayoutTitle = (TextInputLayout) findViewById(R.id.edt_activity_schedule_enroll_title_layout);
        mInputLayoutStartSpot = (TextInputLayout) findViewById(R.id.edt_activity_schedule_enroll_start_spot_layout);
        mInputLayoutEndSpot = (TextInputLayout) findViewById(R.id.edt_activity_schedule_enroll_end_spot_layout);
        mInputLayoutDescription = (TextInputLayout) findViewById(R.id.edt_activity_schedule_enroll_descriptions_layout);

        mDateText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_date);
        mTitleText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_title);
        mStartSpotText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_start_spot);
        mEndSpotText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_end_spot);
        mDescriptionsText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_descriptions);
        mStartTimeText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_start_time);
        mEndTimeText = (EditText) findViewById(R.id.edt_activity_schedule_enroll_end_time);

        mEnroll = (Button) findViewById(R.id.btn_activity_schedule_enroll_submit);

        mDateText.setKeyListener(null);
        mStartTimeText.setKeyListener(null);
        mEndTimeText.setKeyListener(null);
        mStartTimeText.setFocusable(false);
        mEndTimeText.setFocusable(false);
        mStartTimeText.setClickable(false);
        mEndTimeText.setClickable(false);

        mDateText.addTextChangedListener(new ScheduleEnrollTextWatcher(mInputLayoutDate));
        mTitleText.addTextChangedListener(new ScheduleEnrollTextWatcher(mTitleText));
        mStartSpotText.addTextChangedListener(new ScheduleEnrollTextWatcher(mStartSpotText));
        mEndSpotText.addTextChangedListener(new ScheduleEnrollTextWatcher(mEndSpotText));
        mDescriptionsText.addTextChangedListener(new ScheduleEnrollTextWatcher(mDescriptionsText));

        mBtnDatePicker.setOnClickListener(this);
        mEnroll.setOnClickListener(this);
        mStartTimeText.setOnClickListener(this);
        mEndTimeText.setOnClickListener(this);

    }

    private void setDefaultSetting() {
        Intent intent = getIntent();
        mDefaultEntity = (ScheduleDefaultEntitiy) intent.getSerializableExtra("ScheduleDefaultEntitiy") ;

        strSelectedDate = mDefaultEntity.getDate();
        strSelectedDayOfWeek = mDefaultEntity.getDayOfWeek();
        mDateText.setText(strSelectedDate + " " + strSelectedDayOfWeek);

        mPresenter = new ScheduleEnrollPresenterImpl(this);
    }

    private void scheduleSubmit() {
        Schedule schedule = Schedule.builder()
                            .setMember(mDefaultEntity.getMember())
                            .setDate(strSelectedDate)
                            .setTitle(mTitleText.getText().toString())
                            .setStartPoint(mStartSpotText.getText().toString())
                            .setEndPoint(mEndSpotText.getText().toString())
                            .setStartTime(mStartTimeText.getText().toString())
                            .setEndTime(mEndTimeText.getText().toString())
                            .setDescriptions(mDescriptionsText.getText().toString())
                            .setStatus(1)
                            .setStartSpot(mStartSpotText.getText().toString())
                            .setEndSpot(mEndSpotText.getText().toString());

        mPresenter.scheduleEnroll(schedule);
    }

    class ScheduleEnrollTextWatcher implements TextWatcher {

        private View view;

        public ScheduleEnrollTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.edt_activity_schedule_enroll_date:
                    validateText(mDateText, mInputLayoutDate, "날짜를 선택해주세요");
                    break;
                case R.id.edt_activity_schedule_enroll_title:
                    validateText(mTitleText, mInputLayoutTitle, "제목을 입력해주세요");
                    break;
                case R.id.edt_activity_schedule_enroll_start_spot:
                    validateText(mStartSpotText, mInputLayoutStartSpot, "출발지를 입력해주세요");
                    break;
                case R.id.edt_activity_schedule_enroll_end_spot:
                    validateText(mEndSpotText, mInputLayoutEndSpot, "도착지를 입력해주세요");
                    break;
                case R.id.edt_activity_schedule_enroll_descriptions:
                    validateText(mDescriptionsText, mInputLayoutDescription, "상세정보를 입력해주세요");
                    break;
            }
        }
    }

    private boolean validateText(TextView textView, TextInputLayout textInputLayout, String msg) {
        if (textView.getText().toString().trim().isEmpty()) {
            textInputLayout.setError(msg);
            requestFocus(this, textView);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    public void showHourPicker(String title) {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);

                    if(currentTimePickerId == R.id.edt_activity_schedule_enroll_start_time) {
                        mStartTimeText.setText(String.format("%02d:%02d", hourOfDay, minute));
                    } else {
                        mEndTimeText.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle(title);
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
}
