package com.bhola.english_ghoststory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper2 extends SQLiteOpenHelper {
    String DbName;
    String DbPath;
    Context context;
    String Database_tableNo;
    Cursor cursor;

//    When Deleting story from  Favourite_list "Database_tableNo" act as "Story Title"

    public DatabaseHelper2(@Nullable Context mcontext, String DB_NAME, int version, String Table_Number) {
        super(mcontext, DB_NAME, null, version);
        this.context = mcontext;
        this.DbName = DB_NAME;
        this.Database_tableNo = Table_Number;
        DbPath = "/data/data/" + context.getOpPackageName() + "/databases/";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CheckDatabases() {
        try {
            String path = DbPath + DbName;
            SQLiteDatabase.openDatabase(path, null, 0);

        } catch (Exception e) {
            this.getReadableDatabase();
//            CopyDatabases();

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


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void OpenDatabase() {
        String path = DbPath + DbName;
        SQLiteDatabase.openDatabase(path, null, 0);

    }

    public void db_delete() {
        File file = new File(DbPath + DbName);
        if (file.exists()) {
            file.delete();
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
        }

        if (Database_tableNo.equals("category")) {
            qry = "select * from category";
        }

        SQLiteDatabase db = this.getWritableDatabase();

        cursor = db.rawQuery(qry, null);
        return cursor;

    }


    public String addrecord(String Date, String Heading, String Title) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("Date", Date);
        cv.put("Heading", Heading);
        cv.put("Title", Title);

        float res = db.insert("Collection1", null, cv);

        if (res == -1)
            return "Failed";
        else
            return "Successfully inserted";

    }

    public String updaterecord(int _id, int like_value) {



        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Liked", like_value);

        float res= db.update(Database_tableNo, cv, "_id=" + _id, null);
        if(res==-1)
            return "Failed";
        else
            return  "Liked";
    }

    public Cursor read_Liked_data() {
        String qry = null;

        SQLiteDatabase db = this.getWritableDatabase();


        qry = "SELECT * FROM Table_Name WHERE Col_Name='Liked'";
        Cursor cursor = db.rawQuery(qry, null);
        return cursor;

    }

}
