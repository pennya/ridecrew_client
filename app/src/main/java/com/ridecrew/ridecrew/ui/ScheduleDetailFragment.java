package com.ridecrew.ridecrew.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.presenter.ScheduleMemberPresenter;
import com.ridecrew.ridecrew.presenter.ScheduleMemberPresenterImpl;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapView;

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

import static com.ridecrew.ridecrew.ui.BaseTMapActivity.TMAP_API_KEY;

/**
 * Created by KIM on 2018-01-03.
 */

public class ScheduleDetailFragment extends DialogFragment implements View.OnClickListener, ScheduleMemberPresenter.View {

    private ScheduleMemberPresenter presenter;
    private Schedule mCurrentSchedule;
    private TextView mTitle, mAuthor, mDate, mStartTime, mEndTime, mStartSpot, mEndSpot, mDescription;
    private Button mBtnJoin, mBtnCancel;
    private RelativeLayout mMap;
    private TMapView mTMapView;
    private View mMapFake;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater dialogInfalter = getActivity().getLayoutInflater();
        View scheduleDetailView = dialogInfalter.inflate(R.layout.fragment_schedule_detail, null);

        layoutInit(scheduleDetailView);
        setDefaultSetting();
        TMapInit();

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

        mBtnJoin = (Button)view.findViewById(R.id.btn_fragment_schedule_detail_join);
        mBtnCancel = (Button)view.findViewById(R.id.btn_fragment_schedule_detail_cancel);

        mMap = (RelativeLayout) view.findViewById(R.id.rl_fragment_schedule_detail_map_view);
        mMapFake = (View) view.findViewById(R.id.v_fragment_schedule_detail_map_view_fake);
        mBtnJoin.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

        mMapFake.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // in order to not touch TMapView
                // return true is required
                return true;
            }
        });
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

    private void TMapInit() {
        mTMapView = new TMapView(getActivity());
        mTMapView.setSKPMapApiKey(TMAP_API_KEY);
        mTMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        mTMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        mTMapView.setTileType(TMapView.TILETYPE_HDTILE);

        mMap.addView(mTMapView);

        // 출발,도착 마커 최적화해서 보이기
        //mTMapView.getDisplayTMapInfo(arrays);

        // 출발,도착 마커
        /*Bitmap start = BitmapFactory.decodeResource(context.getResources(),R.drawable.Start);
        Bitmap end = BitmapFactory.decodeResource(context.getResources(),R.drawable.End);
        Bitmap pass = BitmapFactory.decodeResource(context.getResources(),R.drawable.Pass);
        tmapview.setTMapPathIcon(start, end, pass);*/

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
