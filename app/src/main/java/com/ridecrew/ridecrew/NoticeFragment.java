package com.ridecrew.ridecrew;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ridecrew.ridecrew.adapter.NoticeRecyclerViewAdapter;
import com.ridecrew.ridecrew.callback.NoticeRecyclerViewCallback;
import com.ridecrew.ridecrew.presenter.LoginPresenter;
import com.ridecrew.ridecrew.presenter.NoticePresenter;
import com.ridecrew.ridecrew.presenter.NoticePresenterImpl;
import com.ridecrew.ridecrew.ui.NoticeAddActivity;

import java.util.ArrayList;

import Define.DefineValue;
import Entity.ApiResult;
import Entity.Member;
import Entity.MemberSingleton;
import Entity.Notice;
import util.SharedUtils;

import static android.support.v7.widget.RecyclerView.*;

public class NoticeFragment extends Fragment implements NoticeRecyclerViewCallback, NoticePresenter.View, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private NoticeRecyclerViewAdapter mRecyclerViewAdapter;
    private NoticePresenter mPresenter;
    private ArrayList<Notice> mNoticeList;
    private ImageButton mAddNotice;

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
        addData();
        mNoticeList = apiResult.getData();
        mRecyclerViewAdapter.setmItemLists(mNoticeList);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }


    private void layoutInit(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_notice_recycler_view);
        mAddNotice = (ImageButton) view.findViewById(R.id.btn_fragment_notice_add);
        mAddNotice.setOnClickListener(this);
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
        //관리자 계정일 때 버튼 visible
        if(MemberSingleton.getInstance().getMember().getMemberType() == 0) {
            mAddNotice.setVisibility(View.VISIBLE);
            mAddNotice.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),NoticeAddActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            mAddNotice.setVisibility(View.GONE);
        }
    }

    private void loadData() {
        mPresenter.loadAllNoticeData();
    }

    //공지 추가
    private void addData() {
        mPresenter.addNoticeData(mNoticeList);
    }
    //공지 삭제
    private void deleteData(long noticeId) {
        mPresenter.deleteNoticeData(noticeId);
    }


}
