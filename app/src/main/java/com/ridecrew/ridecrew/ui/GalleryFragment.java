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
import com.ridecrew.ridecrew.presenter.GalleryPresenter;
import com.ridecrew.ridecrew.presenter.GalleryPresenterImpl;

import java.util.ArrayList;

import Define.DefineValue;
import Entity.ApiResult;
import Entity.Gallery;
import Entity.MemberSingleton;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment implements View.OnClickListener, GalleryPresenter.View {

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
                                            .setTitle("TEST TITLE")
                                            .setContent("TEST CONTENT")
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

        Toast.makeText(getActivity(), result.getData().getImageUrl() + " 등록완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void initLayout(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_gallery);
        btnAdd = (ImageButton) view.findViewById(R.id.btn_fragment_gallery_add);
    }

    private void setDefaultSettings() {
        adapter = new GalleryAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        btnAdd.setOnClickListener(this);

        presenter = new GalleryPresenterImpl(this);
    }

    private void loadData() {
        presenter.loadGalleries();
    }
}
