package vishnu.com.testapp.Activity;

/**
 * Created by vishn on 12/11/2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;



public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Test.db";
    public static final String MEDIA_TABLE_NAME = "media";
    public static final String MEDIA_COLUMN_ID = "id";
    public static final String MEDIA_COLUMN_TITLE = "title";
    public static final String MEDIA_COLUMN_FARM = "farm";
    public static final String MEDIA_COLUMN_URL = "url";
    public static final String MEDIA_COLUMN_SECRET = "secret";
    public static final String MEDIA_COLUMN_SERVER = "server";

    public static final String PROJECT_COLUMN_ID = "id";
    public static final String PROJECT_COLUMN_NAME = "projectname";
    public static final String PROJECT_TABLE_NAME = "projecttable";
    public SQLiteDatabase db;



    private static final int DATABASE_VERSION = 1;
    public static Boolean LOCSTATUS ;
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);

       //  db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
       String sql = "CREATE TABLE IF NOT EXISTS " +MEDIA_TABLE_NAME+
               " ("+MEDIA_COLUMN_ID +" integer primary key, "+MEDIA_COLUMN_TITLE+" text, "+MEDIA_COLUMN_FARM+" text, " +
               MEDIA_COLUMN_SECRET+" text, "+MEDIA_COLUMN_SERVER+" text, "+MEDIA_COLUMN_URL+" text)";

        Log.d("dblog",sql);

        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS media");
        onCreate(db);
    }



    public boolean insertProject (String id, String name){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PROJECT_COLUMN_ID, Integer.parseInt(id));
        contentValues.put(PROJECT_COLUMN_NAME,name);
        db.insert(PROJECT_TABLE_NAME,null,contentValues);
        db.close();
//        long val = db.insert(PROJECT_TABLE_NAME, null, contentValues);
//        Log.d("dbcheck", "-- "+val);
        return true;


    }


    public boolean insertImage (String title, String id, String farm, String secret, String server,String url) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(MEDIA_COLUMN_TITLE, title);
        contentValues.put(MEDIA_COLUMN_FARM, farm);
        contentValues.put(MEDIA_COLUMN_ID , Integer.parseInt(id));
        contentValues.put(MEDIA_COLUMN_SECRET, secret);
        contentValues.put(MEDIA_COLUMN_SERVER, server);
        contentValues.put(MEDIA_COLUMN_URL, url);

         long val = db.insert(MEDIA_TABLE_NAME, null, contentValues);
        Log.d("dbcheck", "-- "+val);
        return true;
    }


    public int checkdata(){


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from "+MEDIA_TABLE_NAME,null);

        return res.getCount();
    }

    public ArrayList<PictureModel> getAllPics() {
        ArrayList<PictureModel> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+MEDIA_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //  array_list.add(res.getString(res.getColumnIndex(MEDIA_COLUMN_TITLE)));
            PictureModel item = new PictureModel();
            item.setId(res.getString(res.getColumnIndex(MEDIA_COLUMN_ID)));
            item.setTitle(res.getString(res.getColumnIndex(MEDIA_COLUMN_TITLE)));
            item.setFarm(res.getString(res.getColumnIndex(MEDIA_COLUMN_FARM)));
            item.setServer(res.getString(res.getColumnIndex(MEDIA_COLUMN_SERVER)));
            item.setSecret(res.getString(res.getColumnIndex(MEDIA_COLUMN_SECRET)));
            item.setUrl(res.getString(res.getColumnIndex(MEDIA_COLUMN_URL)));

            array_list.add(item);

            res.moveToNext();
        }

        return array_list;
    }



}
