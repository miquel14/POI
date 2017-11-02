package com.worldline.data.repository;


import com.worldline.data.repository.database.HomeItemsDataStoreFactory;
import com.worldline.domain.model.HomeItems;
import com.worldline.domain.repository.HomeRepository;
import java.util.List;

import rx.Observable;

public class DataRepository implements HomeRepository{

    private final HomeItemsDataStoreFactory dataStoreFactory;

    public DataRepository(HomeItemsDataStoreFactory dataStoreFactory) {
        if (dataStoreFactory == null){
            throw new NullPointerException();
        }
        this.dataStoreFactory = dataStoreFactory;
    }

    @Override
    public Observable<HomeItems> get(HomeItems homeitems) {
        return null;
    }

    @Override
    public Observable<List<HomeItems>> getList(HomeItems homeItems) {
        return null;
    }
}
