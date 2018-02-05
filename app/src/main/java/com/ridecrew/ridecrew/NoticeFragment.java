package com.ridecrew.ridecrew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ridecrew.ridecrew.adapter.NoticeRecyclerViewAdapter;
import com.ridecrew.ridecrew.callback.NoticeRecyclerViewCallback;
import com.ridecrew.ridecrew.presenter.NoticePresenter;
import com.ridecrew.ridecrew.presenter.NoticePresenterImpl;
import java.util.ArrayList;

import Entity.ApiResult;
import Entity.Notice;

import static android.support.v7.widget.RecyclerView.*;

public class NoticeFragment extends Fragment implements NoticeRecyclerViewCallback, NoticePresenter.View, View.OnClickListener {

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
    public void getAllNoticeData(ApiResult<ArrayList<Notice>> apiResult) {
        //addData();
        mNoticeList = apiResult.getData();
        mRecyclerViewAdapter.setmItemLists(mNoticeList);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }


    private void layoutInit(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_notice_recycler_view);
    }

    private void setDefaultSetting(View view) {
        LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewAdapter = new NoticeRecyclerViewAdapter(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setHasFixedSize(true);
        mPresenter = new NoticePresenterImpl(this);

        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                for(Boolean check : mRecyclerViewAdapter.getterExpand()) {
                    if(check.booleanValue()) {
                        mRecyclerViewAdapter.setterFlag(mRecyclerViewAdapter.getterExpand());
                    }
                    else {
                        mRecyclerViewAdapter.setterFlag(mRecyclerViewAdapter.getterExpand());
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void loadData() {
        mPresenter.loadAllNoticeData();
    }

    //공지 추가
    private void addData() {
        mPresenter.addNoticeData(mNoticeList);
    }


}
