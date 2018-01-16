package network;

import java.util.ArrayList;
import java.util.List;

import Entity.ApiResult;
import Entity.Notice;
import Entity.Schedule;
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
/*rest/v1/notice
add, delete, patch, list 전부되고
POST 는 @Body Notice notice
Delete랑 Patch는 /rest/v1/notice/{pk} 가 url이고
파라미터는 @Path("pk") Long pk 으로 줘야되요
[1:18 PM]
스케줄리스트받는 API 중  @Query("data") 에서 data아니고 date인데 오타네요..ㅋㅋ
*/
public interface NoticeService {
    @GET("/rest/v1/notice")
    Call<ApiResult<ArrayList<Notice>>> getAllNoticeData();

    @POST("/rest/v1/notice")
    Call<ApiResult<ArrayList<Notice>>> addNotice(@Body Notice notice);

    @DELETE("/rest/v1/notice/{pk}")
    Call<ApiResult<Void>> deleteNotice(@Path("pk") Long pk);

    @PATCH("/rest/v1/notice/{pk}")
    Call<ApiResult<Void>> patchNotice(@Path("pk") Long pk);

}
