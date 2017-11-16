package com.worldline.template.presenter;


import com.worldline.data.GeoConstant;
import com.worldline.domain.interactor.GetHomeItemsUseCase;
import com.worldline.domain.model.HomeItem;
import com.worldline.domain.subscriber.DefaultSubscriber;
import com.worldline.template.internal.di.PerFragment;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.model.mapper.HomeItemModelMapper;
import com.worldline.template.view.IView;
import com.worldline.template.view.fragment.MainFragment;

import android.location.Location;

import java.util.Comparator;
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
        getHomeItems();
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

    public void gotoDetail(int id, String title){
        navigator.openDetailActivity(getView().getActivity(), id, title);
    }

    public void getHomeItems() {
        getHomeItemsUseCase.execute(new DefaultSubscriber<List<HomeItem>>() {
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
            public void onNext(List<HomeItem> homeItems) {
                super.onNext(homeItems);
                List<HomeItemModel> homeItemModelList = homeItemModelMapper.dataListToModelList(homeItems);
                if (homeItemModelList == null || homeItemModelList.isEmpty()) {
                    view.showEmptyCase();
                } else {
                    calculateAllDistances(homeItemModelList);
                    view.showItems(homeItemModelList);
                }
            }
        });
    }

    private void calculateAllDistances(List<HomeItemModel> homeItemModelList){
        for (HomeItemModel item : homeItemModelList){
            item.setDistanceInKm(distance(item.getGeoCoordinates()));
        }
    }

    private String distance(String coordinates) {
        String[] coordinatesList = coordinates.split(",");
        String latitude = coordinatesList[0];
        String longitude = coordinatesList[1];

        Location loc1 = new Location("");
        loc1.setLongitude(Double.parseDouble(longitude));
        loc1.setLatitude(Double.parseDouble(latitude));

        Location loc2 = new Location("");
        loc2.setLatitude(GeoConstant.latitude);
        loc2.setLongitude(GeoConstant.longitude);

        float distanceMeters = loc2.distanceTo(loc1);
        float distanceKm = distanceMeters / 1000;
        return String.format("%.2f", distanceKm);
    }

    final public Comparator<HomeItemModel> comp = new Comparator<HomeItemModel>() {
        @Override
        public int compare(HomeItemModel o1, HomeItemModel o2) {
            if (Double.valueOf(o1.getDistanceInKm()) > Double.valueOf(o2.getDistanceInKm())) {
                return 1;
            } else if (Double.valueOf(o1.getDistanceInKm()) < Double.valueOf(o2.getDistanceInKm())){
                return -1;
            } else {
                return 0;
            }
        }
    };


    public interface View extends IView {
        void showItems(List<HomeItemModel> homeItems);
    }
}

