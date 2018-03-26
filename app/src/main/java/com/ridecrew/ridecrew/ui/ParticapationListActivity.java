package com.ridecrew.ridecrew.ui;

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
import Entity.MemberSingleton;
import Entity.ScheduleMember;

public class ParticapationListActivity extends BaseToolbarActivity implements ScheduleMemberRecyclerViewCallback,
        ScheduleMemberPresenter.View{

    private ScheduleMemberPresenter presenter;
    private RecyclerView recyclerView;
    private ScheduleMemberAdapter adapter;
    private TextView noScheduleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void showItem(int position) {
        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
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
