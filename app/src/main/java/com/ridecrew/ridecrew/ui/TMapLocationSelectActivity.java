package com.ridecrew.ridecrew.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ridecrew.ridecrew.R;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import Define.DefineValue;
import Entity.LocationInfo;
import util.BaseTMapActivity;
import util.UtilsApp;

public class TMapLocationSelectActivity extends BaseTMapActivity implements TMapView.OnEnableScrollWithZoomLevelCallback,
                                                                                View.OnClickListener,
                                                                                TMapData.ConvertGPSToAddressListenerCallback{

    private RelativeLayout mRootLayout;
    private Button mCompleteButton;
    private Button mCloseButton;
    private TextView mLocationInfo;

    private TMapMarkerItem mCenterMarkerItem;
    private TMapData mTMapData;

    private LocationInfo mLocationInfoEntity;
    private int mKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInit();
        TMapInit();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_tmap_location_select;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_name;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEnableScrollWithZoomLevelEvent(float v, TMapPoint tMapPoint) {
        mCenterMarkerItem.setTMapPoint(tMapPoint);
        mTMapData.convertGpsToAddress(tMapPoint.getLatitude(), tMapPoint.getLongitude(), this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tmap_location_select_complete:
                if(mLocationInfoEntity == null) {
                    UtilsApp.ShowDialog(this, "로딩중입니다");
                    return;
                }

                LocationInfo Item = new LocationInfo()
                                            .setTitle(mLocationInfoEntity.getTitle())
                                            .setLatitude(mLocationInfoEntity.getLatitude())
                                            .setLongitude(mLocationInfoEntity.getLongitude());

                Intent intent = new Intent();
                intent.putExtra("item", Item);
                intent.putExtra("key", mKey);
                setResult(DefineValue.LOCATION_SELECTION_REQUEST_CODE, intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btn_tmap_location_select_close:
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    public void onConvertToGPSToAddress(String strAddress) {
        mLocationInfoEntity = new LocationInfo()
                                .setTitle(strAddress)
                                .setLatitude(mTMapView.getCenterPoint().getLatitude())
                                .setLongitude(mTMapView.getCenterPoint().getLongitude())
                                .build();

        mLocationInfo.setText(strAddress);
    }

    private void layoutInit() {
        Intent intent = getIntent();
        mKey = intent.getIntExtra("key", 0);

        mRootLayout = (RelativeLayout)findViewById(R.id.rl_tmap_view);
        mCompleteButton = (Button)findViewById(R.id.btn_tmap_location_select_complete);
        mCloseButton = (Button)findViewById(R.id.btn_tmap_location_select_close);
        mCompleteButton.setOnClickListener(this);
        mCloseButton.setOnClickListener(this);
        mLocationInfo = (TextView)findViewById(R.id.tv_tmap_location_to_address);
    }

    private void TMapInit() {
        mRootLayout.addView(mTMapView);

        // 화면 중앙에 마커 표시
        mTMapView.setOnEnableScrollWithZoomLevelListener(this);
        mCenterMarkerItem = new TMapMarkerItem();
        mCenterMarkerItem.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_location_click));
        mCenterMarkerItem.setTMapPoint(mTMapView.getCenterPoint());
        mCenterMarkerItem.setVisible(mCenterMarkerItem.VISIBLE);
        mTMapView.addMarkerItem("centerMarkerItem", mCenterMarkerItem);

        mTMapData = new TMapData();
        mTMapData.convertGpsToAddress(mTMapView.getCenterPoint().getLatitude(),
                mTMapView.getCenterPoint().getLongitude(), this);
    }
}
