package com.ridecrew.ridecrew.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ridecrew.ridecrew.R;
import com.skp.Tmap.TMapView;

/**
 * Created by KJH on 2017-09-25.
 */

public abstract class BaseTMapActivity extends AppCompatActivity {
    public static final String TMAP_API_KEY = "7d54b976-ee11-3f11-a5d8-0846567726ef";

    protected TMapView mTMapView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        configureToolbar();

        mTMapView = new TMapView(this);
        mTMapView.setSKPMapApiKey(TMAP_API_KEY);
        mTMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        mTMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        mTMapView.setTileType(TMapView.TILETYPE_HDTILE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract int getLayoutResource();

    protected abstract int getTitleToolBar();

    private void configureToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(getTitleToolBar());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
