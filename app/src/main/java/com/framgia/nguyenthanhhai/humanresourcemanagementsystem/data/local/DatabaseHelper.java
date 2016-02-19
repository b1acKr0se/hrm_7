package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.constants.DatabaseConstants;

/**
 * Helper class to manage staff database
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper mDatabaseInstance;

    public DatabaseHelper(Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getDatabaseHelper(Context context) {
        if (mDatabaseInstance == null)
            mDatabaseInstance = new DatabaseHelper(context);
        return mDatabaseInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create necessary tables
        db.execSQL(DatabaseConstants.CREATE_TABLE_STAFF);
        db.execSQL(DatabaseConstants.CREATE_TABLE_DEPARTMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop existing tables
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_STAFF);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_DEPARTMENT);
        //Recreate tables
        onCreate(db);
    }
}