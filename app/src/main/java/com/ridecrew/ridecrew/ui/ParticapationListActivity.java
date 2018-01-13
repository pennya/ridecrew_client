package com.ridecrew.ridecrew.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.adapter.ScheduleMemberAdapter;
import com.ridecrew.ridecrew.adapter.ScheduleRecyclerViewApdater;
import com.ridecrew.ridecrew.callback.ScheduleMemberRecyclerViewCallback;
import com.ridecrew.ridecrew.callback.ScheduleRecyclerViewCallback;
import com.ridecrew.ridecrew.presenter.ScheduleMemberPresenter;
import com.ridecrew.ridecrew.presenter.ScheduleMemberPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import Entity.ApiResult;
import Entity.Member;
import Entity.MemberSingleton;
import Entity.Schedule;
import Entity.ScheduleMember;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class ParticapationListActivity extends BaseToolbarActivity implements ScheduleMemberRecyclerViewCallback, ScheduleMemberPresenter.View {

    private ScheduleMemberPresenter presenter;
    private RecyclerView recyclerView;
    private ScheduleMemberAdapter adapter;
    private static VerticalRecyclerViewFastScroller fastScroller;

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
        return R.string.app_name;
    }

    @Override
    public void showItem(int position) {
        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
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
        adapter.setArrayList(scheduleMembers);
        adapter.notifyDataSetChanged();
    }

    private void initLayout() {
        recyclerView = (RecyclerView) findViewById(R.id.appList);
    }

    private void setDefaultSetting() {
        adapter = new ScheduleMemberAdapter(this);

        fastScroller = (VerticalRecyclerViewFastScroller) findViewById(R.id.fast_scroller);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
        fastScroller.setRecyclerView(recyclerView);
        recyclerView.setOnScrollListener(fastScroller.getOnScrollListener());

        presenter = new ScheduleMemberPresenterImpl(this);
        presenter.getScheduleMemberListByMemberId(MemberSingleton.getInstance().getMember().getId());
    }
}
