package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.constants.DatabaseConstants;

/**
 * Model for a staff.
 */
public class Staff implements Parcelable {
    private int mId;
    private String mName;
    private String mPlaceOfBirth;
    private String mBirthday;
    private String mPhoneNumber;
    private Status mStatus;
    private Position mPosition;
    private int mDepartmentId;

    public Staff() {

    }

    public Staff(String mName, String mPlaceOfBirth,
                 String mBirthday, String mPhoneNumber,
                 Status mStatus, Position mPosition, int mDepartmentId) {
        this.mName = mName;
        this.mPlaceOfBirth = mPlaceOfBirth;
        this.mBirthday = mBirthday;
        this.mPhoneNumber = mPhoneNumber;
        this.mStatus = mStatus;
        this.mPosition = mPosition;
        this.mDepartmentId = mDepartmentId;
    }

    public Staff(int mId, String mName, String mPlaceOfBirth,
                 String mBirthday, String mPhoneNumber,
                 Status mStatus, Position mPosition, int mDepartmentId) {
        this.mId = mId;
        this.mName = mName;
        this.mPlaceOfBirth = mPlaceOfBirth;
        this.mBirthday = mBirthday;
        this.mPhoneNumber = mPhoneNumber;
        this.mStatus = mStatus;
        this.mPosition = mPosition;
        this.mDepartmentId = mDepartmentId;
    }

    public Staff(Cursor cursor) {
        this.mId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_ID));
        this.mName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.STAFF_NAME));
        this.mPlaceOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.STAFF_POB));
        this.mBirthday = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.STAFF_BIRTHDAY));
        this.mPhoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.STAFF_PHONE));
        this.mDepartmentId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.STAFF_DEPARTMENT));
        this.mStatus = Status.getStatusFromCode(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.STAFF_STATUS)));
        this.mPosition = Position.getPositionFromCode(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.STAFF_POSITION)));
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

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public void setPlaceOfBirth(String mPlaceOfBirth) {
        this.mPlaceOfBirth = mPlaceOfBirth;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String mBirthday) {
        this.mBirthday = mBirthday;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status mStatus) {
        this.mStatus = mStatus;
    }

    public Position getPosition() {
        return mPosition;
    }

    public void setPosition(Position mPosition) {
        this.mPosition = mPosition;
    }

    public int getDepartmentId() {
        return mDepartmentId;
    }

    public void setDepartmentId(int mDepartmentId) {
        this.mDepartmentId = mDepartmentId;
    }

    protected Staff(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mPlaceOfBirth = in.readString();
        mBirthday = in.readString();
        mPhoneNumber = in.readString();
        mStatus = (Status) in.readValue(Status.class.getClassLoader());
        mPosition = (Position) in.readValue(Position.class.getClassLoader());
        mDepartmentId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mPlaceOfBirth);
        dest.writeString(mBirthday);
        dest.writeString(mPhoneNumber);
        dest.writeValue(mStatus);
        dest.writeValue(mPosition);
        dest.writeInt(mDepartmentId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Staff> CREATOR = new Parcelable.Creator<Staff>() {
        @Override
        public Staff createFromParcel(Parcel in) {
            return new Staff(in);
        }

        @Override
        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };
}