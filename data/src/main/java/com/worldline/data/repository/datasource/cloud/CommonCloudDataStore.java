package com.worldline.data.repository.datasource.cloud;

import com.squareup.okhttp.OkHttpClient;
import com.worldline.data.net.APIConstants;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public abstract class CommonCloudDataStore {

    /**
     * Build the Retrofit object for the REST services.
     */
    protected Retrofit buildRetrofit() {
        OkHttpClient httpClient = new OkHttpClient();
        return new Retrofit.Builder()
                .baseUrl(APIConstants.HOST)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

}