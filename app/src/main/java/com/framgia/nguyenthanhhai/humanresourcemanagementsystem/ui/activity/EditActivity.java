package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.R;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.local.DepartmentDao;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Department;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Position;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Staff;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Status;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.util.ArrayConversionUtil;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.util.DateConversionUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private static final int INVALID_ID = -1;
    private Toolbar mToolbar;
    private EditText mNameEditText;
    private EditText mPlaceOfBirthEditText;
    private EditText mPhoneEditText;
    private TextView mBirthdayTextView;
    private TextView mChangeBirthdayTextView;
    private TextView mDepartmentNameTextView;
    private TextView mChangeDepartmentTextView;
    private RadioButton mPositionTraineeButton;
    private RadioButton mPositionInternButton;
    private RadioButton mPositionOfficialButton;
    private RadioButton mStatusActiveButton;
    private RadioButton mStatusLeftButton;
    private DepartmentDao mDepartmentDao;
    private Staff mStaff;
    private Department mDepartment = null;
    private String mBirthdayString;
    private boolean mHasStaffDetails; //As the edit and new staff function share the
    //same activity in their startActivityForResult,
    //this boolean is to return the appropriate object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mDepartmentDao = new DepartmentDao(this);
        bindViews();
        getStaffFromIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_apply:
                validate();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setHomeAsUp();
        setTitle(getString(R.string.edit_staff));
        mNameEditText = (EditText) findViewById(R.id.edit_name);
        mPlaceOfBirthEditText = (EditText) findViewById(R.id.edit_pob);
        mPhoneEditText = (EditText) findViewById(R.id.edit_phone);
        mBirthdayTextView = (TextView) findViewById(R.id.text_staff_birthday);
        mChangeBirthdayTextView = (TextView) findViewById(R.id.text_change_birthday);
        mDepartmentNameTextView = (TextView) findViewById(R.id.text_department_name);
        mChangeDepartmentTextView = (TextView) findViewById(R.id.text_change_department_name);
        mPositionTraineeButton = (RadioButton) findViewById(R.id.radio_btn_trainee);
        mPositionInternButton = (RadioButton) findViewById(R.id.radio_btn_intern);
        mPositionOfficialButton = (RadioButton) findViewById(R.id.radio_btn_staff);
        mStatusActiveButton = (RadioButton) findViewById(R.id.radio_btn_active);
        mStatusLeftButton = (RadioButton) findViewById(R.id.radio_btn_left);
        mChangeDepartmentTextView.setOnClickListener(this);
        mChangeBirthdayTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_change_department_name:
                showDepartmentList();
                break;
            case R.id.text_change_birthday:
                showDatePickerDialog();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mBirthdayString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        if (mStaff != null) {
            mStaff.setBirthday(mBirthdayString);
        }
        mBirthdayTextView.setText(mBirthdayString);
        mBirthdayTextView.setVisibility(View.VISIBLE);
    }

    private void getStaffFromIntent() {
        Intent intent = getIntent();
        mStaff = intent.getParcelableExtra(StaffActivity.EXTRA_STAFF);
        // check if the parent activity wants to edit or add new staff
        // if there is a staff object attached with the intent, show the current details of that staff
        if (mStaff != null) {
            mHasStaffDetails = true;
            String departmentName = intent.getStringExtra(StaffActivity.EXTRA_DEPARTMENT_NAME);
            int departmentId = intent.getIntExtra(StaffActivity.EXTRA_DEPARTMENT_ID, INVALID_ID);
            if (departmentId != INVALID_ID) {
                mDepartment = new Department(departmentId, departmentName);
            }
            mBirthdayString = mStaff.getBirthday();
            showCurrentStaffDetail();
        } else {
            mHasStaffDetails = false;
        }
    }

    private void showCurrentStaffDetail() {
        mNameEditText.setText(mStaff.getName());
        mBirthdayTextView.setText(mStaff.getBirthday());
        mBirthdayTextView.setVisibility(View.VISIBLE);
        mPlaceOfBirthEditText.setText(mStaff.getPlaceOfBirth());
        mPhoneEditText.setText(mStaff.getPhoneNumber());
        mDepartmentNameTextView.setText(mDepartment.getName());
        mDepartmentNameTextView.setVisibility(View.VISIBLE);
        switch (mStaff.getPosition()) {
            case TRAINEE:
                mPositionTraineeButton.setChecked(true);
                break;
            case INTERNSHIP:
                mPositionInternButton.setChecked(true);
                break;
            case OFFICIAL_STAFF:
                mPositionOfficialButton.setChecked(true);
                break;
        }
        switch (mStaff.getStatus()) {
            case CURRENT:
                mStatusActiveButton.setChecked(true);
                break;
            case LEFT:
                mStatusLeftButton.setChecked(true);
                break;
        }
    }

    private void showDepartmentList() {
        final List<Department> departmentList = mDepartmentDao.getDepartmentList();
        String[] departmentName = ArrayConversionUtil.toDepartmentNameArray(departmentList);
        int checkedPosition = INVALID_ID;
        if(mDepartment != null) {
        //we loop through the list of the department, find the existing department index
        //and use it to check the radio button
            for (int i = 0; i < departmentList.size(); i++) {
                if (mDepartment.getId() == departmentList.get(i).getId()) {
                    checkedPosition = i;
                    break;
                }
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.change_department));
        builder.setSingleChoiceItems(departmentName, checkedPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateDepartmentName(departmentList.get(which));
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void updateDepartmentName(Department department) {
        mDepartment = department;
        mDepartmentNameTextView.setText(mDepartment.getName());
        mDepartmentNameTextView.setVisibility(View.VISIBLE);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year, month, day;
        if (mStaff == null) {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            Date date = DateConversionUtil.getDateFromString(mStaff.getBirthday());
            calendar.setTime(date);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dialog = new DatePickerDialog(this, this, year, month, day);
        dialog.show();
    }

    private Position getCheckedPosition() {
        if (mPositionTraineeButton.isChecked()) {
            return Position.TRAINEE;
        } else if (mPositionInternButton.isChecked()) {
            return Position.INTERNSHIP;
        } else if (mPositionOfficialButton.isChecked()) {
            return Position.OFFICIAL_STAFF;
        } else {
            return null;
        }
    }

    private Status getCheckedStatus() {
        if (mStatusActiveButton.isChecked()) {
            return Status.CURRENT;
        } else if (mStatusLeftButton.isChecked()) {
            return Status.LEFT;
        } else {
            return null;
        }
    }

    private void validate() {
        if (mNameEditText.getText().toString().isEmpty()
                || mPlaceOfBirthEditText.getText().toString().isEmpty()
                || mPhoneEditText.getText().toString().isEmpty()
                || mBirthdayString == null
                || mDepartment == null
                || getCheckedPosition() == null
                || getCheckedStatus() == null) {
            showMessage(getString(R.string.error_empty_field));
        } else {
            Staff staff = new Staff();
            if (mHasStaffDetails) {
                staff.setId(mStaff.getId());
            }
            staff.setName(mNameEditText.getText().toString());
            staff.setBirthday(mBirthdayString);
            staff.setPlaceOfBirth(mPlaceOfBirthEditText.getText().toString());
            staff.setPhoneNumber(mPhoneEditText.getText().toString());
            staff.setDepartmentId(mDepartment.getId());
            staff.setPosition(getCheckedPosition());
            staff.setStatus(getCheckedStatus());
            returnIntent(staff); //return the result
        }
    }

    private void returnIntent(Staff staff) {
        Intent intent = new Intent();
        if (staff != null) {
            intent.putExtra(StaffActivity.EXTRA_STAFF, staff);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
