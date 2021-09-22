package com.zebra.jamesswinton.blockincomingcallstest;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class PermissionsHelper {

    // Constants
    public static final int PERMISSIONS_REQUEST_CODE = 1000;
    private static final List<String> PERMISSIONS = new ArrayList<>();
    static {
        PERMISSIONS.add(CALL_PHONE);
    }

    // Variables
    private Activity mActivity;
    private OnPermissionsResultListener mOnPermissionsResultListener;

    // Interfaces
    public interface OnPermissionsResultListener {
        void onPermissionsGranted();
    }

    public PermissionsHelper(@NonNull Activity activity,
                             @NonNull OnPermissionsResultListener onPermissionsResultListener) {
        this.mActivity = activity;
        this.mOnPermissionsResultListener = onPermissionsResultListener;
        forcePermissionsUntilGranted();
    }

    public void forcePermissionsUntilGranted() {
        if (checkStandardPermissions()) {
            mOnPermissionsResultListener.onPermissionsGranted();
        } else {
            requestStandardPermission();
        }
    }

    private boolean checkStandardPermissions() {
        boolean permissionsGranted = true;
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(mActivity, permission) != PERMISSION_GRANTED) {
                permissionsGranted = false;
                break;
            }
        }
        return permissionsGranted;
    }

    private void requestStandardPermission() {
        ActivityCompat.requestPermissions(mActivity, PERMISSIONS.toArray(new String[0]), PERMISSIONS_REQUEST_CODE);
    }

    public void onRequestPermissionsResult() {
        forcePermissionsUntilGranted();
    }

}
