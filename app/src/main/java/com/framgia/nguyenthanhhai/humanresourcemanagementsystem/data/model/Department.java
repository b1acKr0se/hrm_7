package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model;

/**
 * Model for a department.
 */
public class Department {
    private int mId;
    private String mName;

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