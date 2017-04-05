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
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import at.fhj.mad.poiapp.service.HttpHelper;
import at.fhj.mad.poiapp.service.ICallback;
import at.fhj.mad.poiapp.service.POIService;
import at.fhj.mad.poiapp.service.POIServiceImpl;


public class NewPOIScreenGPS extends AppCompatActivity {

    private static final int REQUEST_GPS = 101;
    private PoiLocation poiLocation;
    private POIService poiService;
    private TextView foundCoordinates;
    private EditText poiName;
    private TextView poiInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_poiscreen_gps);

        poiService = new POIServiceImpl();
        poiLocation = new PoiLocation();
        foundCoordinates = (TextView) findViewById(R.id.foundCoordinates);
        poiName = (EditText) findViewById(R.id.poiName);
        poiInfo = (TextView) findViewById(R.id.poiInfo);
    }

    /**
     * gets the current location
     * @param view
     */
    public void getCurrentLocation(View view) {

        Log.i("NEW POI GPS", "try to get current location...");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListenerImpl();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_GPS);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, locationListener);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_GPS) {
            startGPS();
        }
    }

    private void startGPS() {
        Log.i("NEW POI GPS", "start GPS");
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_GPS);
        }
    }

    /**
     * saves validate objects to the database
     * @param view
     */
    public void savePOI(View view) {
        if (validatePOI(poiLocation)) {
            poiLocation.setName(poiName.getText().toString());
            poiService.savePoi(this, poiLocation);
            clearPOI(view);
            poiInfo.setText("POI has been successful added.");
        }
    }

    /**
     * resets the PoiLocation object and the view
     * @param view
     */
    public void clearPOI(View view) {
        NewPOIScreenGPS.this.foundCoordinates.setText("");
        NewPOIScreenGPS.this.poiName.setText("");
        poiLocation = new PoiLocation();
    }

    /**
     * validates the input data
     * @param poiLocation
     * @return
     */
    public boolean validatePOI(PoiLocation poiLocation) {
        if(poiLocation.getLatitude() < 0.0 || poiLocation.getLongitude() < 0.0) {
            poiInfo.setText("please look for a POI first before you save one");
        }
        if(poiName.getText().toString().equals("") || poiName.getText().toString().equals("Name")) {
            poiInfo.setText("you have to enter a valid name to save your POI");
            return false;
        }
        return true;
    }

    class LocationListenerImpl implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            poiLocation.setLatitude(location.getLatitude());
            poiLocation.setLongitude(location.getLongitude());

            Log.i("Latitude", String.valueOf(poiLocation.getLatitude()));
            Log.i("Longitude", String.valueOf(poiLocation.getLongitude()));

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
    }

    class AsyncCallback implements ICallback {

        @Override
        public void handleResult(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONObject jsonResult = jsonArray.getJSONObject(0);

                String address = jsonResult.getString("formatted_address");
                poiLocation.setAddress(address);

                NewPOIScreenGPS.this.foundCoordinates.setText(poiLocation.getAddress());
                Log.i("resolved address: ",address);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
