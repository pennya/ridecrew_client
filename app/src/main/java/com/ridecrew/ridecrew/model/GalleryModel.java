package com.ridecrew.ridecrew.model;

import android.util.Log;

import com.ridecrew.ridecrew.callback.GalleryLikeCallback;
import com.ridecrew.ridecrew.callback.ModelCallback;

import java.util.ArrayList;

import Entity.ApiErrorCode;
import Entity.ApiResult;
import Entity.Gallery;
import Entity.GalleryLike;
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
    private GalleryLikeCallback likeCallback;

    public GalleryModel(ModelCallback callback, GalleryLikeCallback likeCallback) {
        this.callback = callback;
        this.likeCallback = likeCallback;
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
                if(response.isSuccessful() && response.code() == 200) {
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

    public void like(GalleryLike gl) {
        GalleryService service = NetworkManager.getInstance().getRetrofit(GalleryService.class);
        Call<ApiResult<GalleryLike>> like = service.like(gl);
        like.enqueue(new Callback<ApiResult<GalleryLike>>() {
            @Override
            public void onResponse(Call<ApiResult<GalleryLike>> call, Response<ApiResult<GalleryLike>> response) {
                if(response.isSuccessful() && response.code() == 200) {
                    likeCallback.likeResult(response.body(), 200);
                } else {
                    if( response.body() == null) {
                       Log.e("PACKRIDING", response.message());
                    } else {
                        callback.getErrorNetworkResponse(response.body().getError().getMessage(), response.body().getError().getCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResult<GalleryLike>> call, Throwable t) {
                callback.getErrorNetworkResponse(t.getMessage(), ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }

    public void disLike(Long galleryId, Long memberId) {
        GalleryService service = NetworkManager.getInstance().getRetrofit(GalleryService.class);
        Call<ApiResult<Void>> disLike = service.disLike(galleryId, memberId);
        disLike.enqueue(new Callback<ApiResult<Void>>() {
            @Override
            public void onResponse(Call<ApiResult<Void>> call, Response<ApiResult<Void>> response) {
                if(response.isSuccessful() && response.code() == 200) {
                    likeCallback.disLikeResult(response.body(), 202);
                } else {
                    callback.getErrorNetworkResponse(response.body().getError().getMessage(), response.body().getError().getCode());
                }
            }

            @Override
            public void onFailure(Call<ApiResult<Void>> call, Throwable t) {
                callback.getErrorNetworkResponse(t.getMessage(), ApiErrorCode.UNKNOWN_SERVER_ERROR);
            }
        });
    }
}
