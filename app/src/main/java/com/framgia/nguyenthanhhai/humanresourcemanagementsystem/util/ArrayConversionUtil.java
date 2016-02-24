package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.util;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Department;

import java.util.List;

public class ArrayConversionUtil {
    public static String[] toStringArray(List<Department> list) {
        String[] string = new String[list.size()];
        for (int i = 0; i< list.size(); i++) {
            string[i] = list.get(i).getName();
        }
        return string;
    }
}
