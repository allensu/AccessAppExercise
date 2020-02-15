package com.example.accessappexercise.api;

import com.example.accessappexercise.AppConstants;

public class ApiServiceUtil {

    public static boolean isSuccess(int status) {
        if(status == AppConstants.API_STATUS_SUCCESS) {
            return true;
        } else {
            return false;
        }
    }
}
