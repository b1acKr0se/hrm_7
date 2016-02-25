package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConversionUtil {
    public static Date getDateFromString(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date parsedDate;
        try {
            parsedDate = formatter.parse(date);
        } catch (ParseException exception) {
            return new Date();
        }
        return parsedDate;
    }
}
