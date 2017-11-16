package com.worldline.data.repository.datasource.cloud;

import com.worldline.data.entity.mapper.HomeItemsDto;
import com.worldline.data.entity.mapper.HomeItemsDtoList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiService {

    @GET("points/{id}")
    Observable<HomeItemsDto> getDetailItem(@Path("id") String id);

    @GET("points")
    Observable<HomeItemsDtoList> getHomeItemsList();
}

