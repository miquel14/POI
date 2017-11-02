package com.worldline.domain.repository;

import com.worldline.domain.model.HomeItems;

import java.util.List;

import rx.Observable;


/**
 * Interface that specifies the methods that represents our data Repository.
 */
public interface HomeRepository {
    Observable<HomeItems> get(HomeItems homeitems);

    Observable<List<HomeItems>> getList(HomeItems homeItems);


}
