package com.bhola.english_ghoststory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    String DbName;
    private static  String DbPath;
    Context context;
    String Database_tableNo;


    public DatabaseHelper(@Nullable Context mcontext, String name, int version, String databaseNo) {
        super(mcontext, name, null, version);
        this.context = mcontext;
        this.DbName = name;
        this.Database_tableNo = databaseNo;
        DbPath = "/data/data/" + context.getPackageName() + "/databases/";

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d("TAGA", "oldVersion: " + oldVersion);
        Log.d("TAGA", "newVersion: " + newVersion);

    }

    public void CheckDatabases() {
        try {
            String path = DbPath + DbName;
            SQLiteDatabase.openDatabase(path, null, 0);
//            db_delete();
            //Database file is Copied here
            checkandUpdateLoginTimes_UpdateDatabaseCheck();
        } catch (Exception e) {
            this.getReadableDatabase();
            CopyDatabases();

        }
    }

    public void CopyDatabases() {


        try {
            InputStream mInputStream = context.getAssets().open(DbName);
            String outFilename = DbPath + DbName;


            OutputStream mOutputstream = new FileOutputStream(outFilename);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = mInputStream.read(buffer)) > 0) {
                mOutputstream.write(buffer, 0, length);
            }
            mOutputstream.flush();
            mOutputstream.close();
            mInputStream.close();

            //Database file is Copied here
            checkandUpdateLoginTimes_UpdateDatabaseCheck();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void OpenDatabase() {
        String path = DbPath + DbName;
        SQLiteDatabase.openDatabase(path, null, 0);


    }

    private void checkandUpdateLoginTimes_UpdateDatabaseCheck() {

        //       Check for Database Update

        Cursor cursor1 = new DatabaseHelper(context, SplashScreen.DB_NAME, SplashScreen.DB_VERSION, "DB_VERSION").readalldata();
        while (cursor1.moveToNext()) {
            int DB_VERSION_FROM_DATABASE = cursor1.getInt(1);

            if (DB_VERSION_FROM_DATABASE != SplashScreen.DB_VERSION_INSIDE_TABLE) {
                DatabaseHelper databaseHelper2 = new DatabaseHelper(context, SplashScreen.DB_NAME, SplashScreen.DB_VERSION, "DB_VERSION");
                databaseHelper2.db_delete();
            }

        }
        cursor1.close();

    }


    public void db_delete() {
        File file = new File(DbPath + DbName);
        if (file.exists()) {
            file.delete();
            Log.d("TAGAA", "file Deleted");
        }
        CopyDatabases();
    }

    public Cursor readalldata() {
        String qry = null;
        if (Database_tableNo.equals("collection1")) {
            qry = "select * from collection1";
        }
        if (Database_tableNo.equals("collection2")) {
            qry = "select * from collection2";
        }
        if (Database_tableNo.equals("collection3")) {
            qry = "select * from collection3";
        }
        if (Database_tableNo.equals("collection4")) {
            qry = "select * from collection4";
        }
        if (Database_tableNo.equals("collection5")) {
            qry = "select * from collection5";
        }
        if (Database_tableNo.equals("collection6")) {
            qry = "select * from collection6";
        }

        if (Database_tableNo.equals("collection7")) {
            qry = "select * from collection7";
        }
        if (Database_tableNo.equals("collection8")) {
            qry = "select * from collection8";
        }
        if (Database_tableNo.equals("collection9")) {
            qry = "select * from collection9";
        }

        if (Database_tableNo.equals("collection10")) {
            qry = "select * from collection10";
        }  if (Database_tableNo.equals("Audio_story_Category")) {
            qry = "select * from Audio_story_Category";

        }  if (Database_tableNo.equals("Audio_Story_1")) {
            qry = "select * from Audio_Story_1";
        }
        if (Database_tableNo.equals("Audio_Story_2")) {
            qry = "select * from Audio_Story_2";
        }
        if (Database_tableNo.equals("Audio_Story_3")) {
            qry = "select * from Audio_Story_3";
        }
        if (Database_tableNo.equals("UserInformation")) {
            qry = "select * from UserInformation";
        }

        if (Database_tableNo.equals("DB_VERSION")) {
            qry = "select * from DB_VERSION";
        }

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(qry, null);
        return cursor;
    }

    public  String updaterecord(int _id,int like_value) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Liked", like_value);

        float res= db.update(Database_tableNo, cv, "_id=" + _id, null);
        if(res==-1)
            return "Failed";
        else
            return  "Liked";

    }

    public String updaterecord_Login_Times(int _id, int login_Number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Login_Times", login_Number);

        float res = db.update(Database_tableNo, cv, "_id=" + _id, null);
        if (res == -1)
            return "Failed";
        else
            return "Sucess";

    }

    public String addAudioStoriesLinks(String coverImage,String collectionName,String season,String description, String ref, String table) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put("coverImage", coverImage);
        values.put("collectionName", collectionName);
        values.put("season", season);
        values.put("description", description);
        values.put("ref", ref);

        // after adding all values we are passing
        // content values to our table.
        float res = db.insert(table, null, values);
        if (res == -1)
            return "Failed "+table;
        else
            return "Sucess";
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }
}
