package com.ridecrew.ridecrew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.adapter.GalleryAdapter;
import com.ridecrew.ridecrew.callback.GalleryCallback;
import com.ridecrew.ridecrew.presenter.GalleryPresenter;
import com.ridecrew.ridecrew.presenter.GalleryPresenterImpl;

import java.util.ArrayList;

import Define.DefineValue;
import Entity.ApiResult;
import Entity.Gallery;
import Entity.GalleryLike;
import Entity.MemberSingleton;
import util.SharedUtils;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment implements View.OnClickListener, GalleryPresenter.View, GalleryCallback {

    private GalleryPresenter presenter;
    private RecyclerView recyclerView;
    private GalleryAdapter adapter;
    private ImageButton btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        initLayout(view);
        setDefaultSettings();
        loadData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setDefaultSettings();
        loadData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fragment_gallery_add:
                startActivityForResult(new Intent(getActivity(), FileUploadActivity.class), DefineValue.GALLERY_FRAGMENT_REQUEST_CODE);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == DefineValue.GALLERY_FRAGMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            Gallery gallery = Gallery.builder()
                                            .setMember(MemberSingleton.getInstance().getMember())
                                            .setTitle("NO_TITLE")
                                            .setContent("NO_CONTENT")
                                            .setImageUrl(data.getStringExtra("imageUrl"));
            presenter.addGallery(gallery);
        }
    }

    @Override
    public void getGalleries(ApiResult<ArrayList<Gallery>> result) {
        adapter.items = result.getData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getGallery(ApiResult<Gallery> result) {
        adapter.items.add(0, result.getData());
        adapter.notifyDataSetChanged();

        //Toast.makeText(getActivity(), result.getData().getImageUrl() + " 등록완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void like(Gallery gallery) {
        presenter.like(GalleryLike.builder()
                                .setGallery(gallery)
                                .setMember(MemberSingleton.getInstance().getMember())
        );
    }

    @Override
    public void share(Gallery gallery) {
        Toast.makeText(getContext(), "준비중입니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getGalleryLike(ApiResult<GalleryLike> result) {
        Toast.makeText(getActivity(), "좋아요 성공", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getGalleryDisLike(ApiResult<Void> result) {

    }

    private void initLayout(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_gallery);
        btnAdd = (ImageButton) view.findViewById(R.id.btn_fragment_gallery_add);
        btnAdd.setOnClickListener(this);
    }

    private void setDefaultSettings() {
        adapter = new GalleryAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        presenter = new GalleryPresenterImpl(this);

        if(SharedUtils.getBooleanValue(getActivity(), DefineValue.IS_LOGIN)) {
            btnAdd.setVisibility(View.VISIBLE);
        } else {
            btnAdd.setVisibility(View.GONE);
        }
    }

    private void loadData() {
        presenter.loadGalleries();
    }
}
