package com.worldline.domain.interactor;


import com.worldline.domain.executor.PostExecutionThread;
import com.worldline.domain.executor.ThreadExecutor;
import com.worldline.domain.model.HomeItems;
import com.worldline.domain.repository.HomeRepository;

import rx.Observable;
import rx.Subscriber;

public class GetHomeItemsUseCase extends Interactor{

    private final HomeRepository homeRepository;
    private HomeItems homeItems;

    protected GetHomeItemsUseCase(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, ApiCatalogueRepository apiCatalogueRepository, HomeRepository homeRepository) {
        super(threadExecutor, postExecutionThread);
        //check repos
        //checkRepository(homeRepository);
        //this.apiCatalogueRepository = apiCatalogueRepository;
        this.homeRepository = homeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return homeRepository.getList(homeItems);
    }

    public void execute(HomeItems homeItems, Subscriber useCaseSubscriber){
        this.homeItems = homeItems;
        super.execute(useCaseSubscriber);
    }
}
