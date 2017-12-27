package com.ridecrew.ridecrew.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.adapter.MyPageRecyclerViewAdapter;
import com.ridecrew.ridecrew.callback.MyPageRecyclerViewCallback;
import com.ridecrew.ridecrew.presenter.MyPagePresenter;
import com.ridecrew.ridecrew.presenter.MyPagePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;

public class MyPageFragment extends Fragment implements MyPagePresenter.View, MyPageRecyclerViewCallback {

    private RecyclerView mRecyclerView;
    private MyPageRecyclerViewAdapter mAdapter;
    private MyPagePresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        layoutInit(view);
        setDefaultSetting(view);
        loadData();

        return view;
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

    private void layoutInit(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_mypage_recycler_view);
    }

    private void setDefaultSetting(View view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
