package com.worldline.domain.interactor;

import com.worldline.domain.executor.PostExecutionThread;
import com.worldline.domain.executor.ThreadExecutor;
import com.worldline.domain.repository.HomeRepository;

import rx.Observable;
import rx.Subscriber;


public class GetDetailItemUseCase extends Interactor {

    private final HomeRepository homeRepository;
    private String id;

    public GetDetailItemUseCase(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, HomeRepository homeRepository) {
        super(threadExecutor, postExecutionThread);
        this.homeRepository = homeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return homeRepository.get(id);
    }


    public void execute(String id, Subscriber useCaseSubscriber){
        this.id = id;
        super.execute(useCaseSubscriber);
    }
}

