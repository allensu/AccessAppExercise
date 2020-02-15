package com.example.accessappexercise.api;

import android.app.Activity;
import android.content.DialogInterface;

import com.example.accessappexercise.AppConstants;
import com.example.accessappexercise.R;
import com.example.accessappexercise.utility.DialogUtils;
import com.google.gson.Gson;


import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

public abstract class ApiObserver<T> implements Observer<T> {
    protected Activity _activity;


    public ApiObserver(Activity activity) {
        _activity = activity;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {
        onNext(t);
    }
}