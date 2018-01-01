package com.ridecrew.ridecrew.ui.custom;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.ridecrew.ridecrew.R;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by kim on 2017. 12. 25..
 */

public class ItemDecorator implements DayViewDecorator {

    private final Drawable drawable;
    private final HashSet<CalendarDay> dates;

    public ItemDecorator(Activity context, Collection<CalendarDay> dates) {
        drawable = context.getResources().getDrawable(R.drawable.shape_rectangle);
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
        view.addSpan(new ForegroundColorSpan(Color.DKGRAY));
    }
}