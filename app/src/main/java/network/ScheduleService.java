package network;

import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kim on 2017. 12. 25..
 */

public interface ScheduleService {
    @GET("/rest/v1/schedules_by_dates")
    Call<ApiResult<List<Schedule>>> getAllSchedules(@Query("date") String date);
}
