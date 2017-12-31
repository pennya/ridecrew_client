package network;

import Entity.ApiResult;
import Entity.Member;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by KJH on 2017-12-30.
 */

public interface MemberService {
    @POST("/rest/v1/members")
    Call<ApiResult<Member>> signUpMember(@Body Member member);

    @FormUrlEncoded
    @POST("/rest/v1/member_login")
    Call<ApiResult<Member>> loginMember(@Field("email") String email, @Field("pwd") String pwd);
}