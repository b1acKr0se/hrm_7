package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model;

/**
 * Defines all the positions in a company..
 */
public enum Position {
    TRAINEE(0),
    INTERNSHIP(1),
    OFFICIAL_STAFF(2);

    private int mCode;

    Position(int code) {
        this.mCode = code;
    }

    public static Position getPositionFromCode(int code) {
        for (Position position : Position.values())
            if (position.mCode == code)
                return position;
        return null;
    }

    public static int getCodeFromPosition(final Position position) {
        return position.mCode;
    }
}