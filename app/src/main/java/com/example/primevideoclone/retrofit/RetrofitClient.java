package com.example.primevideoclone.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    //URL Update
    private static final String BASE_URL = "https://awsstreaming.s3.us-east-2.amazonaws.com/";

    public static ApiInterface getRetrofitClient(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(BASE_URL);

        return builder.build().create(ApiInterface.class);

    }
}
