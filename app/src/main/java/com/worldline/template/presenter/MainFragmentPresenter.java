package com.worldline.template.presenter;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.worldline.data.GeoConstant;
import com.worldline.domain.interactor.GetHomeItemsUseCase;
import com.worldline.domain.model.HomeItem;
import com.worldline.domain.subscriber.DefaultSubscriber;
import com.worldline.template.internal.di.PerFragment;
import com.worldline.template.location.LocationHelper;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.model.mapper.HomeItemModelMapper;
import com.worldline.template.view.IView;
import com.worldline.template.view.fragment.MainFragment;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

@PerFragment
public class MainFragmentPresenter extends Presenter<MainFragment> implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private final GetHomeItemsUseCase getHomeItemsUseCase;

    private final HomeItemModelMapper homeItemModelMapper;

    private List<HomeItemModel> homeItemModelList;

    private Location lastKnownLocation;

    private LocationHelper locationHelper;

    private LocationManager locationManager;

    @Inject
    MainFragmentPresenter(GetHomeItemsUseCase getHomeItemsUseCase, HomeItemModelMapper homeItemsModelMapper) {
        this.getHomeItemsUseCase = getHomeItemsUseCase;
        this.homeItemModelMapper = homeItemsModelMapper;
    }

    @Override
    protected void initialize() {
        locationHelper.checkPermission();
        locationManager = (LocationManager) getView().getContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListenerGPS);
        getHomeItems();
    }

    @Override
    public void resume() {

    }

    @Override
    public void start() {
        locationHelper = new LocationHelper(getView().getContext());
        lastKnownLocation = new Location("");
        if (locationHelper.checkPlayServices()) {
            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient();
        }
        if (locationHelper.getGoogleApiCLient() != null) {
            locationHelper.connectApiClient();
        }
        initialize();
    }

    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            refreshLocationsList();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void refreshLocationsList() {
        if (homeItemModelList == null){
            getHomeItems();
        }
        getLastLocation();
        calculateAllDistances(homeItemModelList);
        view.showItems(homeItemModelList);
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
                    refreshLocationsList();
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
        if (lastKnownLocation.getLatitude() == 0 || lastKnownLocation.getLongitude() == 0) {
            loc2.setLatitude(GeoConstant.latitude);
            loc2.setLongitude(GeoConstant.longitude);
        } else {
            loc2.set(lastKnownLocation);
        }

        float distanceMeters = loc2.distanceTo(loc1);
        float distanceKm = distanceMeters / 1000;
        return String.format(Locale.ENGLISH, "%.2f", distanceKm);
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
        Location mLocation = locationHelper.getLocation();
        if (mLocation != null) {
            lastKnownLocation.setLongitude(mLocation.getLongitude());
            lastKnownLocation.setLatitude(mLocation.getLatitude());
        } else {
            showToast("No es pot obtenir l'ubicació.");
        }
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

    private void showToast(String message) {
        Toast.makeText(getView().getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        lastKnownLocation = locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        locationHelper.connectApiClient();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }


    public interface View extends IView {

        void showItems(List<HomeItemModel> homeItems);
    }
}

