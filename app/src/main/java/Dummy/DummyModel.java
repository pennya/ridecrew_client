package Dummy;

import android.util.Log;

import java.util.List;

import Entity.ApiResult;
import Entity.Schedule;
import network.DummyService;
import network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kim on 2017. 12. 8..
 */

public class DummyModel {

    public void requestScheduleList() {
        DummyService service = NetworkManager.getIntance().getRetrofit(DummyService.class);
        Call<ApiResult<List<Schedule>>> scheduleListCall = service.getAllSchedules();
        scheduleListCall.enqueue(new Callback<ApiResult<List<Schedule>>>() {
            @Override
            public void onResponse(Call<ApiResult<List<Schedule>>> call, Response<ApiResult<List<Schedule>>> response) {
                if( response.isSuccessful() && response.code() == 200) {
                    ApiResult<List<Schedule>> result = response.body();

                    List<Schedule> schedules = result.getData();
                    for(Schedule schedule : schedules) {
                        Log.d(Define.Define.TAG, schedule.getId() + " " + schedule.getTitle() + " " + schedule.getTitle());
                    }

                }
            }

            @Override
            public void onFailure(Call<ApiResult<List<Schedule>>> call, Throwable t) {
                Log.d(Define.Define.TAG, "requestScheduleList fail");
                Log.d(Define.Define.TAG, t.getMessage());
            }
        });
    }

}
