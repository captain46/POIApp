package at.fhj.mad.poiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import at.fhj.mad.poiapp.service.POIService;
import at.fhj.mad.poiapp.service.POIServiceImpl;

public class POIDetail extends AppCompatActivity {

    private PoiLocation poiLocation;
    private POIService poiService;
    private TextView showName;
    private TextView showLon;
    private TextView showLat;
    private TextView showAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poidetail);

        poiService = new POIServiceImpl();
        poiLocation = (PoiLocation) getIntent().getExtras().getSerializable("poiLocation");

        displayPoiDetail(poiLocation);
    }

    public void displayPoiDetail(PoiLocation poiLocation) {
        showName = (TextView)findViewById(R.id.showName);
        showLon = (TextView) findViewById(R.id.showLongitude);
        showLat = (TextView) findViewById(R.id.showLatitude);
        showAddress = (TextView) findViewById(R.id.showAddress);

        showName.setText(poiLocation.getName());
        //show longitude and latitude data only if they exist
        if (poiLocation.getLatitude() > 0.0 && poiLocation.getLongitude() > 0.0) {
            showLon.setText("Longitude: " + poiLocation.getLongitude());
            showLat.setText("Latitude: " + poiLocation.getLatitude());
        }
        showAddress.setText(poiLocation.getAddress());
    }

    public void deletePoi(View view) {
        poiService.deletePoi(this, poiLocation);

        //forward to POI view and show all existing
        Intent intent = new Intent(this, POIList.class);
        startActivity(intent);
    }
}
