package com.example.accessappexercise;

public interface AppConstants {
    String APP_TAG = "AccessAppExercise";
    String API_URL = "https://api.github.com/";

    int API_STATUS_SUCCESS = 200;

    int USERS_PAGE_SIZE = 15;

    // User Api Url
//    String API_USERS = "users?page=page&per_page=per_page";
    String API_USERS = "users";
    String API_USER_DETAIL = "users/{login}";
}


