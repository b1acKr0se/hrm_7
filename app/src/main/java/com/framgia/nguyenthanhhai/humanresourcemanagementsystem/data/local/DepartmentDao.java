package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Department;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.constants.DatabaseConstants;

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
        contentValues.put(DatabaseConstants.COLUMN_NAME, department.getName());
        try {
            database.insertOrThrow(DatabaseConstants.TABLE_DEPARTMENT, null, contentValues);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Department> getDepartmentList() {
        List<Department> list = new ArrayList<>();
        Cursor cursor = database.query(DatabaseConstants.TABLE_DEPARTMENT,
                null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                list.add(new Department(cursor));
            }
        }
        cursor.close();
        return list;
    }
}