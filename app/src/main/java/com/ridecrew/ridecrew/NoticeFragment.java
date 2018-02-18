package com.ridecrew.ridecrew;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ridecrew.ridecrew.adapter.NoticeRecyclerViewAdapter;
import com.ridecrew.ridecrew.callback.NoticeRecyclerViewCallback;
import com.ridecrew.ridecrew.presenter.NoticePresenter;
import com.ridecrew.ridecrew.presenter.NoticePresenterImpl;
import com.ridecrew.ridecrew.ui.NoticeAddActivity;
import com.ridecrew.ridecrew.ui.custom.SwipeController;
import com.ridecrew.ridecrew.ui.custom.SwipeControllerActions;

import java.util.ArrayList;

import Define.DefineValue;
import Entity.ApiResult;
import Entity.MemberSingleton;
import Entity.Notice;
import util.SharedUtils;

import static android.app.Activity.RESULT_OK;
import static android.support.v7.widget.RecyclerView.*;

public class NoticeFragment extends Fragment implements NoticeRecyclerViewCallback, NoticePresenter.View, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private NoticeRecyclerViewAdapter mRecyclerViewAdapter;
    private NoticePresenter mPresenter;
    private ArrayList<Notice> mNoticeList;
    private FloatingActionMenu mFabButton;
    private FloatingActionButton mAddButton;
    private FloatingActionButton mDeleteButton;

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
        mFabButton = (FloatingActionMenu) view.findViewById(R.id.fab_notice_menu);
        mAddButton = (FloatingActionButton) view.findViewById(R.id.fab_notice_add);
        mDeleteButton = (FloatingActionButton) view.findViewById(R.id.fab_notice_delete);
        mFabButton.setOnClickListener(this);
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
        if(!SharedUtils.getBooleanValue(getContext(),DefineValue.IS_LOGIN) || MemberSingleton.getInstance().getMember().getId() != 17) {
            mFabButton.setVisibility(View.GONE);
        } else if( MemberSingleton.getInstance().getMember().getId()==17) {
            mFabButton.setVisibility(View.VISIBLE);
            mAddButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(getActivity(),NoticeAddActivity.class),DefineValue.NOTICE_FRAGMENT_REQUEST_CODE);
                    getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                }
            });
            mDeleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == DefineValue.NOTICE_FRAGMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            loadData();
        }
    }

    private void loadData() {
        mPresenter.loadAllNoticeData();
    }

    //공지 삭제
    private void deleteData(long noticeId) {
        mPresenter.deleteNoticeData(noticeId);
    }

}
