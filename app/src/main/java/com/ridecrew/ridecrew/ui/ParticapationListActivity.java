package com.ridecrew.ridecrew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.adapter.ScheduleMemberAdapter;
import com.ridecrew.ridecrew.callback.ScheduleMemberRecyclerViewCallback;
import com.ridecrew.ridecrew.presenter.ScheduleMemberPresenter;
import com.ridecrew.ridecrew.presenter.ScheduleMemberPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import Entity.ApiResult;
import Entity.Member;
import Entity.MemberSingleton;
import Entity.Schedule;
import Entity.ScheduleMember;

import static Define.DefineValue.PARTICAPATION_LIST_REQUEST_CODE;

public class ParticapationListActivity extends BaseToolbarActivity implements ScheduleMemberRecyclerViewCallback,
        ScheduleMemberPresenter.View{

    private ScheduleMemberPresenter presenter;
    private RecyclerView recyclerView;
    private ScheduleMemberAdapter adapter;
    private TextView noScheduleText;
    private int currentItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Member member = MemberSingleton.getInstance().getMember();
        if(member == null || member.getId() == null) {
            startActivityForResult(new Intent(this, LoginActivity.class), PARTICAPATION_LIST_REQUEST_CODE);
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
            return;
        }

        initLayout();
        setDefaultSetting();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_particapation_list;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_no_title;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == PARTICAPATION_LIST_REQUEST_CODE && resultCode == RESULT_OK) {
            initLayout();
            setDefaultSetting();
        }
    }

    @Override
    public void showItem(int position) {
        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showItem(Schedule schedule) {
        ScheduleDetailFragment scheduleDetailFragment = new ScheduleDetailFragment();
        scheduleDetailFragment.setSchedule(schedule);
        scheduleDetailFragment.show( getSupportFragmentManager().beginTransaction(), "dialog_schedule_detail");
    }

    @Override
    public void cancelSchedule(int itemPosition, long scheduleId, long memberId) {
        // 선택된 스케줄 삭제
        presenter.delete(scheduleId, memberId);
        adapter.addDeletePosition(itemPosition);
    }

    @Override
    public void deleteComplete() {
        adapter.deletePositionValidate();
    }

    @Override
    public void noScheduleValidate() {
        noScheduleText.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMembers(int itemPosition, long scheduleId) {
        currentItemPosition = itemPosition;
        presenter.getScheduleMemberListByScheduleId(scheduleId);
    }

    @Override
    public void showMembers(ArrayList<Member> members) {
        adapter.setMemberLists(currentItemPosition, members);
    }

    @Override
    public void moveActivity() {
        // nothing
    }

    @Override
    public void showToast(String text) {

    }

    @Override
    public void getScheduleMemberList(ApiResult<List<ScheduleMember>> result) {
        ArrayList<ScheduleMember> scheduleMembers = new ArrayList<>();
        scheduleMembers.addAll(result.getData());

        if(scheduleMembers.size() == 0) {
            noScheduleText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }

        adapter.setmItemLists(scheduleMembers);
        adapter.notifyDataSetChanged();
    }

    private void initLayout() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_activity_particapation_list);
        noScheduleText = (TextView) findViewById(R.id.tv_activity_partication_no_schedule);
    }

    private void setDefaultSetting() {
        adapter = new ScheduleMemberAdapter(this, this);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        presenter = new ScheduleMemberPresenterImpl(this);
        presenter.getScheduleMemberListByMemberId(MemberSingleton.getInstance().getMember().getId());
    }
}
