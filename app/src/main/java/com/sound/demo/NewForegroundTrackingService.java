package com.sound.demo;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;

public class NewForegroundTrackingService extends Service implements LocationListener {

    private static final String TAG = "BookingTrackingService";
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "ForegroundServiceChannel";

    private Context context;
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private boolean isTracking = false;

    public double track_lat = 0.0;
    public double track_lng = 0.0;
    public static String str_receiver = "servicetutorial.service.receiver";
    Intent intent;
    private Realm realm;
    private Handler handler;
    private Runnable locationRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        realm = Realm.getDefaultInstance();
        intent = new Intent(str_receiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, buildNotification());
        fn_getlocation();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    private void fn_getlocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {
            Log.d(TAG, "CAN'T GET LOCATION");
            stopSelf();
        } else {
            if (isNetworkEnable) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (isGPSEnable) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(getApplicationContext(), "Latitude: " + latitude + " Longitude: " + longitude, Toast.LENGTH_SHORT).show();
                track_lat = latitude;
                track_lng = longitude;
                fn_update(location);
                if (!isTracking) { // Start tracking only if it's not already in progress
                    trackLocation();
                }
            }
        }
    }

    private void trackLocation() {
        // Log.d(TAG, "trackLocation");
        String TAG_TRACK_LOCATION = "trackLocation";
        Map<String, String> params = new HashMap<>();
        params.put("latitude", "" + track_lat);
        params.put("longitude", "" + track_lng);
        addDataToDatabase(track_lat, track_lng);
        Log.d(TAG, "param_track_location >> " + params.toString());

        // Call trackLocation() here if you want to continuously track location updates
//        trackLocation();

        // Stop the service after tracking the location
        stopSelf();
    }

    private void addDataToDatabase(double latitude, double longitude) {

        // on below line we are creating
        // a variable for our modal class.
        LocationModal modal = new LocationModal();

        // on below line we are getting id for the course which we are storing.
        Number id = realm.where(LocationModal.class).max("id");

        // on below line we are
        // creating a variable for our id.
        long nextId;

        // validating if id is null or not.
        if (id == null) {
            // if id is null
            // we are passing it as 1.
            nextId = 1;
        } else {
            // if id is not null then
            // we are incrementing it by 1
            nextId = id.intValue() + 1;
        }
        // on below line we are setting the
        // data entered by user in our modal class.
        modal.setId(nextId);
        modal.setLat_(String.valueOf(latitude));
        modal.setLong_(String.valueOf(longitude));

        // on below line we are calling a method to execute a transaction.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // inside on execute method we are calling a method
                // to copy to real m database from our modal class.
                realm.copyToRealm(modal);
            }
        });
    }

    private void fn_update(Location location) {
        intent.putExtra("latitude", location.getLatitude() + "");
        intent.putExtra("longitude", location.getLongitude() + "");
        sendBroadcast(intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        // Handle location updates if needed
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Handle status changes if needed
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Handle provider enabled if needed
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Handle provider disabled if needed
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private Notification buildNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("Tracking Location")
                .setContentText("Tracking user location in the background")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_SERVICE);

        return builder.build();
    }
}
