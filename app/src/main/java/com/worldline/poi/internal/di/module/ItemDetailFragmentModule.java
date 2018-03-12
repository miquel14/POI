package com.worldline.template.internal.di.module;

import com.worldline.domain.executor.PostExecutionThread;
import com.worldline.domain.executor.ThreadExecutor;
import com.worldline.domain.interactor.GetDetailItemUseCase;
import com.worldline.domain.repository.HomeRepository;
import com.worldline.template.internal.di.PerFragment;

import dagger.Module;
import dagger.Provides;


@Module
public class ItemDetailFragmentModule {
    @Provides
    @PerFragment
    GetDetailItemUseCase provideDetailItemUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            HomeRepository homeRepository) {
        return new GetDetailItemUseCase(threadExecutor, postExecutionThread, homeRepository);
    }
}
