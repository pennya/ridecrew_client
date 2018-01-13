package com.ridecrew.ridecrew.ui;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.ridecrew.ridecrew.ui.custom.SwipeController;
import com.ridecrew.ridecrew.ui.custom.SwipeControllerActions;

import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class ParticapationListActivity extends BaseToolbarActivity implements ScheduleMemberRecyclerViewCallback, ScheduleMemberPresenter.View {

    private ScheduleMemberPresenter presenter;
    private RecyclerView recyclerView;
    private ScheduleMemberAdapter adapter;
    private static VerticalRecyclerViewFastScroller fastScroller;
    SwipeController swipeController = null;

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
        adapter.mItemLists = scheduleMembers;
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

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                adapter.mItemLists.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}
