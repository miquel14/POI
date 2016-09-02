package com.worldline.template.clean.data.repository.datasource;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.tempos21.android.commons.utils.T21Log;
import com.worldline.template.clean.data.net.ApiConstants;

import android.content.Context;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * TODO: Add your comments
 */
public abstract class CommonCloudDataStore {

    private static final String TAG = "CloudDataStore";

    protected Context context;

    public CommonCloudDataStore(Context context) {
        this.context = context;
    }

    protected Retrofit buildRetrofit() {
        OkHttpClient httpClient = createUnsafeOkHttpClient();

        // Loggin
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // add interceptors
        httpClient.interceptors().add(logging);
        httpClient.networkInterceptors().add(chain -> {
            Request.Builder builder = chain.request().newBuilder();
            Map<String, String> headers = getHeaders();
            if (headers != null) {
                Set<String> keys = headers.keySet();
                for (String key : keys) {
                    String value = headers.get(key);
                    if (value != null) {
                        builder.addHeader(key, value);
                    }
                }
            }

            Request request = builder.build();
            return chain.proceed(request);
        });

        httpClient.setConnectTimeout(45, TimeUnit.SECONDS);
        httpClient.setReadTimeout(45, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(45, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .baseUrl(ApiConstants.ENDPOINT)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    protected abstract Map<String, String> getHeaders();

    private OkHttpClient createUnsafeOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            // Create an ssl socket factory with our all-trusting manager
            TLSSocketFactory sslSocketFactory = new TLSSocketFactory();

            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier((hostname, session) -> true);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            T21Log.e(TAG, e.getMessage());
        }
        return okHttpClient;
    }
}