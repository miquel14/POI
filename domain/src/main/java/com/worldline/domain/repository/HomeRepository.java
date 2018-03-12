package com.worldline.domain.repository;

import com.worldline.domain.model.HomeItem;

import java.util.List;

import rx.Observable;


/**
 * Interface that specifies the methods that represents our data Repository.
 */
public interface HomeRepository {
    Observable<HomeItem> get(String id);

    Observable<List<HomeItem>> getList();


}
