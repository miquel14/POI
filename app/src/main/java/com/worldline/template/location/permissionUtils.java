package com.worldline.template.location;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class permissionUtils {

    private Context context;

    private Activity currentActivity;

    private PermissionResultCallback permissionResultCallback;

    private ArrayList<String> permissionList = new ArrayList<>();

    private ArrayList<String> listPermissionsNeeded = new ArrayList<>();

    private String dialogContent = "";

    private int reqCode;

    private permissionUtils(Context context) {
        this.context = context;
        this.currentActivity = (Activity) context;

        permissionResultCallback = (PermissionResultCallback) context;
    }

    public permissionUtils(Context context, PermissionResultCallback callback) {
        this.context = context;
        this.currentActivity = (Activity) context;
        permissionResultCallback = callback;
    }

    /**
     * Check the API Level & Permission
     */

    public boolean checkPermission(ArrayList<String> permissions, String dialogContent, int requestCode) {
        this.permissionList = permissions;
        this.dialogContent = dialogContent;
        this.reqCode = requestCode;

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkAndRequestPermissions(permissions, requestCode)) {
                permissionResultCallback.permissionGranted(requestCode);
                Log.i("all permissions", "granted");
                Log.i("proceed", "to callback");
                return true;
            } else {
                return false;
            }
        } else {
            permissionResultCallback.permissionGranted(requestCode);
            Log.i("all permissions", "granted");
            Log.i("proceed", "to callback");
            return true;
        }
    }

    /**
     * Check and request the Permissions
     */

    private boolean checkAndRequestPermissions(ArrayList<String> permissions, int requestCode) {
        if (permissions.size() > 0) {
            listPermissionsNeeded = new ArrayList<>();
            for (int i = 0; i < permissions.size(); i++) {
                int hasPermission = ContextCompat.checkSelfPermission(currentActivity, permissions.get(i));
                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(permissions.get(i));
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(currentActivity,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), requestCode);
                return false;
            }
        }
        return true;
    }

    /**
     *
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    Map<String, Integer> perms = new HashMap<>();

                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }

                    final ArrayList<String> pendingPermissions = new ArrayList<>();

                    for (int i = 0; i < listPermissionsNeeded.size(); i++) {
                        if (perms.get(listPermissionsNeeded.get(i)) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat
                                    .shouldShowRequestPermissionRationale(currentActivity, listPermissionsNeeded.get(i))) {
                                pendingPermissions.add(listPermissionsNeeded.get(i));
                            } else {
                                Log.i("Go to settings", "and enable permissions");
                                permissionResultCallback.neverAskAgain(reqCode);
                                Toast.makeText(currentActivity, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                        .show();
                                return;
                            }
                        }

                    }

                    if (pendingPermissions.size() > 0) {
                        showMessageOKCancel(dialogContent,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                checkPermission(permissionList, dialogContent, reqCode);
                                                break;
                                            case DialogInterface.BUTTON_NEGATIVE:
                                                Log.i("permisson", "not fully given");
                                                if (permissionList.size() == pendingPermissions.size()) {
                                                    permissionResultCallback.permissionDenied(reqCode);
                                                } else {
                                                    permissionResultCallback
                                                            .partialPermissionGranted(reqCode, pendingPermissions);
                                                }
                                                break;
                                        }


                                    }
                                });

                    } else {
                        Log.i("all", "permissions granted");
                        Log.i("proceed", "to next step");
                        permissionResultCallback.permissionGranted(reqCode);

                    }


                }
                break;
        }
    }


    /**
     * Explain why the app needs permissions
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(currentActivity)
                .setMessage(message)
                .setPositiveButton("Ok", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    public interface PermissionResultCallback {
        void permissionGranted(int requestCode);

        void partialPermissionGranted(int requestCode, ArrayList<String> grantedPermissions);

        void permissionDenied(int requestCode);

        void neverAskAgain(int requestCode);
    }
}


