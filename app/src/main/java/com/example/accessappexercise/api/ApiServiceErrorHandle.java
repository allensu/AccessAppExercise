package com.example.accessappexercise.api;

import android.app.Activity;
import android.util.Log;

import com.example.accessappexercise.AppConstants;


public class ApiServiceErrorHandle {

    public static void exceptionHandle(Activity activity, Throwable e) {
        Log.e(AppConstants.APP_TAG, e.getMessage());
    }

    public static void errorHandle(Activity activity, int status) {
        Log.e(AppConstants.APP_TAG, String.valueOf(status));
    }
}
