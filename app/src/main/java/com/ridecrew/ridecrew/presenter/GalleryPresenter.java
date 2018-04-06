package com.ridecrew.ridecrew.presenter;

import java.util.ArrayList;

import Entity.ApiResult;
import Entity.Gallery;
import Entity.GalleryLike;

/**
 * Created by KIM on 2018-01-16.
 */

public interface GalleryPresenter {

    void loadGalleries();
    void addGallery(Gallery gallery);
    void deleteGallery(Long id);
    void like(GalleryLike galleryLike);
    void disLike(Long galleryId, Long memberId);

    interface View {
        void getGalleries(ApiResult<ArrayList<Gallery>> result);
        void getGallery(ApiResult<Gallery> result);
        void showToast(String msg);
        void getGalleryLike(ApiResult<GalleryLike> result);
        void getGalleryDisLike(ApiResult<Void> result);
    }
}
