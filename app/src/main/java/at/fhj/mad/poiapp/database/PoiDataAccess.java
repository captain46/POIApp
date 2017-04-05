package at.fhj.mad.poiapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import at.fhj.mad.poiapp.PoiLocation;

/**
 * Created by Simone on 03.04.2017.
 */

public class PoiDataAccess {
    private PoiDbHelper poiDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public PoiDataAccess(Context context) {
        poiDbHelper = new PoiDbHelper(context);
    }

    public long addPoi(PoiLocation poiLocation) {

        sqLiteDatabase = poiDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PoiDbHelper.COLUMN_NAME, poiLocation.getName());
        contentValues.put(PoiDbHelper.COLUMN_LONG, poiLocation.getLongitude());
        contentValues.put(PoiDbHelper.COLUMN_LAT, poiLocation.getLatitude());
        contentValues.put(PoiDbHelper.COLUMN_ADDRESS, poiLocation.getPoiResult());

        long id = sqLiteDatabase.insert(PoiDbHelper.TABLE_POIS, null, contentValues);

        sqLiteDatabase.close();

        return id;
    }

    public void deletePoi(PoiLocation poiLocation) {

        sqLiteDatabase = poiDbHelper.getWritableDatabase();

        sqLiteDatabase.delete(PoiDbHelper.TABLE_POIS, PoiDbHelper.COLUMN_ID + "=" + poiLocation.getId(), null);
        sqLiteDatabase.close();
    }

    public List<PoiLocation> getAllPois() {
        List<PoiLocation> poiLocations = new ArrayList<>();

        String query = "SELECT * FROM " + PoiDbHelper.TABLE_POIS;
        sqLiteDatabase = poiDbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                PoiLocation poiLocation = new PoiLocation();
                poiLocation.setId(cursor.getLong(0));
                poiLocation.setName(cursor.getString(1));
                poiLocation.setLatitude(cursor.getDouble(2));
                poiLocation.setLongitude(cursor.getDouble(3));
                poiLocation.setPoiResult(cursor.getString(4));

                poiLocations.add(poiLocation);
            } while (cursor.moveToNext());
        }
        return poiLocations;
    }
}
