package network;

import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;
import Entity.User;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kim on 2017. 12. 8..
 */

public interface DummyService {

    @GET("/rest/v1/users/")
    Call<ApiResult<List<User>>> getAllUsers();

    @GET("/rest/v1/schedules")
    Call<ApiResult<List<Schedule>>> getAllSchedules();
}
