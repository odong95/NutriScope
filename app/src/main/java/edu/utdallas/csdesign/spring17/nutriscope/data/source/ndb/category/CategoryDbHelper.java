package edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.category;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * Created by john on 3/23/17.
 */

public class CategoryDbHelper extends SQLiteOpenHelper {
    Context context;
    public final static String dbName = "category";
    public final static String dbCol0 = "_id";
    public final static String dbCol1 = "category";

    public CategoryDbHelper(Context context) {
        super(context, null, null, 1);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + dbName + "(" +
                    "_id text primary key, " +
                    dbCol1 + " text)");


        GZIPInputStream is = null;

        try {
            is = new GZIPInputStream(context.getAssets().open("category.jpg"));

            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            String line = "";
            db.beginTransaction();
            try {
                while ((line = buffer.readLine()) != null) {
                    String[] colums = line.split("\t");
                    if (colums.length != 2) {
                        Log.d("CSVParser", "Skipping Bad CSV Row");
                        continue;
                    }
                 //   Log.d("parsing", line);
                    ContentValues cv = new ContentValues(2);
                    cv.put(dbCol0, colums[0].trim());
                    cv.put(dbCol1, colums[1].trim());
                    db.insert(dbName, null, cv);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.setTransactionSuccessful();
            db.endTransaction();


            buffer.close();


        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}