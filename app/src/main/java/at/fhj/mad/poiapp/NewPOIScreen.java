package at.fhj.mad.poiapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NewPOIScreen extends AppCompatActivity {

    private static final int REQUEST_GPS = 101;

    private static final String TAG = "NewPOIScreen";

    private PoiLocation poiLocation;
    private TextView foundCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_poiscreen);

        poiLocation = new PoiLocation();
        foundCoordinates = (TextView) findViewById(R.id.foundCoordinates);
    }

    public void getCurrentLocation(View view) {

        Log.e("TEST", "I AM CLICKED");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListenerImpl();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, locationListener);
    }

    public void clearPOI(View view) {
        NewPOIScreen.this.foundCoordinates.setText("");
    }

    class LocationListenerImpl implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            poiLocation.setLatitude(location.getLatitude());
            poiLocation.setLongitude(location.getLongitude());

            Log.e("Latitude", String.valueOf(poiLocation.getLatitude()));
            Log.e("Longitude", String.valueOf(poiLocation.getLongitude()));

            POIService poiService = new POIServiceImpl();
            String url = poiService.resolveAddress(poiLocation);
            HttpHelper httpHelper = new HttpHelper(new AsyncCallback());
            httpHelper.execute(url);
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

        public void getAddress(double longitude, double latitude) {

            String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";

            if(longitude > 0.0 && latitude > 0.0) {
                url += String.valueOf(latitude) + "," + String.valueOf(longitude) + "&sensor=true";
            }

            HttpHelper httpHelper = new HttpHelper(new AsyncCallback());
            httpHelper.execute(url);

        }
    }

    class AsyncCallback implements ICallback {

        @Override
        public void handleResult(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONObject jsonResult = jsonArray.getJSONObject(0);

                String address = jsonResult.getString("formatted_address");
                poiLocation.setPoiResult(address);

                NewPOIScreen.this.foundCoordinates.setText(poiLocation.getPoiResult());
                Log.i("ADDRESS",address);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
