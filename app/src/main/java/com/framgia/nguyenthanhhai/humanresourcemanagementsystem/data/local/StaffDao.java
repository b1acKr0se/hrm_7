package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.constants.DatabaseConstants;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Position;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Staff;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Handle the staff table
 */
public class StaffDao extends DbContentProvider {

    public StaffDao(Context context) {
        super(context);
    }

    public boolean insertStaff(Staff staff) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstants.STAFF_NAME, staff.getName());
        contentValues.put(DatabaseConstants.STAFF_POB, staff.getPlaceOfBirth());
        contentValues.put(DatabaseConstants.STAFF_BIRTHDAY, staff.getBirthday());
        contentValues.put(DatabaseConstants.STAFF_DEPARTMENT, staff.getDepartmentId());
        contentValues.put(DatabaseConstants.STAFF_PHONE, staff.getPhoneNumber());
        //set the appropriate position
        //Trainee is indiated by 0, Internship is 1, Official Staff is 2
        contentValues.put(DatabaseConstants.STAFF_POSITION, Position.getCodeFromPosition(staff.getPosition()));
        //set the appropriate status, when current staff is indicated by 0 and left staff is 1
        contentValues.put(DatabaseConstants.STAFF_STATUS, Status.getCodeFromStatus(staff.getStatus()));
        try {
            database.insertOrThrow(DatabaseConstants.TABLE_STAFF, null, contentValues);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateStaff(int id, Staff staff) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstants.STAFF_NAME, staff.getName());
        contentValues.put(DatabaseConstants.STAFF_POB, staff.getPlaceOfBirth());
        contentValues.put(DatabaseConstants.STAFF_BIRTHDAY, staff.getBirthday());
        contentValues.put(DatabaseConstants.STAFF_DEPARTMENT, staff.getDepartmentId());
        contentValues.put(DatabaseConstants.STAFF_PHONE, staff.getPhoneNumber());
        contentValues.put(DatabaseConstants.STAFF_POSITION, Position.getCodeFromPosition(staff.getPosition()));
        contentValues.put(DatabaseConstants.STAFF_STATUS, Status.getCodeFromStatus(staff.getStatus()));
        int rowsAffected = database.update(DatabaseConstants.TABLE_STAFF, contentValues
                , DatabaseConstants.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }

    public Cursor searchStaff(String condition) {
        String selection = DatabaseConstants.STAFF_NAME + " LIKE '%" + condition + "%'"
                + " OR " + DatabaseConstants.STAFF_PHONE + " LIKE '%" + condition + "%'";
        Cursor cursor = database.query(true, DatabaseConstants.TABLE_STAFF,
                null, selection, null, null, null, null, null);
        // we first search in table Staff to check if there is any record that
        // has the name and phone in the query
        if (cursor != null && cursor.getCount() > 0) {
            return cursor;
        } else {
            // no Staff who has name or phone matched the query, so we search
            // for the department name.
            if (getStaffListByDepartmentName(condition) != null) {
                return getStaffListByDepartmentName(condition);
            }
            return cursor;
        }
    }

    public Cursor getStaffListByDepartmentName(String name) {
        String innerJoin = DatabaseConstants.TABLE_STAFF + " INNER JOIN " + DatabaseConstants.TABLE_DEPARTMENT
                + " ON " + DatabaseConstants.TABLE_STAFF + "." + DatabaseConstants.STAFF_DEPARTMENT + " = "
                + DatabaseConstants.TABLE_DEPARTMENT + "." + DatabaseConstants.COLUMN_ID;
        String where = DatabaseConstants.TABLE_DEPARTMENT + "." + DatabaseConstants.DEPARTMENT_NAME + " LIKE '%" + name + "%'";
        Cursor cursor = database.query(innerJoin,
                null, where, null, null, null, null, null);
        return cursor;
    }

    public Staff getStaff(int id) {
        String selection = DatabaseConstants.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = database.query(DatabaseConstants.TABLE_STAFF,
                null, selection, selectionArgs, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Staff staff = new Staff(cursor);
            cursor.close();
            return staff;
        }
        return null;
    }

    public List<Staff> getStaffList(int departmentId, int offset) {
        List<Staff> list = new ArrayList<>();
        String selection = DatabaseConstants.STAFF_DEPARTMENT + " = ?";
        String[] selectionArgs = {String.valueOf(departmentId)};
        String orderQuery = "_id asc";
        String paginationQuery = offset + ",30";
        Cursor cursor = database.query(DatabaseConstants.TABLE_STAFF,
                null, selection, selectionArgs, null, null, orderQuery, paginationQuery);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                list.add(new Staff(cursor));
            }
            cursor.close();
            return list;
        }
        return null;
    }
}