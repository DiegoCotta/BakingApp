package com.example.baking.service;

import com.example.baking.BuildConfig;
import com.example.baking.model.Recipe;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by diegocotta on 19/10/2018.
 */

//Reference https://zeroturnaround.com/rebellabs/getting-started-with-retrofit-2/
public interface Service {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();

    static OkHttpClient getClient() {
        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(getLoggingCapableHttpClient())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    static HttpLoggingInterceptor getLoggingCapableHttpClient() {
        HttpLoggingInterceptor mLogging = new HttpLoggingInterceptor();
        mLogging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return mLogging;
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
