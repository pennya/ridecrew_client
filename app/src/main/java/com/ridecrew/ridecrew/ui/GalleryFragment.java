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


        ArrayList<GalleryEntity> items = new ArrayList<>();
        items.add( (new GalleryEntity()).setUserId("홍길동").setLikeCount(6).setImageUrl("http://cfile1.uf.tistory.com/image/20602F0F4CEA8EE73B9543"));
        items.add( (new GalleryEntity()).setUserId("홍길순").setLikeCount(9).setImageUrl("http://www.cj-ilbo.com/news/photo/201603/914429_18223_0003.jpg"));
        items.add( (new GalleryEntity()).setUserId("김지훈").setLikeCount(15).setImageUrl("http://www.bicyclenews.co.kr/news/photo/201510/2246_8710_2846.jpg"));
        items.add( (new GalleryEntity()).setUserId("이주형").setLikeCount(40).setImageUrl("http://bike-korea.com/files/attach/images/1910/874/527/4e783a1efcf8da7241a5c98cb9e927b1.jpg"));

        adapter.items = items;
        adapter.notifyDataSetChanged();
    }

    public class GalleryEntity {
        private String iconUrl;
        private String userId;
        private int likeCount;
        private String imageUrl;


        public String getIconUrl() {
            return iconUrl;
        }

        public GalleryEntity setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
            return this;
        }

        public String getUserId() {
            return userId;
        }

        public GalleryEntity setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public GalleryEntity setLikeCount(int likeCount) {
            this.likeCount = likeCount;
            return this;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public GalleryEntity setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }
    }
}
