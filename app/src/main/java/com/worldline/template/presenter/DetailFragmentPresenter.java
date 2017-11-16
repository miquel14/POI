package com.worldline.template.presenter;

import com.worldline.domain.interactor.GetDetailItemUseCase;
import com.worldline.domain.model.HomeItem;
import com.worldline.domain.subscriber.DefaultSubscriber;
import com.worldline.template.internal.di.PerFragment;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.model.mapper.HomeItemModelMapper;
import com.worldline.template.view.IView;
import com.worldline.template.view.fragment.DetailFragment;

import javax.inject.Inject;


@PerFragment
public class DetailFragmentPresenter extends Presenter<DetailFragment>{

    private final GetDetailItemUseCase getDetailItemUseCase;
    private final HomeItemModelMapper homeItemModelMapper;

    @Inject
    public DetailFragmentPresenter(GetDetailItemUseCase getDetailItemUseCase, HomeItemModelMapper homeItemModelMapper) {
        this.getDetailItemUseCase = getDetailItemUseCase;
        this.homeItemModelMapper = homeItemModelMapper;
    }


    @Override
    protected void initialize() {

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

    public void getDetail() {
        getItemDetail();
    }

    private void getItemDetail() {
        getDetailItemUseCase.execute(view.getItemId(),new DefaultSubscriber<HomeItem>(){
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
            public void onNext(HomeItem homeItem) {
                super.onNext(homeItem);
                HomeItemModel item = homeItemModelMapper.dataToModel(homeItem);
                if (item == null){
                    view.showEmptyCase();
                } else{
                    view.showItem(item);
                }
            }
        });

    }

    public interface View extends IView{
        void showItem(HomeItemModel item);

        String getItemId();
    }
}
