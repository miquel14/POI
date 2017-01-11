package com.worldline.data.repository.datasource.cloud;

import com.tempos21.android.commons.utils.BuildConfig;
import com.tempos21.android.commons.utils.T21Log;
import com.worldline.data.net.APIConstants;
import com.worldline.data.util.NetworkUtils;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

public abstract class CommonCloudDataStore {

    @Inject
    Context context;

    private static final String HTTP_CACHE = "http-cache";

    private static final String CACHE_CONTROL = "Cache-Control";

    /**
     * Build the Retrofit object for the REST services.
     */
    protected Retrofit buildRetrofit() {
        return provideCommonRetrofitBuilder()
            .client(provideOkHttpCommonClient().build())
            .build();
    }

    protected Retrofit buildRetrofitWithCache(int time, TimeUnit timeUnit) {
        OkHttpClient client = provideOkHttpCommonClient()
            .addNetworkInterceptor(provideCacheInterceptor(time, timeUnit))
            .cache(provideCache())
            .build();

        return provideCommonRetrofitBuilder()
            .client(client)
            .build();
    }

    protected Retrofit buildRetrofitWithOfflineCache(int time, TimeUnit timeUnit) {
        OkHttpClient client = provideOkHttpCommonClient()
            .addInterceptor(provideOfflineCacheInterceptor(time, timeUnit))
            .cache(provideCache())
            .build();

        return provideCommonRetrofitBuilder()
            .client(client)
            .build();
    }

    protected Retrofit buildRetrofitWithBothCacheTypes(int onlineCacheTime, TimeUnit onlineTimeUnit, int offlineCacheTime,
        TimeUnit offlineTimeUnit) {
        OkHttpClient client = provideOkHttpCommonClient()
            .addNetworkInterceptor(provideCacheInterceptor(onlineCacheTime, onlineTimeUnit))
            .addInterceptor(provideOfflineCacheInterceptor(offlineCacheTime, offlineTimeUnit))
            .cache(provideCache())
            .build();

        return provideCommonRetrofitBuilder()
            .client(client)
            .build();
    }

    private OkHttpClient.Builder provideOkHttpCommonClient() {
        return new OkHttpClient.Builder()
            .addInterceptor(provideHttpLoggingInterceptor());
    }

    private Retrofit.Builder provideCommonRetrofitBuilder() {
        return new Retrofit.Builder()
            .baseUrl(APIConstants.HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    private Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(), HTTP_CACHE),
                10 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            T21Log.e(e.getMessage());
        }
        return cache;
    }

    private Interceptor provideCacheInterceptor(int time, TimeUnit timeUnit) {
        return (chain) ->
        {
            Response response = chain.proceed(chain.request());
            // re-write response header to force use of cache
            CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(time, timeUnit)
                .build();

            return response.newBuilder()
                .header(CACHE_CONTROL, cacheControl.toString())
                .build();
        };
    }

    private Interceptor provideOfflineCacheInterceptor(int time, TimeUnit timeUnit) {
        return (chain) ->
        {
            Request request = chain.request();
            if (!NetworkUtils.isNetworkAvailable(context)) {
                CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(time, timeUnit)
                    .build();

                request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
            }
            return chain.proceed(request);
        };
    }

    private HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(T21Log::d);
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HEADERS : NONE);
        return httpLoggingInterceptor;
    }
}