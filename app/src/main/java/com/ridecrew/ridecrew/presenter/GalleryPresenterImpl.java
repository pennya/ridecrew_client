package com.ridecrew.ridecrew.presenter;

import com.ridecrew.ridecrew.callback.ModelCallback;
import com.ridecrew.ridecrew.model.GalleryModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Gallery;

/**
 * Created by KIM on 2018-01-16.
 */

public class GalleryPresenterImpl implements GalleryPresenter, ModelCallback<Gallery, ArrayList<Gallery>> {

    private GalleryPresenter.View view;
    private GalleryModel model;

    public GalleryPresenterImpl(GalleryPresenter.View view) {
        this.view = view;
        model = new GalleryModel(this);
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
    public void getSingleNetworkResponse(ApiResult<Gallery> result, int status) {

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
    }

    class AscendingObj implements Comparator<Gallery> {

        @Override
        public int compare(Gallery o1, Gallery o2) {
            // 마지막 수정시간 오름차순
            return o2.getLastModifiedDateTime().compareTo(o1.getLastModifiedDateTime());
        }
    }
}
