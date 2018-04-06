package com.ridecrew.ridecrew.presenter;

import android.util.Log;

import com.ridecrew.ridecrew.callback.GalleryLikeCallback;
import com.ridecrew.ridecrew.callback.ModelCallback;
import com.ridecrew.ridecrew.model.GalleryModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Gallery;
import Entity.GalleryLike;

/**
 * Created by KIM on 2018-01-16.
 */

public class GalleryPresenterImpl implements GalleryPresenter, ModelCallback<Gallery, ArrayList<Gallery>>, GalleryLikeCallback {

    private GalleryPresenter.View view;
    private GalleryModel model;

    public GalleryPresenterImpl(GalleryPresenter.View view) {
        this.view = view;
        model = new GalleryModel(this, this);
    }

    @Override
    public void loadGalleries() {
        model.loadGalleries();
    }

    @Override
    public void addGallery(Gallery gallery) {
        model.addGallery(gallery);
    }

    @Override
    public void deleteGallery(Long id) {
        model.deleteGallery(id);
    }

    @Override
    public void like(GalleryLike galleryLike) {
        model.like(galleryLike);
    }

    @Override
    public void disLike(Long galleryId, Long memberId) {
        model.disLike(galleryId, memberId);
    }

    @Override
    public void getSingleNetworkResponse(ApiResult<Gallery> result, int status) {
        view.getGallery(result);
    }

    @Override
    public void getMultiNetworkResponse(ApiResult<ArrayList<Gallery>> result, int status) {
        Collections.sort(result.getData(), new AscendingObj());
        view.getGalleries(result);
    }

    @Override
    public void getVoidNetworkResponse(ApiResult<Void> result, int status) {

    }

    @Override
    public void getErrorNetworkResponse(String msg, ApiErrorCode code) {
        view.showToast(msg + " [" + code + "]");
        Log.e("PACKRIDING", msg + " [" + code + "]");
    }

    @Override
    public void likeResult(ApiResult<GalleryLike> result, int status) {
        view.getGalleryLike(result);
    }

    @Override
    public void disLikeResult(ApiResult<Void> result, int status) {
        view.getGalleryDisLike(result);
    }

    class AscendingObj implements Comparator<Gallery> {

        @Override
        public int compare(Gallery o1, Gallery o2) {
            // 마지막 수정시간 오름차순
            return o2.getLastModifiedDateTime().compareTo(o1.getLastModifiedDateTime());
        }
    }
}
