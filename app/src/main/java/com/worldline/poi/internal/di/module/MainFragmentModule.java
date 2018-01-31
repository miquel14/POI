package com.worldline.template.internal.di.module;

import com.worldline.domain.executor.PostExecutionThread;
import com.worldline.domain.executor.ThreadExecutor;
import com.worldline.domain.interactor.GetHomeItemsUseCase;
import com.worldline.domain.repository.HomeRepository;
import com.worldline.template.internal.di.PerFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class MainFragmentModule {

    @Provides
    @PerFragment
    GetHomeItemsUseCase provideGetHomeItemsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
           HomeRepository homeRepository) {
        return new GetHomeItemsUseCase(threadExecutor, postExecutionThread, homeRepository);
    }
}
