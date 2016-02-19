package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model;

import android.database.Cursor;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.constants.DatabaseConstants;

/**
 * Model for a department.
 */
public class Department {
    private int mId;
    private String mName;

    public Department(Cursor cursor) {
        this.mId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_ID));
        this.mName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_NAME));
    }

    public Department(String mName) {
        this.mName = mName;
    }

    public Department(int mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}