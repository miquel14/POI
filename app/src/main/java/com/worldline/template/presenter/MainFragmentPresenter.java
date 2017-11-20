package com.worldline.template.presenter;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import com.worldline.data.GeoConstant;
import com.worldline.domain.interactor.GetHomeItemsUseCase;
import com.worldline.domain.model.HomeItem;
import com.worldline.domain.subscriber.DefaultSubscriber;
import com.worldline.template.internal.di.PerFragment;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.model.mapper.HomeItemModelMapper;
import com.worldline.template.view.IView;
import com.worldline.template.view.fragment.MainFragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

@PerFragment
public class MainFragmentPresenter extends Presenter<MainFragment> {

    private final GetHomeItemsUseCase getHomeItemsUseCase;

    private final HomeItemModelMapper homeItemModelMapper;

    private List<HomeItemModel> homeItemModelList;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private Location lastKnownLocation;

    @Inject
    MainFragmentPresenter(GetHomeItemsUseCase getHomeItemsUseCase, HomeItemModelMapper homeItemsModelMapper) {
        this.getHomeItemsUseCase = getHomeItemsUseCase;
        this.homeItemModelMapper = homeItemsModelMapper;
        // fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getView().getContext());
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

    public void gotoDetail(int id, String title) {
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
                homeItemModelList = homeItemModelMapper.dataListToModelList(homeItems);
                if (homeItemModelList == null || homeItemModelList.isEmpty()) {
                    view.showEmptyCase();
                } else {
                    //getLastLocation();
                    calculateAllDistances(homeItemModelList);
                    view.showItems(homeItemModelList);
                }
            }
        });
    }

    private void calculateAllDistances(List<HomeItemModel> homeItemModelList) {
        for (HomeItemModel item : homeItemModelList) {
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
        lastKnownLocation = new Location("");
        if (lastKnownLocation.getLatitude() == 0 || lastKnownLocation.getLongitude() == 0) {
            loc2.setLatitude(GeoConstant.latitude);
            loc2.setLongitude(GeoConstant.longitude);
        } else {
            loc2.set(lastKnownLocation);
        }

        float distanceMeters = loc2.distanceTo(loc1);
        float distanceKm = distanceMeters / 1000;
        return String.format("%.2f", distanceKm);
    }

    final public Comparator<HomeItemModel> comp = new Comparator<HomeItemModel>() {
        @Override
        public int compare(HomeItemModel o1, HomeItemModel o2) {
            if (Double.valueOf(o1.getDistanceInKm()) > Double.valueOf(o2.getDistanceInKm())) {
                return 1;
            } else if (Double.valueOf(o1.getDistanceInKm()) < Double.valueOf(o2.getDistanceInKm())) {
                return -1;
            } else {
                return 0;
            }
        }
    };


    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //  ActivityCompat.requestPermissions(view.getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
            // .ACCESS_COARSE_LOCATION)
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener((Activity) view.getContext(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            lastKnownLocation = location;
                        }
                    }
                });
    }

    public void searchItems(String newText) {
        filter(homeItemModelList, newText);
    }

    private void filter(List<HomeItemModel> list, String query) {
        List<HomeItemModel> filteredList = new ArrayList<HomeItemModel>();
        for (HomeItemModel model : list) {
            if (model.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(model);
            }
        }
        view.showItems(filteredList);
    }

    public interface View extends IView {
        void showItems(List<HomeItemModel> homeItems);
    }
}

