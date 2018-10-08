package ru.startandroid.yogaapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Настик on 05.10.2018.
 */

public class YogaDB extends SQLiteAssetHelper {

    private static final String DB_NAME = "Yoga.db";
    private static final int DB_VER = 1;

    public YogaDB(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public int getSettingMode() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"Mode"};
        String sqlTable = "Setting";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("Mode"));


    }

    public void saveSettingMode(int value) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE Setting SET Mode = " +value;
        db.execSQL(query);
    }




}
