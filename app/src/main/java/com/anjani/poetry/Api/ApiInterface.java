package com.anjani.poetry.Api;

import com.anjani.poetry.Response.DeleteResponse;
import com.anjani.poetry.Response.GetPoetryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    // Read api(data response from data base )
    @GET("readapi.php")
    Call<GetPoetryResponse> getpoetry();

    @FormUrlEncoded                            // delete api(data delete from data base )
    @POST("deleteapi.php")
    Call<DeleteResponse> deletepoetry(@Field("id") String id);      //POJO CLASS WITH ANY METHOD NAME BASSED ON CHOICE/WORK

    @FormUrlEncoded
    @POST("addapi.php")
    Call<DeleteResponse> add_poetry(@Field("poetry") String poetry_data, @Field("poet_name") String poet_nane);

    @FormUrlEncoded
    @POST("updateapi.php")
    Call<DeleteResponse> update_poetry(@Field("poetry_data") String poetry_data, @Field("id") String id);

}
