package com.worldline.template.presenter;


import com.worldline.domain.interactor.GetHomeItemsUseCase;
import com.worldline.domain.model.HomeItems;
import com.worldline.domain.subscriber.DefaultSubscriber;
import com.worldline.template.model.mapper.HomeItemModelMapper;
import com.worldline.template.view.IView;
import com.worldline.template.view.fragment.MainFragment;

import java.util.List;

import javax.inject.Inject;

public class MainFragmentPresenter extends Presenter<MainFragment> {

    private final GetHomeItemsUseCase getHomeItemsUseCase;

    private final HomeItemModelMapper homeItemModelMapper;

    @Inject
    public MainFragmentPresenter(GetHomeItemsUseCase getHomeItemsUseCase, HomeItemModelMapper homeItemsModelMapper) {
        this.getHomeItemsUseCase = getHomeItemsUseCase;
        this.homeItemModelMapper = homeItemsModelMapper;
    }

    @Override
    protected void initialize() {
        getHomeItemsPrograms();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    private void getHomeItemsPrograms() {
        getHomeItemsUseCase.execute(new DefaultSubscriber<List<HomeItems>>() {
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<HomeItems> homeItems) {
                super.onNext(homeItems);
                List<HomeItems> homeItemModelList = homeItemModelMapper.dataListToModelList(homeItems);
                if (homeItems == null || homeItems.isEmpty()) {
                    view.showEmptyCase();
                } else {
                    view.showItems(homeItemModelList);
                }
            }
        });
    }

    public interface View extends IView {
        void showItems(List<HomeItems> homeItems);
    }
}

