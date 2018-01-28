package com.ridecrew.ridecrew.model;

import com.ridecrew.ridecrew.callback.ModelCallback;

import java.util.ArrayList;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Gallery;
import network.GalleryService;
import network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KIM on 2018-01-16.
 */

public class GalleryModel {
    private ModelCallback callback;

    public GalleryModel(ModelCallback callback) {
        this.callback = callback;
    }

    public void loadGalleries() {
        GalleryService service = NetworkManager.getInstance().getRetrofit(GalleryService.class);
        Call<ApiResult<ArrayList<Gallery>>> getAllCall = service.getAllGalleries();
        getAllCall.enqueue(new Callback<ApiResult<ArrayList<Gallery>>>() {
            @Override
            public void onResponse(Call<ApiResult<ArrayList<Gallery>>> call, Response<ApiResult<ArrayList<Gallery>>> response) {
                if(response.isSuccessful() && response.code() == 200) {
                    if(response.body().isSuccess()) {
                        callback.getMultiNetworkResponse(response.body(), 200);
                    } else {
                        callback.getErrorNetworkResponse(response.body().getError().getMessage(), response.body().getError().getCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResult<ArrayList<Gallery>>> call, Throwable t) {
                callback.getErrorNetworkResponse(t.getMessage(), ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }

    public void addGallery(Gallery gallery) {
        GalleryService service = NetworkManager.getInstance().getRetrofit(GalleryService.class);
        Call<ApiResult<Gallery>> addCall = service.addGallery(gallery);
        addCall.enqueue(new Callback<ApiResult<Gallery>>() {
            @Override
            public void onResponse(Call<ApiResult<Gallery>> call, Response<ApiResult<Gallery>> response) {
                if(response.body().isSuccess()) {
                    callback.getSingleNetworkResponse(response.body(), 200);
                } else {
                    callback.getErrorNetworkResponse(response.body().getError().getMessage(), response.body().getError().getCode());
                }
            }

            @Override
            public void onFailure(Call<ApiResult<Gallery>> call, Throwable t) {
                callback.getErrorNetworkResponse(t.getMessage(), ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }

    public void deleteGallery(Long id) {

    }
}
