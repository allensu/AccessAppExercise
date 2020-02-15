package com.example.accessappexercise.api.request;

import com.example.accessappexercise.AppConstants;
import com.example.accessappexercise.api.ApiService;
import com.example.accessappexercise.api.ApiServiceResult;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRequest {

    public interface ExecuteCallbackInterface<T> {
        void onCompleted();
        void onError(Throwable e);
        void onNext(ApiServiceResult<T> result);
    }

    protected Retrofit _retrofit;
    protected ApiService _apiService;

    public BaseRequest()
    {
        OkHttpClient mOkHttpClient = getNewHttpClient();

        _retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) // 使用 Gson 解析
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        _apiService = _retrofit.create(ApiService.class);

    }

    private OkHttpClient getNewHttpClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        //强行返回true 即验证成功
//                        return true;
//                    }
//                });

        return client.build();
    }



}
