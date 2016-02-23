package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.R;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Staff;

/**
 * Dialog that shows staff information
 */
public class StaffDetailDialog extends Dialog implements View.OnClickListener {
    private Staff mStaff;
    private String mDepartmentName;
    private TextView mStaffNameTextView;
    private TextView mStaffBirthdayTextView;
    private TextView mStaffPobTextView;
    private TextView mStaffPhoneTextView;
    private TextView mStaffDepartmentTextView;
    private TextView mStaffStatusTextView;
    private TextView mStaffPositionTextView;
    private Button mOkButton;

    public StaffDetailDialog(Context context) {
        super(context);
    }

    public StaffDetailDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected StaffDetailDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public StaffDetailDialog(Context context, Staff staff, String departmentName) {
        super(context);
        this.mStaff = staff;
        this.mDepartmentName = departmentName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_staff_info);
        bindViews();
        setDetail();
    }

    private void bindViews() {
        mStaffNameTextView = (TextView) findViewById(R.id.text_staff_name);
        mStaffBirthdayTextView = (TextView) findViewById(R.id.text_staff_birthday);
        mStaffPobTextView = (TextView) findViewById(R.id.text_staff_pob);
        mStaffPhoneTextView = (TextView) findViewById(R.id.text_staff_phone);
        mStaffDepartmentTextView = (TextView) findViewById(R.id.text_staff_department);
        mStaffStatusTextView = (TextView) findViewById(R.id.text_staff_status);
        mStaffPositionTextView = (TextView) findViewById(R.id.text_staff_position);
        mOkButton = (Button) findViewById(R.id.btn_ok);
    }

    private void setDetail() {
        mStaffNameTextView.setText(mStaff.getName());
        mStaffBirthdayTextView.setText(mStaff.getBirthday());
        mStaffPobTextView.setText(mStaff.getPlaceOfBirth());
        mStaffPhoneTextView.setText(mStaff.getPhoneNumber());
        mStaffDepartmentTextView.setText(mDepartmentName);
        mStaffPositionTextView.setText(mStaff.getPosition().toString());
        mStaffStatusTextView.setText(mStaff.getStatus().toString());
        mOkButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
