package at.fhj.mad.poiapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Simone on 01.04.2017.
 */

public class POIServiceImpl implements POIService {

    @Override
    public String resolveAddress(PoiLocation poiLocation) {

        StringBuilder sb = new StringBuilder();
        sb.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");

        if(poiLocation.getLongitude() > 0.0 && poiLocation.getLatitude() > 0.0) {
            sb.append(String.valueOf(poiLocation.getLatitude()))
                    .append(",")
                    .append(String.valueOf(poiLocation.getLongitude()))
                    .append("&sensor=true");
        }

        return sb.toString();
    }
}
