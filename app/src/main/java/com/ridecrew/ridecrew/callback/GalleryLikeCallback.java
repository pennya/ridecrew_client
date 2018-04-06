package com.ridecrew.ridecrew.callback;

import Entity.ApiResult;
import Entity.GalleryLike;

/**
 * Created by kim on 2018. 4. 6..
 */

public interface GalleryLikeCallback {
    void likeResult(ApiResult<GalleryLike> result, int status);
    void disLikeResult(ApiResult<Void> result, int status);
}
