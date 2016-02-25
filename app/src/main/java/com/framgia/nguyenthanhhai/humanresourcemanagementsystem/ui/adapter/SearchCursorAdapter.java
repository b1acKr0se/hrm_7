package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.R;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Staff;

public class SearchCursorAdapter extends android.support.v4.widget.CursorAdapter {

    public SearchCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public int getCount() {
        if (getCursor() == null) {
            return 0;
        }
        return super.getCount();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView staffNameTextView = (TextView) view.findViewById(R.id.text_search_result);
        Staff staff = new Staff(cursor);
        staffNameTextView.setText(staff.getName());
    }
}
