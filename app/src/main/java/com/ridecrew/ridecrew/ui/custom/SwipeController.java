package com.ridecrew.ridecrew.ui.custom;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by kim on 2018. 1. 13..
 */

public class SwipeController extends ItemTouchHelper.Callback {

    enum ButtonState {
        GONE,
        LEFT_VISIBLE,
        RIGHT_VISIBLE
    }

    private boolean swipeBack = false;
    private ButtonState buttonShowedState = ButtonState.GONE;
    private static final float buttonWidth = 300;
    private RectF buttonInstance = null;
    private RecyclerView.ViewHolder currentItemViewHolder = null;
    private SwipeControllerActions buttonsActions = null;

    public SwipeController(SwipeControllerActions buttonsActions) {
        this.buttonsActions = buttonsActions;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // RIGHT    오른쪽으로 밀기
        // LEGT     왼쪽으로 밀기
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if(swipeBack) {
            swipeBack = buttonShowedState != ButtonState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // 아이템을 누르고 스와이프하는 중
            if(buttonShowedState != ButtonState.GONE) {
                // 아이템을 누르고 이동시키고 터치를 중단한 상태
                if(buttonShowedState == ButtonState.RIGHT_VISIBLE) dX = Math.min(dX, -buttonWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            } else {
                // 아이템 누르고 이동중
                setOnTouchListner(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if(buttonShowedState == ButtonState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        currentItemViewHolder = viewHolder;
    }

    private void setOnTouchListner(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState,
                                   final boolean isCurrentlyActive) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // ACTION_DOWN      액티비티의 영역을 누름
                // ACTION_UP        액티비티의 영역을 누른것을 뗌
                // ACTION_MOVE      액티비티의 영역을 이동
                // ACTION_CANCEL    터치 취소의 모든 것을 잡아냄

                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL ||
                            event.getAction() == MotionEvent.ACTION_UP;

                if(swipeBack) {
                    // 스와이핑 하다가 터치가 중단된 상태
                    if(dX < -buttonWidth) {
                        // 버튼크기보다 왼쪽으로 스와이핑을 많이하면 오른쪽 버튼 VISIBLE 세팅
                        Log.d("KJH", "dX: " + dX + " -buttonWidth: " + -buttonWidth);
                        buttonShowedState = ButtonState.RIGHT_VISIBLE;
                    }

                    if(buttonShowedState != ButtonState.GONE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                }
                return false;
            }
        });
    }

    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder,
                                      final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    // 영역이 누른 상태
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder,
                                    final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    // 영역을 누르고 뗀 상태
                    SwipeController.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                    swipeBack = false;

                    if (buttonsActions != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())) {
                        buttonsActions.onRightClicked(viewHolder.getAdapterPosition());
                    }
                    buttonShowedState = ButtonState.GONE;
                    currentItemViewHolder = null;
                }
                return false;
            }
        });
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder) {
        float buttonWidthWithoutPadding = buttonWidth - 20;
        float corners = 16;

        View itemView = viewHolder.itemView;
        Paint p = new Paint();

        RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithoutPadding,
                                    itemView.getTop() + 16, itemView.getRight() - 16, itemView.getBottom() - 16);
        p.setColor(Color.RED);
        c.drawRoundRect(rightButton, corners, corners, p);
        drawText("DELETE", c, rightButton, p);

        buttonInstance = null;
        buttonInstance = rightButton;
    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 60;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(textSize/2), p);
    }

    public void onDraw(Canvas c) {
        if (currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder);
        }
    }
}
