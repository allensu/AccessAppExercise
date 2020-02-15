package com.example.accessappexercise.api;


import com.example.accessappexercise.AppConstants;
import com.example.accessappexercise.api.data.user.UserData;
import com.example.accessappexercise.api.data.userDetail.UserDetailData;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface ApiService {

    /* User Module */
    @GET(AppConstants.API_USERS)
    Observable<List<UserData>> users(@Query("since") int since, @Query("per_page") int perPage);

    @GET(AppConstants.API_USER_DETAIL)
    Observable<UserDetailData> userDetail(@Path("login") String login);





}