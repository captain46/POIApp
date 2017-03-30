package at.fhj.mad.poiapp;

/**
 * Created by Simone on 31.03.2017.
 */

public class PoiLocation {

    private double longitude;
    private double latitude;
    private String poiResult;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPoiResult() {
        return poiResult;
    }

    public void setPoiResult(String poiResult) {
        this.poiResult = poiResult;
    }
}
