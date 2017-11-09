package com.worldline.template.presenter;


import com.worldline.domain.interactor.GetHomeItemsUseCase;
import com.worldline.domain.model.HomeItems;
import com.worldline.domain.subscriber.DefaultSubscriber;
import com.worldline.template.internal.di.PerFragment;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.model.mapper.HomeItemModelMapper;
import com.worldline.template.view.IView;
import com.worldline.template.view.activity.MainActivity;
import com.worldline.template.view.fragment.MainFragment;

import java.util.List;

import javax.inject.Inject;

@PerFragment
public class MainFragmentPresenter extends Presenter<MainFragment> {

    private final GetHomeItemsUseCase getHomeItemsUseCase;

    private final HomeItemModelMapper homeItemModelMapper;

    @Inject
    MainFragmentPresenter(GetHomeItemsUseCase getHomeItemsUseCase, HomeItemModelMapper homeItemsModelMapper) {
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

    public void gotoDetail(int Id){
        navigator.openDetailActivity(getView().getActivity(),Id);
    }

    public void getHomeItemsPrograms() {
        getHomeItemsUseCase.execute(new DefaultSubscriber<List<HomeItems>>() {
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showEmptyCase();
            }

            @Override
            public void onNext(List<HomeItems> homeItems) {
                super.onNext(homeItems);
                List<HomeItemModel> homeItemModelList = homeItemModelMapper.dataListToModelList(homeItems);
                if (homeItemModelList == null || homeItemModelList.isEmpty()) {
                    view.showEmptyCase();
                } else {
                    view.showItems(homeItemModelList);
                }
            }
        });
    }

    public interface View extends IView {
        void showItems(List<HomeItemModel> homeItems);
    }
}

