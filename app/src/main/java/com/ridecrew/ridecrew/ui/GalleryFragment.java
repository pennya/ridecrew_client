package com.ridecrew.ridecrew.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.ridecrew.ridecrew.R;
import com.ridecrew.ridecrew.adapter.GalleryAdapter;

import java.util.ArrayList;

import Entity.Gallery;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class GalleryFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private GalleryAdapter adapter;
    private ImageButton btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        initLayout(view);
        setDefaultSettings();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fragment_gallery_add:
                break;
        }
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
    }
}
