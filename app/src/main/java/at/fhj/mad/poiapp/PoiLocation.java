package at.fhj.mad.poiapp;

/**
 * Created by Simone on 31.03.2017.
 */

public class PoiLocation {

    private long id;
    private String name;
    private double longitude;
    private double latitude;
    private String poiResult;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "{id:" + id + ", name:" + name + ", lat:" + latitude + ", lon:" + longitude + "}";
    }
}
