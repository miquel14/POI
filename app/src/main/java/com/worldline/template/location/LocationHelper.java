package com.worldline.template.location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class LocationHelper implements com.worldline.template.location.permissionUtils.PermissionResultCallback {

    private Context context;

    private Activity currentActivity;

    private boolean isPermissionGranted;

    private Location lastLocation;

    // Google client to interact with Google API

    private GoogleApiClient googleApiClient;

    // list of permissions

    private ArrayList<String> permissions = new ArrayList<>();

    private com.worldline.template.location.permissionUtils permissionUtils;

    private final static int PLAY_SERVICES_REQUEST = 1000;

    private final static int REQUEST_CHECK_SETTINGS = 2000;

    public LocationHelper(Context context) {

        this.context = context;
        this.currentActivity = (Activity) context;

        permissionUtils = new permissionUtils(context, this);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    /**
     * Method to check the availability of location permissions
     */

    public boolean checkPermission() {
        return permissionUtils.checkPermission(permissions, "Need GPS permission for getting your location", 1);
    }

    private boolean isPermissionGranted() {
        return isPermissionGranted;
    }

    /**
     * Method to verify google play services on the device
     */

    public boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(currentActivity, resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                showToast("This device is not supported.");
            }
            return false;
        }
        return true;
    }

    /**
     * Method to display the location on UI
     */

    public Location getLocation() {
        if (isPermissionGranted()) {
            try {
                lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                return lastLocation;
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Method used to build GoogleApiClient
     */

    public void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) currentActivity)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) currentActivity)
                .addApi(LocationServices.API).build();

        googleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        lastLocation = getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(currentActivity, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    /**
     * Method used to connect GoogleApiClient
     */
    public void connectApiClient() {
        googleApiClient.connect();
    }

    /**
     * Method used to get the GoogleApiClient
     */
    public GoogleApiClient getGoogleApiCLient() {
        return googleApiClient;
    }


    /**
     * Handles the permission results
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Handles the activity results
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        lastLocation = getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
                break;
        }
    }


    @Override
    public void permissionGranted(int requestCode) {
        Log.i("PERMISSION", "GRANTED");
        isPermissionGranted = true;
    }

    @Override
    public void partialPermissionGranted(int requestCode, ArrayList<String> grantedPermissions) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
    }

    @Override
    public void permissionDenied(int requestCode) {
        Log.i("PERMISSION", "DENIED");
    }

    @Override
    public void neverAskAgain(int requestCode) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
    }


    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
