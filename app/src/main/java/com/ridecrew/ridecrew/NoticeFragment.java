package com.ridecrew.ridecrew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ridecrew.ridecrew.adapter.NoticeRecyclerViewAdapter;
import com.ridecrew.ridecrew.callback.NoticeRecyclerViewCallback;
import com.ridecrew.ridecrew.presenter.NoticePresenter;
import com.ridecrew.ridecrew.presenter.NoticePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import Entity.ApiResult;
import Entity.Notice;

public class NoticeFragment extends Fragment implements NoticeRecyclerViewCallback,NoticePresenter.View,View.OnClickListener{

    private RecyclerView mRecyclerView;

    private NoticeRecyclerViewAdapter mRecyclerViewAdapter;
    private NoticePresenter mPresenter;
    private ArrayList<Notice> mNoticeList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        layoutInit(view);
        setDefaultSetting(view);
        loadData();
        return view;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void showItem(int position) {

    }

    @Override
    public void getAllNoticeData(ApiResult<ArrayList<Notice>> apiResult) {
         mNoticeList = apiResult.getData();
         mRecyclerViewAdapter.setmItemLists(mNoticeList);
         mRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void layoutInit(View view) {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_fragment_notice_recycler_view);
    }

    private void setDefaultSetting(View view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewAdapter = new NoticeRecyclerViewAdapter(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setHasFixedSize(true);
        mPresenter = new NoticePresenterImpl(this);
    }

    private void loadData() {
        mPresenter.loadAllNoticeData();
    }
}
