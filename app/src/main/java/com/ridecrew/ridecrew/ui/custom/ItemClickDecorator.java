package com.ridecrew.ridecrew.ui.custom;

import android.app.Activity;
import android.graphics.drawable.Drawable;

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
        drawable = context.getResources().getDrawable(R.drawable.shaple_circle);
        date = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }

    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
