package com.example.accessappexercise.api.request;

import com.example.accessappexercise.api.ApiObserver;
import com.example.accessappexercise.api.ApiServiceResult;
import com.example.accessappexercise.api.data.user.UserData;
import com.example.accessappexercise.api.data.userDetail.UserDetailData;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserApiRequest extends BaseRequest {
    private static UserApiRequest request;

    public static UserApiRequest instance() {
        if(request == null) {
            request = new UserApiRequest();
        }

        return request;
    }

    public void users(int since, int perPage, ApiObserver<List<UserData>> callback)
    {
        _apiService.users(since, perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }


    public void userDetail(String login, ApiObserver<UserDetailData> callback)
    {
        _apiService.userDetail(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

}
