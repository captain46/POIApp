package at.fhj.mad.poiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import at.fhj.mad.poiapp.service.POIService;
import at.fhj.mad.poiapp.service.POIServiceImpl;

public class NewPOIScreenManual extends AppCompatActivity {

    private PoiLocation poiLocation;
    private POIService poiService;
    private EditText editName;
    private EditText editLongitude;
    private EditText editLatitude;
    private EditText editAddress;
    private TextView textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_poiscreen_manual);

        poiService = new POIServiceImpl();
        poiLocation = new PoiLocation();
        editName = (EditText) findViewById(R.id.editName);
        editLongitude = (EditText) findViewById(R.id.editLongitude);
        editLatitude = (EditText) findViewById(R.id.editLatitude);
        editAddress = (EditText) findViewById(R.id.editAddress);
        textInfo = (TextView) findViewById(R.id.textInfo);
    }

    public void saveManualPOI(View view) {
        if(validateInput()) {
            poiLocation.setName(editName.getText().toString());
            try {
                poiLocation.setLongitude(Double.parseDouble(editLongitude.getText().toString()));
                poiLocation.setLatitude(Double.parseDouble(editLatitude.getText().toString()));
            } catch (NumberFormatException nfe) {

            }

            poiLocation.setAddress(editAddress.getText().toString());
            poiService.savePoi(this, poiLocation);

            clearFields(view);
            textInfo.setText("POI has been successful added.");
        }
    }

    public void clearFields(View view) {
        editName.setText("Name");
        editLongitude.setText("Longitude");
        editLatitude.setText("Latitude");
        editAddress.setText("Address");
        textInfo.setText("");
        poiLocation = new PoiLocation();
    }

    public boolean validateInput() {
         if(editName.getText().toString().equals("") || editName.getText().toString().equals("Name")) {
            textInfo.setText("Please enter a valid name for your POI.");
            return false;
        }

        if(editAddress.getText().toString().equals("") || editAddress.getText().toString().equals("Address")) {
            textInfo.setText("Please enter at least the address for your POI.");
            return false;
        }

        return true;
    }
}
