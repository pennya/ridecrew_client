package network;

import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by KIM on 2017-12-27.
 */

public interface MyPageService {

    @GET("/rest/v1/schedules_by_member")
    Call<ApiResult<List<Schedule>>> getAllSchedules(@Query("memberId") Long memberId);
}
