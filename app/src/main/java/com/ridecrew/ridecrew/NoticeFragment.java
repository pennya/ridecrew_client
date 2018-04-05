package com.ridecrew.ridecrew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ridecrew.ridecrew.adapter.NoticeRecyclerViewAdapter;
import com.ridecrew.ridecrew.callback.NoticeRecyclerViewCallback;
import com.ridecrew.ridecrew.presenter.NoticePresenter;
import com.ridecrew.ridecrew.presenter.NoticePresenterImpl;
import com.ridecrew.ridecrew.ui.NoticeAddActivity;

import java.util.ArrayList;

import Define.DefineValue;
import Entity.ApiResult;
import Entity.MemberSingleton;
import Entity.Notice;
import util.SharedUtils;

import static android.app.Activity.RESULT_OK;
import static android.support.v7.widget.RecyclerView.*;

public class NoticeFragment extends Fragment implements NoticeRecyclerViewCallback, NoticePresenter.View, View.OnClickListener {

    private NoticeRecyclerViewCallback mCallback;
    private RecyclerView mRecyclerView;
    private NoticeRecyclerViewAdapter mRecyclerViewAdapter;
    private NoticePresenter mPresenter;
    private ArrayList<Notice> mNoticeList;
    private FloatingActionMenu mFabButton;
    private FloatingActionButton mAddButton;
    private FloatingActionButton mDeleteButton;
    private FloatingActionButton mModifyButton;
    private boolean mDeleteFlag;
    private boolean mModifyFlag;

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

    @Override
    public void deleteFucntion(int position) {
        deleteData(mNoticeList.get(position).getId());
        mNoticeList.remove(position);
        mRecyclerViewAdapter.setmItemLists(mNoticeList);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getNoticeData(ApiResult<Notice> apiResult) {

    }

    @Override
    public void deleteVisible(ImageView imageView) {
        if (mDeleteFlag) {
            imageView.setVisibility(VISIBLE);
        } else {
            imageView.setVisibility(GONE);
        }
    }

    @Override
    public void modifyFlag(boolean flag) {
        if(flag) {
            mModifyFlag = true;
        } else {
            mModifyFlag = false;
        }
    }

    @Override
    public void modifyFunction(Activity context,long id) {
        Intent intent = new Intent(getContext(),NoticeAddActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("flag",mModifyFlag);
        startActivityForResult(intent,DefineValue.NOTICE_FRAGMENT_MODIFY_CODE);
        getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DefineValue.NOTICE_FRAGMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            Notice notice = (Notice) data.getSerializableExtra("data");
            mNoticeList.add(0, notice);
            mRecyclerViewAdapter.setmItemLists(mNoticeList);
            mRecyclerViewAdapter.notifyDataSetChanged();
        }
        else if(requestCode == DefineValue.NOTICE_FRAGMENT_MODIFY_CODE && resultCode == RESULT_OK) {
            Notice notice = (Notice) data.getSerializableExtra("data");
            mNoticeList.set(mRecyclerViewAdapter.getterPosition(),notice);
            mRecyclerViewAdapter.setmItemLists(mNoticeList);
            mRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    private void layoutInit(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_notice_recycler_view);
        mFabButton = (FloatingActionMenu) view.findViewById(R.id.fab_notice_menu);
        mAddButton = (FloatingActionButton) view.findViewById(R.id.fab_notice_add);
        mDeleteButton = (FloatingActionButton) view.findViewById(R.id.fab_notice_delete);
        mModifyButton = (FloatingActionButton) view.findViewById(R.id.fab_notice_modify);
        mDeleteButton.setOnClickListener(this);
    }

    private void setDefaultSetting(View view) {
        LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewAdapter = new NoticeRecyclerViewAdapter(this, getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setHasFixedSize(true);
        mPresenter = new NoticePresenterImpl(this);
        mDeleteFlag = false;
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                for (Boolean check : mRecyclerViewAdapter.getterExpand()) {
                    if (check.booleanValue()) {
                        mRecyclerViewAdapter.setterFlag(mRecyclerViewAdapter.getterExpand());
                    } else {
                        mRecyclerViewAdapter.setterFlag(mRecyclerViewAdapter.getterExpand());
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        if(MemberSingleton.getInstance().getMember() == null ||
                MemberSingleton.getInstance().getMember().getId() == null  )
            return;

        //관리자 계정일 때 버튼 visible
         if (MemberSingleton.getInstance().getMember().getEmail().equals("pack@riding.com")) {
            //Floating Action Button OnClick
            mFabButton.setVisibility(View.VISIBLE);
            mAddButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mModifyFlag = false;
                    startActivityForResult(new Intent(getActivity(), NoticeAddActivity.class), DefineValue.NOTICE_FRAGMENT_REQUEST_CODE);
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            mDeleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mDeleteFlag) {
                        mDeleteFlag = false;
                    } else {
                        mDeleteFlag = true;
                    }
                    mRecyclerViewAdapter.notifyDataSetChanged();
                }
            });
            mModifyButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mModifyFlag) {
                        mModifyFlag = false;
                        mRecyclerViewAdapter.setterModifyFlag(false);
                        Toast.makeText(getContext(), "수정기능 비활성화", Toast.LENGTH_SHORT).show();
                    } else {
                        mModifyFlag = true;
                        Toast.makeText(getContext(), "수정기능 활성화, 수정할 리스트 선택", Toast.LENGTH_SHORT).show();
                        mRecyclerViewAdapter.setterModifyFlag(true);
                    }
                }
            });
            //FAB버튼 활성화 중에 빈 공간 클릭시 애니메이션 비활성화
            mRecyclerView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    mFabButton.close(true);
                    return false;
                }
            });
        } else {
            mFabButton.setVisibility(View.GONE);
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
