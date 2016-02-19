package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model;


/**
 * Model for a staff.
 */
public class Staff {

    private int mId;
    private String mName;
    private String mPlaceOfBirth;
    private String mBirthday;
    private String mPhoneNumber;
    private Status mStatus;
    private Position mPosition;
    private int mDepartmentId;

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
}