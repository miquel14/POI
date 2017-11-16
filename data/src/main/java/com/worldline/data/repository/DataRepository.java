package com.worldline.data.repository;


import com.worldline.domain.model.HomeItem;
import com.worldline.domain.repository.HomeRepository;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DataRepository implements HomeRepository {

    private final CloudDataStore cloudDataStore;

    @Inject
    public DataRepository(CloudDataStore cloudDataStore){
        this.cloudDataStore = cloudDataStore;
    }

    @Override
    public Observable<HomeItem> get(String id) {
        return cloudDataStore.getDetailItem(id);
    }

    @Override
    public Observable<List<HomeItem>> getList() {
        return cloudDataStore.getHomeItems();
    }
}
