package at.fhj.mad.poiapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Simone on 03.04.2017.
 */

public class PoiDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "POI.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_POIS = "Pois";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LAT = "latitude";
    public static final String COLUMN_LONG = "longitude";
    public static final String COLUMN_ADDRESS = "address";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_POIS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_LAT + " real, "
            + COLUMN_LONG + " real, "
            + COLUMN_ADDRESS + " text);";

    public PoiDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POIS);
        onCreate(db);
    }
}
