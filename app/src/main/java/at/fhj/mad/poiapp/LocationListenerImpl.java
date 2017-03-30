package at.fhj.mad.poiapp;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Simone on 31.03.2017.
 */

public class LocationListenerImpl implements LocationListener {

    private PoiLocation poiLocation;

    public LocationListenerImpl(PoiLocation poiLocation) {
        this.poiLocation = poiLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Log.e("Latitude", String.valueOf(latitude));
        Log.e("Longitude", String.valueOf(longitude));

        poiLocation.setLatitude(latitude);
        poiLocation.setLongitude(longitude);
        //getAddress(longitude, latitude);
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
}
