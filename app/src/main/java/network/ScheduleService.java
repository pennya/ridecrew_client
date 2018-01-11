package network;

import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;
import Entity.ScheduleMember;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kim on 2017. 12. 25..
 */

public interface ScheduleService {
    @GET("/rest/v1/schedules_by_dates")
    Call<ApiResult<List<Schedule>>> getAllSchedules(@Query("date") String date);

    @POST("/rest/v1/schedules")
    Call<ApiResult<Schedule>> addSchedule(@Body Schedule schedule);

    @GET("rest/v1/schedule_members")
    Call<ApiResult<List<ScheduleMember>>> getAllScheduleMember();

    @POST("rest/v1/schedule_members")
    Call<ApiResult<ScheduleMember>> addScheduleMember(@Body ScheduleMember scheduleMember);

    @DELETE("rest/v1/schedule_members")
    Call<ApiResult<Void>> deleteScheduleMember(@Query("scheduleId") Long scheduleId, @Query("memberId") Long memberId);
}
