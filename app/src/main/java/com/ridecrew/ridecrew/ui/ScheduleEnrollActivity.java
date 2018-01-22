package com.ridecrew.ridecrew.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
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
        DatePickerDialog.OnDateSetListener, TimePicker.OnTimeChangedListener, ScheduleEnrollPresenter.View
{

    private ScheduleEnrollPresenter mPresenter;
    private ImageButton mBtnDatePicker, mBtnStartSpot, mBtnEndSpot;
    private TimePicker mStartTime, mEndTime;

    // 날짜 제목 출발지 도착지 상세설명
    private TextInputLayout mInputLayoutDate, mInputLayoutTitle, mInputLayoutStartSpot, mInputLayoutEndSpot, mInputLayoutDescription;
    private EditText mDateText, mTitleText, mStartSpotText, mEndSpotText, mDescriptionsText;

    private Button mEnroll;
    private String strStartTime, strEndTime, strSelectedDate, strSelectedDayOfWeek, strStartPoint, strEndPoint;
    private ScheduleDefaultEntitiy mDefaultEntity;

    private Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInit();
        setDefaultSetting();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == DefineValue.LOCATION_SELECTION_REQUEST_CODE && resultCode != RESULT_OK) {
            int key = data.getIntExtra("key", 0);
            LocationInfo item = (LocationInfo)data.getSerializableExtra("item");

            if(key == R.id.btn_activity_schedule_enroll_start_spot_location) {
                mStartSpotText.setText(item.getTitle());
                strStartPoint = String.valueOf(item.getLatitude()) + "|" + String.valueOf(item.getLongitude());
            } else {
                mEndSpotText.setText(item.getTitle());
                strEndPoint = String.valueOf(item.getLatitude()) + "|" + String.valueOf(item.getLongitude());
            }
        }
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

            case R.id.btn_activity_schedule_enroll_start_spot_location:
                startActivityForResult(new Intent(this, TMapLocationSelectActivity.class).putExtra("key", R.id.btn_activity_schedule_enroll_start_spot_location),
                        DefineValue.LOCATION_SELECTION_REQUEST_CODE);
                break;

            case R.id.btn_activity_schedule_enroll_end_spot_location:
                startActivityForResult(new Intent(this, TMapLocationSelectActivity.class).putExtra("key", R.id.btn_activity_schedule_enroll_end_spot_location),
                        DefineValue.LOCATION_SELECTION_REQUEST_CODE);
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
                if(hour < 10) {
                    strStartTime = "0" + hour + ":" + minute;
                } else {
                    strStartTime = hour + ":" + minute;
                }
                break;

            case R.id.tp_activity_schedule_enroll_end_time:
                if(hour < 10) {
                    strEndTime = "0" + hour + ":" + minute;
                } else {
                    strEndTime = hour + ":" + minute;
                }

                break;
        }
    }

    @Override
    public void moveActivity() {
        UtilsApp.ShowDialog(this, "스케줄 등록완료");
        setResult(DefineValue.SCHEDULE_FRAGMENT_REQEUST_CODE);
        finish();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void layoutInit() {
        mBtnDatePicker = (ImageButton) findViewById(R.id.btn_activity_schedule_enroll_datepicker);
        mBtnStartSpot = (ImageButton) findViewById(R.id.btn_activity_schedule_enroll_start_spot_location);
        mBtnEndSpot = (ImageButton) findViewById(R.id.btn_activity_schedule_enroll_end_spot_location);
        mStartTime = (TimePicker) findViewById(R.id.tp_activity_schedule_enroll_start_time);
        mEndTime = (TimePicker) findViewById(R.id.tp_activity_schedule_enroll_end_time);

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

        mEnroll = (Button) findViewById(R.id.btn_activity_schedule_enroll_submit);

        mDateText.setKeyListener(null);
        mStartSpotText.setKeyListener(null);
        mEndSpotText.setKeyListener(null);
        mStartSpotText.setText("지도 아이콘 클릭");
        mEndSpotText.setText("지도 아이콘 클릭");

        mStartTime.setOnTimeChangedListener(this);
        mEndTime.setOnTimeChangedListener(this);

        mDateText.addTextChangedListener(new ScheduleEnrollTextWatcher(mInputLayoutDate));
        mTitleText.addTextChangedListener(new ScheduleEnrollTextWatcher(mTitleText));
        mStartSpotText.addTextChangedListener(new ScheduleEnrollTextWatcher(mStartSpotText));
        mEndSpotText.addTextChangedListener(new ScheduleEnrollTextWatcher(mEndSpotText));
        mDescriptionsText.addTextChangedListener(new ScheduleEnrollTextWatcher(mDescriptionsText));

        mBtnDatePicker.setOnClickListener(this);
        mBtnStartSpot.setOnClickListener(this);
        mBtnEndSpot.setOnClickListener(this);
        mEnroll.setOnClickListener(this);

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
        if(strStartPoint == null || strEndPoint == null)
            return;

        Schedule schedule = Schedule.builder()
                            .setMember(mDefaultEntity.getMember())
                            .setDate(strSelectedDate)
                            .setTitle(mTitleText.getText().toString())
                            .setStartPoint(strStartPoint)
                            .setEndPoint(strEndPoint)
                            .setStartTime(strStartTime)
                            .setEndTime(strEndTime)
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
}
