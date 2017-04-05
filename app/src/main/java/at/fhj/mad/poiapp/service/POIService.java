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

    /**
     * removes a selected poiLocation from the database
     * @param context
     * @param poiLocation
     */
    void deletePoi(Context context, PoiLocation poiLocation);

    /**
     * removes all saved POIs from the database
     * @param context
     */
    void deleteAllPois(Context context);

    /**
     * returns a list of all saved POIs
     * @param context
     * @return
     */
    List<PoiLocation> getAllPOIs(Context context);
}
