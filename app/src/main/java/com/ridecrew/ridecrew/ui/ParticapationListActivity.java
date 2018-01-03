package com.ridecrew.ridecrew.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.adapter.ScheduleRecyclerViewApdater;
import com.ridecrew.ridecrew.callback.ScheduleRecyclerViewCallback;

import java.util.ArrayList;

import Entity.Member;
import Entity.Schedule;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class ParticapationListActivity extends BaseToolbarActivity implements ScheduleRecyclerViewCallback {

    private RecyclerView recyclerView;
    private ScheduleRecyclerViewApdater adapter;
    private static VerticalRecyclerViewFastScroller fastScroller;
    private ArrayList<Schedule> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lists = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            lists.add(Schedule.builder().setTitle(i + "").setDescriptions(i+"").setMember(Member.builder().setNickName(i+"")));
        }
        adapter = new ScheduleRecyclerViewApdater(this);
        adapter.setArrayList(lists);

        recyclerView = (RecyclerView) findViewById(R.id.appList);
        fastScroller = (VerticalRecyclerViewFastScroller) findViewById(R.id.fast_scroller);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
        fastScroller.setRecyclerView(recyclerView);
        recyclerView.setOnScrollListener(fastScroller.getOnScrollListener());

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
}
