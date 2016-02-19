package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model;

/**
 * Defines all possible status of a staff
 */
public enum Status {
    CURRENT(0),
    LEFT(1);

    private int mCode;

    Status(int code) {
        this.mCode = code;
    }

    public static Status getStatusFromCode(final int code) {
        for (Status status : Status.values())
            if (status.mCode == code)
                return status;
        return null;
    }

    public static int getCodeFromStatus(final Status status) {
        return status.mCode;
    }
}