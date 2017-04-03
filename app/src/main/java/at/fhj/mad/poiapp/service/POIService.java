package at.fhj.mad.poiapp.service;

import android.content.Context;

import java.util.List;

import at.fhj.mad.poiapp.PoiLocation;

/**
 * Created by Simone on 01.04.2017.
 */

public interface POIService {

    /**
     * resolves the address from a given POI with a longitude and a latitude
     * @param poiLocation
     * @return - the resolved address
     */
    String resolveAddress(PoiLocation poiLocation);

    /**
     * saves the given poiLocation to the database
     * @param context
     * @param poiLocation
     */
    void savePoi(Context context, PoiLocation poiLocation);

    List<String> getAllPOIs(Context context);
}
