// MIT License
// Copyright (c) 2017 SNU_ARTOON TEAM

package com.snu_artoon.arwebtoonplayer.DBManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBManager {
    private DBOpenHelper dbOpenHelper;

    public DBManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
        copyDataBase(context);
    }

    /**
     * Copy the asset database to working database.
     * @param context context to be run
     */
    private void copyDataBase(Context context) {
        try {
            InputStream inputDB = context.getAssets().open("metadata");
            String outDBPath = context.getDatabasePath("metadata").getPath();
            OutputStream outputDB = new FileOutputStream(outDBPath);
            byte[] buffer = new byte[1024];
            int readLength;
            while ((readLength = inputDB.read(buffer)) > 0){
                outputDB.write(buffer, 0, readLength);
            }
            outputDB.flush();
            outputDB.close();
            inputDB.close();
        } catch (IOException e) { }
    }

    /**
     * Return the Cursor of the column from the given table.
     * @param column the column value to find
     * @param tableName table name to find the column
     * @return the cursor of the column of the given table.
     */
    public Cursor get(String column, String tableName) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        return db.rawQuery("SELECT " + column + " FROM " + tableName, null);
    }

    /**
     * Close the dbOpenHelper..
     */
    public void close() {
        dbOpenHelper.close();
    }


}

class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(Context context) {
        super(context, "metadata", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}