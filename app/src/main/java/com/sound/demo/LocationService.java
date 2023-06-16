package com.sound.demo;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class LocationService extends Service {

    private static final long INTERVAL = 5000; // 5 seconds
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("checkLocation", "started");
        getLocationUpdates();

        return START_STICKY;
    }

    private void getLocationUpdates() {
        LocationListener  locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Handle the received location data
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // Update the UI or save the location data
                Log.d("checkLocation", "Latitude: "+latitude+"\nLongitude: "+longitude);
                Toast.makeText(getApplicationContext(), "Latitude: "+latitude+"Longitude: "+longitude, Toast.LENGTH_SHORT).show();
                // e.g., add the location to a list and notify the RecyclerView adapter

                // Notify the RecyclerView adapter about the new location data
                // recyclerViewAdapter.notifyItemInserted(position);
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

        // Request location updates from the LocationManager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, INTERVAL, 0, locationListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop location updates when the service is destroyed
        locationManager.removeUpdates(locationListener);
    }
}

