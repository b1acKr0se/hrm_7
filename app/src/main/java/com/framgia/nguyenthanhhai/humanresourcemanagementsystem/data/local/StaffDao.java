package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Position;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Staff;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Status;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.constants.DatabaseConstants;

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
        contentValues.put(DatabaseConstants.COLUMN_NAME, staff.getName());
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

    public List<Staff> getStaffList(int departmentId) {
        List<Staff> list = new ArrayList<>();
        String selection = DatabaseConstants.STAFF_DEPARTMENT + " = ?";
        String[] selectionArgs = {String.valueOf(departmentId)};
        Cursor cursor = database.query(DatabaseConstants.TABLE_STAFF,
                null, selection, selectionArgs, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                list.add(new Staff(cursor));
            }
            cursor.close();
            return list;
        }
        cursor.close();
        return null;
    }
}