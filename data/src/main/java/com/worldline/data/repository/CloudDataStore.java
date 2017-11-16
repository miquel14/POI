package com.worldline.data.repository;


import com.worldline.data.mapper.dto.ResponseDtoMapper;
import com.worldline.data.repository.datasource.cloud.ApiService;
import com.worldline.data.repository.datasource.cloud.CommonCloudDataStore;
import com.worldline.domain.model.HomeItem;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;

public class CloudDataStore extends CommonCloudDataStore {

    private ApiService apiService;

    private final ResponseDtoMapper baseResponseDtoMapper;

    @Inject
    public CloudDataStore(Context context, ResponseDtoMapper baseResponseDtoMapper) {
        super(context);
        this.baseResponseDtoMapper = baseResponseDtoMapper;
        Retrofit retrofit = buildRetrofit();
        apiService = retrofit.create(ApiService.class);
    }

    public Observable<List<HomeItem>> getHomeItems(){
        return apiService.getHomeItemsList().map(baseResponseDtoMapper::dataListToModelList);
    }

    public Observable<HomeItem> getDetailItem(String id) {
        return apiService.getDetailItem(id).map(baseResponseDtoMapper::dataToModel);
    }
}
