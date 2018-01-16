package network;

import java.util.ArrayList;

import Entity.ApiResult;
import Entity.Gallery;
import Entity.GalleryPicture;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by KIM on 2018-01-16.
 */

public interface GalleryService {
    @GET("/rest/v1/gallery")
    Call<ApiResult<ArrayList<Gallery>>> getAll();

    @POST("/rest/v1/gallery")
    Call<ApiResult<Gallery>> addGallery(@Body Gallery gallery);

    @DELETE("/rest/v1/gallery/{id}")
    Call<ApiResult<Void>> deleteGallery(@Path("id") Long id);

    @GET("/rest/v1/gallery_picture")
    Call<ApiResult<ArrayList<GalleryPicture>>> findByGalleryId(@Field("galleryId") Long galleryId);

    @POST("/rest/v1/gallery_picture")
    Call<ApiResult<GalleryPicture>> addPicture(@Body GalleryPicture picture);
}
