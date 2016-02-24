package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.util.List;

public class EditActivity extends BaseActivity implements View.OnClickListener {
    private static final int INVALID_ID = -1;
    private Toolbar mToolbar;
    private EditText mNameEditText;
    private EditText mBirthdayEditText;
    private EditText mPlaceOfBirthEditText;
    private EditText mPhoneEditText;
    private TextView mDepartmentNameTextView;
    private TextView mChangeDepartmentTextView;
    private RadioButton mPositionTraineeButton;
    private RadioButton mPositionInternButton;
    private RadioButton mPositionOfficialButton;
    private RadioButton mStatusActiveButton;
    private RadioButton mStatusLeftButton;
    private DepartmentDao mDepartmentDao = new DepartmentDao(this);
    private Staff mStaff;
    private Department mDepartment = null;
    private String mDepartmentName;
    private boolean mIsFromStaffActivity; //As the edit and new staff function share the
    //same activity in their startActivityForResult,
    //this boolean is to return the appropriate object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
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
                setResult(RESULT_CANCELED);
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setHomeAsUp();
        mNameEditText = (EditText) findViewById(R.id.edit_name);
        mBirthdayEditText = (EditText) findViewById(R.id.edit_dob);
        mPlaceOfBirthEditText = (EditText) findViewById(R.id.edit_pob);
        mPhoneEditText = (EditText) findViewById(R.id.edit_phone);
        mDepartmentNameTextView = (TextView) findViewById(R.id.text_department_name);
        mChangeDepartmentTextView = (TextView) findViewById(R.id.text_change_department_name);
        mPositionTraineeButton = (RadioButton) findViewById(R.id.radio_btn_trainee);
        mPositionInternButton = (RadioButton) findViewById(R.id.radio_btn_intern);
        mPositionOfficialButton = (RadioButton) findViewById(R.id.radio_btn_staff);
        mStatusActiveButton = (RadioButton) findViewById(R.id.radio_btn_active);
        mStatusLeftButton = (RadioButton) findViewById(R.id.radio_btn_left);
        mChangeDepartmentTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showDepartmentList();
    }

    private void getStaffFromIntent() {
        Intent intent = getIntent();
        mStaff = intent.getParcelableExtra(StaffActivity.EXTRA_STAFF);
        mDepartmentName = intent.getStringExtra(StaffActivity.EXTRA_DEPARTMENT_NAME);
        if (mStaff != null && mDepartmentName != null && mDepartmentName.isEmpty()) {
            mIsFromStaffActivity = true;
            showCurrentStaffDetail();
        }
        mIsFromStaffActivity = false;
    }

    private void showCurrentStaffDetail() {
        mNameEditText.setText(mStaff.getName());
        mBirthdayEditText.setText(mStaff.getBirthday());
        mPlaceOfBirthEditText.setText(mStaff.getPlaceOfBirth());
        mPhoneEditText.setText(mStaff.getPhoneNumber());
        mDepartmentNameTextView.setText(mDepartmentName);
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
        String[] departmentName = ArrayConversionUtil.toStringArray(departmentList);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.change_department));
        builder.setSingleChoiceItems(departmentName, INVALID_ID, new DialogInterface.OnClickListener() {
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
                || mBirthdayEditText.getText().toString().isEmpty()
                || mPlaceOfBirthEditText.getText().toString().isEmpty()
                || mPhoneEditText.getText().toString().isEmpty()
                || mDepartment == null
                || getCheckedPosition() == null
                || getCheckedStatus() == null) {
            Staff staff = new Staff();
            if (mIsFromStaffActivity) {
                staff.setId(mStaff.getId());
            }
            staff.setName(mNameEditText.getText().toString());
            staff.setBirthday(mBirthdayEditText.getText().toString());
            staff.setPlaceOfBirth(mPlaceOfBirthEditText.getText().toString());
            staff.setPhoneNumber(mPhoneEditText.getText().toString());
            staff.setDepartmentId(mDepartment.getId());
            staff.setPosition(getCheckedPosition());
            staff.setStatus(getCheckedStatus());
            returnIntent(staff); //return the result
        } else {
            showError(getString(R.string.error_empty_field));
        }
    }

    private void returnIntent(Staff staff) {
        Intent intent = new Intent();
        if (staff != null) {
            intent.putExtra(StaffActivity.EXTRA_STAFF, staff);
            setResult(RESULT_OK);
            finish();
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
