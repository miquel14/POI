package com.worldline.domain.interactor;

import com.worldline.domain.executor.PostExecutionThread;
import com.worldline.domain.executor.ThreadExecutor;
import com.worldline.domain.repository.HomeRepository;

import rx.Observable;
import rx.Subscriber;

public class GetHomeItemsUseCase extends Interactor{

    private final HomeRepository homeRepository;

    public GetHomeItemsUseCase(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, HomeRepository homeRepository) {
        super(threadExecutor, postExecutionThread);
        this.homeRepository = homeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return homeRepository.getList();
    }

    @Override
    public void execute(Subscriber useCaseSubscriber){
        super.execute(useCaseSubscriber);
    }
}
