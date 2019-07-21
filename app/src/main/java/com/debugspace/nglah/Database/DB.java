package com.debugspace.nglah.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DB extends SQLiteOpenHelper {
    private static final String Dbname = "Nglah_database.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    private static int version_code = 1;

    /* version number of the database (starting at 1); if the database is older,
           onUpgrade will be used to upgrade the database; if the database is
           newer, onDowngrade will be used to downgrade the database
     */
    private static Context context;

    public DB(Context context) {
        super(context, Dbname, null, version_code);   // create the database
        this.context = context;
    }

    public void CopyDataBaseFromAsset() throws IOException{

        InputStream myInput = context.getAssets().open(Dbname);

        // Path to the just created empty db
        String outFileName = getDatabasePath();

        // if the path doesn't exist first, create it

        File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);

        if (!f.exists())

        f.mkdir();

        // Open the empty db as the output stream

        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];

        int length;

        while ((length = myInput.read(buffer)) > 0) {

            myOutput.write(buffer, 0, length);

        }



        // Close the streams

        myOutput.flush();

        myOutput.close();

        myInput.close();

    }

    private static String getDatabasePath() {

        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX

                + Dbname;

    }



    public SQLiteDatabase openDataBase() throws SQLException {

        File dbFile = context.getDatabasePath(Dbname);

        if (!dbFile.exists()) {

            try {

                CopyDataBaseFromAsset();

                System.out.println("Copying sucess from Assets folder");

            } catch (IOException e) {

                throw new RuntimeException("Error creating source database", e);

            }

        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //created when the db is being created at the first time
        // execSql >> performe single sql query that doesn't return data
//        try {
//            db.execSQL("create table " + Tablename + "( region_id integer primary key , country_name text);");
//            Toast.makeText(context, "DataBase is Created Successfully .", Toast.LENGTH_SHORT).show();
//        } catch (SQLException e) {
//            Toast.makeText(context, " DB Creation exception ", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //use this method to upgrade to a new database schema
        // Not mandatory to make drop to the table , you can make any query in the upgrade method
//        try {
//            db.execSQL("drop table if exists " + Tablename);
//            Toast.makeText(context, "database old version : " + oldVersion + " is Upgraded Successfully to newly version :" + newVersion, Toast.LENGTH_LONG).show();
//            onCreate(db); // to recreate the table again
//        } catch (SQLException e) {
//            Toast.makeText(context, " DB Upgrade exception ", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        try {
//            db.execSQL("drop table if exists " + Tablename);
//            Toast.makeText(context, "database is downgraged - old version : " + oldVersion + " && newly version :" + newVersion, Toast.LENGTH_LONG).show();
//            onCreate(db); // to recreate the table again
//        } catch (SQLException e) {
//            Toast.makeText(context, " DB Downgraged exception ", Toast.LENGTH_SHORT).show();
//        }
    }

//    public boolean insertDate(String name, String email) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues content = new ContentValues();
//        content.put("name", name);
//        content.put("email", email);
//        long result = db.insert("countru", null, content);
//        // result >> the row ID of the newly inserted row, or -1 if an error occurred
//        if (result == -1) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    public Map<String, ArrayList> getRegionsWithSectors(String id) {
        Map<String, ArrayList> arrayListMap = new HashMap<>();
        ArrayList arrayList1 = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select region, sector from nglah_region where region_id=? ", new String[]{id});
        //Cursor object is positioned before the first entry.
        while (cursor.moveToNext()) {
            String region = cursor.getString(cursor.getColumnIndex("region"));
            String sector = cursor.getString(cursor.getColumnIndex("sector"));
            arrayList1.add(region);
            arrayList2.add(sector);
        }
        arrayListMap.put("regionList", arrayList1);
        arrayListMap.put("sectorList", arrayList2);
        return arrayListMap;
    }


    public ArrayList getCities(String id) {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select cities from nglah_city where city_id=? ", new String[]{id});
        //Cursor object is positioned before the first entry.
        while (cursor.moveToNext()) {
            String city = cursor.getString(cursor.getColumnIndex("cities"));
            arrayList.add(city);
        }
        return arrayList;
    }


    public ArrayList getCitiesOutside() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select cities from nglah_city", null);
        //Cursor object is positioned before the first entry.
        while (cursor.moveToNext()) {
            String city = cursor.getString(cursor.getColumnIndex("cities"));
            arrayList.add(city);
        }
        return arrayList;
    }


    public ArrayList getSectorsOutside() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select sector from nglah_region", null);
        //Cursor object is positioned before the first entry.
        while (cursor.moveToNext()) {
            String sector = cursor.getString(cursor.getColumnIndex("sector"));
            arrayList.add(sector);
        }
        return arrayList;
    }

}

