package com.worldline.template.presenter;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.worldline.domain.interactor.GetHomeItemsUseCase;
import com.worldline.domain.model.HomeItem;
import com.worldline.domain.subscriber.DefaultSubscriber;
import com.worldline.template.R;
import com.worldline.template.internal.di.PerFragment;
import com.worldline.template.location.LocationHelper;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.model.mapper.HomeItemModelMapper;
import com.worldline.template.view.IView;
import com.worldline.template.view.fragment.MainFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
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

    private String sortBy = "";

    @Inject
    MainFragmentPresenter(GetHomeItemsUseCase getHomeItemsUseCase, HomeItemModelMapper homeItemsModelMapper) {
        this.getHomeItemsUseCase = getHomeItemsUseCase;
        this.homeItemModelMapper = homeItemsModelMapper;
    }

    @Override
    public void initialize() {
        locationHelper = new LocationHelper(getView().getContext());
        lastKnownLocation = new Location("");
        if (locationHelper.checkPlayServices()) {
            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient();
        }
        if (locationHelper.getGoogleApiCLient() != null) {
            locationHelper.connectApiClient();
        }
        if (locationHelper.checkPermission()) {
            locationManager = (LocationManager) getView().getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListenerGPS);
        }
    }

    @Override
    public void resume() {
        view.closeSearchView();
        refreshLocationsList();
    }

    private LocationListener locationListenerGPS = new LocationListener() {
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

    private void refreshLocationsList() {
        if (homeItemModelList == null) {
            getHomeItems();
        } else {
            getLastLocation();
            calculateAllDistances(homeItemModelList);
            sort(sortBy, homeItemModelList, false);
        }
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
                getFavoriteFromPreferences(homeItemModelList);
                if (homeItemModelList == null || homeItemModelList.isEmpty()) {
                    view.showEmptyCase();
                } else {
                    refreshLocationsList();
                }
            }
        });
    }

    private void getFavoriteFromPreferences(List<HomeItemModel> homeItems) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getView().getContext());
        for (HomeItemModel item : homeItems) {
            item.setFavorite(sharedPreferences.getBoolean(Integer.toString(item.getId()), false));
        }
    }

    public void toggleItemFavorite(HomeItemModel item) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getView().getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (item.getFavorite()) {
            item.setFavorite(false);
            editor.remove(Integer.toString(item.getId()));
        } else {
            item.setFavorite(true);
            editor.putBoolean(Integer.toString(item.getId()), true);
        }
        editor.apply();
    }

    private void calculateAllDistances(List<HomeItemModel> homeItemModelList) {
        for (HomeItemModel item : homeItemModelList) {
            item.setDistanceInKm(distance(item));
        }
    }

    private String distance(HomeItemModel item) {
        item.setCoordinates();
        Location loc1 = new Location("");
        loc1.setLongitude(item.getLongitude());
        loc1.setLatitude(item.getLatitude());
        if (lastKnownLocation.getLongitude() == 0 && lastKnownLocation.getLatitude() == 0) {
            return "0";
        } else {
            float distanceMeters = lastKnownLocation.distanceTo(loc1);
            float distanceKm = distanceMeters / 1000;
            return String.format(Locale.ENGLISH, "%.2f", distanceKm);
        }
    }

    final public Comparator<HomeItemModel> compDistance = new Comparator<HomeItemModel>() {
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

    final public Comparator<HomeItemModel> compNameAsc = new Comparator<HomeItemModel>() {
        @Override
        public int compare(HomeItemModel o1, HomeItemModel o2) {
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        }
    };

    final public Comparator<HomeItemModel> compNameDesc = new Comparator<HomeItemModel>() {
        @Override
        public int compare(HomeItemModel o1, HomeItemModel o2) {
            return o2.getTitle().compareToIgnoreCase(o1.getTitle());
        }
    };

    final public Comparator<HomeItemModel> compFav = new Comparator<HomeItemModel>() {

        @Override
        public int compare(HomeItemModel o1, HomeItemModel o2) {
            return (o1.getFavorite() != o2.getFavorite()) ? (o1.getFavorite()) ? -1 : 1 : 0;
        }
    };

    public void onSortClicked(String sortBy) {
        sort(sortBy, homeItemModelList, false);
    }

    public void sort(String sortBy, List<HomeItemModel> homeItemModelList, boolean filtered) {
        this.sortBy = sortBy;
        if (filtered) {
            view.showItems(sortByType(sortBy, homeItemModelList));
        } else {
            view.showItems(sortByType(sortBy, this.homeItemModelList));
        }
        if (!sortBy.equals("")) {
            view.scrollToTop();
            this.sortBy = "";
        }
    }

    private List<HomeItemModel> sortByType(String sortBy, List<HomeItemModel> homeItemModelList) {
        if (sortBy == null || sortBy.equals("")) {
            if (lastKnownLocation == null) {
                Collections.sort(homeItemModelList, compNameAsc);
            } else {
                Collections.sort(homeItemModelList, compDistance);
            }
        } else if (sortBy.equals(getView().getString(R.string.sortAscendant))) {
            Collections.sort(homeItemModelList, compNameAsc);
        } else if (sortBy.equals(getView().getString(R.string.sortDescendant))) {
            Collections.sort(homeItemModelList, compNameDesc);
        } else if (sortBy.equals(getView().getString(R.string.sortByDistance))) {
            Collections.sort(homeItemModelList, compDistance);
        } else {
            Collections.sort(homeItemModelList, compFav);
        }
        return homeItemModelList;
    }


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
        if (homeItemModelList != null && !homeItemModelList.isEmpty()) {
            filter(homeItemModelList, newText);
        } else {
            refreshLocationsList();
        }
    }

    private void filter(List<HomeItemModel> list, String query) {
        List<HomeItemModel> filteredList = new ArrayList<HomeItemModel>();
        for (HomeItemModel model : list) {
            if (model.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(model);
            }
        }
        sort(sortBy, filteredList, true);
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

        void closeSearchView();

        void scrollToTop();
    }
}

