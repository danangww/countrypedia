package app.dww.countrypedia.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by DWW on 28/04/2016.
 */
public class DataCountry extends SQLiteOpenHelper {
    //deklarasi dan inisialisasi variable
    public static final String DB_NAME="dbcountry";
    public static final String TABLE_NAME="country";
    public static final String COUNTRY_NAME="name";
    public static final String COUNTRY_CAPITAL="capital";
    public static final String COUNTRY_REGION="region";
    public static final String COUNTRY_SUBREGION="subregion";
    public static final String COUNTRY_POPULATION="population";
    public static final String COUNTRY_LAT="latitude";
    public static final String COUNTRY_LON="longitude";
    private SQLiteDatabase db = null;

    public DataCountry(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public void createTable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("CREATE TABLE if not exists " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, " +
                "capital TEXT," +
                "region TEXT," +
                "subregion TEXT," +
                "population TEXT," +
                "latitude TEXT," +
                "longitude TEXT);");
    }

    public ArrayList<String> getAllCountry(){
        String selectQuery = "SELECT name FROM "+TABLE_NAME+" order by "+COUNTRY_NAME;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // perulangan untuk mengecek setiap baris hasil query
        ArrayList<String> hasil=new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                hasil.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return hasil;
    }

    public ArrayList<String> getDetailCountry(String country){
        String selectQuery = "SELECT name,capital,region,subregion,population,latitude,longitude FROM "+TABLE_NAME+" where "+COUNTRY_NAME+"='"+country+"'";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // perulangan untuk mengecek setiap baris hasil query
        ArrayList<String> hasil=new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                hasil.add(cursor.getString(0));
                hasil.add(cursor.getString(1));
                hasil.add(cursor.getString(2));
                hasil.add(cursor.getString(3));
                hasil.add(cursor.getString(4));
                hasil.add(cursor.getString(5));
                hasil.add(cursor.getString(6));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return hasil;
    }

    public String inputCountry(String name,String capital,String region,String subregion,String population,String lat,String lon){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COUNTRY_NAME, name);
        cv.put(COUNTRY_CAPITAL, capital);
        cv.put(COUNTRY_REGION, region);
        cv.put(COUNTRY_SUBREGION, subregion);
        cv.put(COUNTRY_POPULATION, population);
        cv.put(COUNTRY_LAT, lat);
        cv.put(COUNTRY_LON, lon);
        db.insert(TABLE_NAME, null, cv);

        return "Data berhasil disimpan";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    public void clear(){
        db = this.getWritableDatabase();
        createTable(db);
    }

    public boolean isEmpty(){
        String selectQuery = "SELECT * FROM "+TABLE_NAME;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
}
