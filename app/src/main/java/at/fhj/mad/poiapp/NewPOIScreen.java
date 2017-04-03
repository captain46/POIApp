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


public class NewPOIScreen extends AppCompatActivity {
    private static final String TAG = "NewPOIScreen";

    private PoiLocation poiLocation;
    private POIService poiService;
    private TextView foundCoordinates;
    private EditText poiName;
    private TextView poiInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_poiscreen);

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

    /**
     * saves validate objects to the database
     * @param view
     */
    public void savePOI(View view) {
        if (validatePOI(poiLocation)) {
            poiLocation.setName(poiName.getText().toString());
            poiService.savePoi(this, poiLocation);
            clearPOI(view);
            poiInfo.setText("successful saved POI "+poiLocation.getName());
        }
    }

    /**
     * resets the PoiLocation object and the view
     * @param view
     */
    public void clearPOI(View view) {
        NewPOIScreen.this.foundCoordinates.setText("");
        NewPOIScreen.this.poiName.setText("");
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

            Log.e("Latitude", String.valueOf(poiLocation.getLatitude()));
            Log.e("Longitude", String.valueOf(poiLocation.getLongitude()));

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
                poiLocation.setPoiResult(address);

                NewPOIScreen.this.foundCoordinates.setText(poiLocation.getPoiResult());
                Log.i("ADDRESS",address);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
