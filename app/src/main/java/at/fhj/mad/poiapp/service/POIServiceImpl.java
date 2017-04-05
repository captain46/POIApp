package at.fhj.mad.poiapp.service;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import at.fhj.mad.poiapp.PoiLocation;
import at.fhj.mad.poiapp.database.PoiDataAccess;

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

    @Override
    public void savePoi(Context context, PoiLocation poiLocation) {
        PoiDataAccess poiDataAccess = new PoiDataAccess(context);
        long id = poiDataAccess.addPoi(poiLocation);
        poiLocation.setId(id);
        Log.i("DB OP", "POI "+poiLocation.toString()+ "has been added to database");
    }

    @Override
    public void deletePoi(Context context, PoiLocation poiLocation) {
        Log.i("DB OP", "POI "+poiLocation.toString()+ "will be removed from database");
        PoiDataAccess poiDataAccess = new PoiDataAccess(context);
        poiDataAccess.deletePoi(poiLocation);
    }

    @Override
    public void deleteAllPois(Context context) {
        PoiDataAccess poiDataAccess = new PoiDataAccess(context);
        poiDataAccess.deleteAllPois();
        Log.i("DB OP", "All POIs have been removed");
    }

    @Override
    public List<PoiLocation> getAllPOIs(Context context) {
        Log.i("DB OP", "Request all POIs");
        PoiDataAccess poiDataAccess = new PoiDataAccess(context);
        List<PoiLocation> savedPois = poiDataAccess.getAllPois();
        return savedPois;
    }
}
