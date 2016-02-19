package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.constants;

public class DatabaseConstants {
    public static final String DATABASE_NAME = "hr_management.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_STAFF = "Staff";
    public static final String TABLE_DEPARTMENT = "Department";
    //common columns
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    //table staff columns
    public static final String STAFF_POB = "pob";
    public static final String STAFF_BIRTHDAY = "birthday";
    public static final String STAFF_PHONE = "phone_number";
    public static final String STAFF_STATUS = "status";
    public static final String STAFF_POSITION = "position";
    public static final String STAFF_DEPARTMENT = "department_id";
    public static final String CREATE_TABLE_STAFF = "CREATE TABLE " + TABLE_STAFF
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, " + STAFF_POB + " TEXT, " + STAFF_BIRTHDAY + " TEXT, "
            + STAFF_PHONE + " TEXT, " + STAFF_STATUS + " INTEGER, " + STAFF_POSITION + " INTEGER, "
            + STAFF_DEPARTMENT + " INTEGER)";
    public static final String CREATE_TABLE_DEPARTMENT = "CREATE TABLE " + TABLE_DEPARTMENT
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT)";
}
