package com.ridecrew.ridecrew.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.adapter.MyPageRecyclerViewAdapter;
import com.ridecrew.ridecrew.callback.MyPageRecyclerViewCallback;
import com.ridecrew.ridecrew.presenter.MyPagePresenter;
import com.ridecrew.ridecrew.presenter.MyPagePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;

public class EnrollAndJoinListActivity extends BaseToolbarActivity implements MyPagePresenter.View, MyPageRecyclerViewCallback {

    private RecyclerView mRecyclerView;
    private MyPageRecyclerViewAdapter mAdapter;
    private MyPagePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInit();
        setDefaultSetting();
        loadData();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_enroll_and_join_list;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_name;
    }

    @Override
    public void getAllScheduleData(ApiResult<List<Schedule>> apiResult) {
        ArrayList<Schedule> lists = new ArrayList<>();
        lists.addAll(apiResult.getData());
        mAdapter.setArrayList(lists);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void delete(Long position) {

    }

    @Override
    public void modify(Long position) {

    }

    private void layoutInit() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_fragment_mypage_recycler_view);
    }

    private void setDefaultSetting() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new MyPageRecyclerViewAdapter(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        mPresenter = new MyPagePresenterImpl(this);
    }

    private void loadData() {
        mPresenter.loadAllScheduleData(1L);
    }
}
