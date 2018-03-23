package com.ridecrew.ridecrew.ui.custom;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.ridecrew.ridecrew.R;

import java.util.Date;

/**
 * Created by kim on 2018. 1. 1..
 */

public class ItemClickDecorator implements DayViewDecorator {

    private Drawable drawable;
    private CalendarDay date;

    public ItemClickDecorator(Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.shape_circle_inset);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
        view.addSpan(new ForegroundColorSpan(Color.WHITE));
    }

    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
