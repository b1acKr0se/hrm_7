package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.constants.DatabaseConstants;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Handle department table
 */
public class DepartmentDao extends DbContentProvider {

    public DepartmentDao(Context context) {
        super(context);
    }

    public boolean addDepartment(Department department) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstants.DEPARTMENT_NAME, department.getName());
        try {
            database.insertOrThrow(DatabaseConstants.TABLE_DEPARTMENT, null, contentValues);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getDepartmentName(int id) {
        String selection = DatabaseConstants.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = database.query(DatabaseConstants.TABLE_DEPARTMENT,
                null, selection, selectionArgs, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.DEPARTMENT_NAME));
            cursor.close();
            return name;
        }
        return "";
    }

    public List<Department> getDepartmentList() {
        List<Department> list = new ArrayList<>();
        Cursor cursor = database.query(DatabaseConstants.TABLE_DEPARTMENT,
                null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                list.add(new Department(cursor));
            }
            cursor.close();
        }
        return list;
    }
}