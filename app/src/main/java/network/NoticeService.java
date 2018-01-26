package network;

import java.util.ArrayList;
import Entity.ApiResult;
import Entity.Notice;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by JooHyeong on 2018. 1. 16..
 */

public interface NoticeService {
    @GET("/rest/v1/notice")
    Call<ApiResult<ArrayList<Notice>>> getAllNoticeData();

    @POST("/rest/v1/notice")
    Call<ApiResult<ArrayList<Notice>>> addNotice(@Body Notice notice);

    @DELETE("/rest/v1/notice/{pk}")
    Call<ApiResult<Void>> deleteNotice(@Path("pk") Long pk);

    @PATCH("/rest/v1/notice/{pk}")
    Call<ApiResult<Void>> patchNotice(@Path("pk") Long pk, @Body Notice notice);
}
