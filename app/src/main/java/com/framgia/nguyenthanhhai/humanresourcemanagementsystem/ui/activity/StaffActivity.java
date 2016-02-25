package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.R;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.local.StaffDao;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Staff;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.adapter.StaffAdapter;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.dialog.StaffDetailDialog;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.listener.OnEditClickListener;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.listener.OnLoadMoreListener;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.listener.OnStaffClickListener;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.widget.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class StaffActivity extends BaseActivity
        implements OnStaffClickListener, OnLoadMoreListener, OnEditClickListener {
    static final String EXTRA_STAFF = "com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity.EXTRA_STAFF";
    static final String EXTRA_DEPARTMENT_ID = "com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity.EXTRA_DEPARTMENT_ID";
    static final String EXTRA_DEPARTMENT_NAME = "com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity.EXTRA_DEPARTMENT_NAME";
    static final int EDIT_STAFF_REQUEST = 1;
    public static final int INVALID_ID = -1;
    private Toolbar mToolbar;
    private RecyclerView mStaffRecyclerView;
    private StaffAdapter mStaffAdapter;
    private List<Staff> mStaffList = new ArrayList<>();
    private StaffDao mStaffDao;
    private static int mDepartmentId;
    private static String mDepartmentName;
    private int mOffset = 0; //for pagination of sql result
    private int mIsBeingEditedIndex; //stores the index of the staff that is being
    // edited so we can update it later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        mStaffDao = new StaffDao(this);
        bindViews();
        retrieveIntent();
    }

    @Override
    protected void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setHomeAsUp();
        mStaffRecyclerView = (RecyclerView) findViewById(R.id.recycler_staff);
        mStaffRecyclerView.setHasFixedSize(true);
        mStaffRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStaffRecyclerView.addItemDecoration(
                new SimpleDividerItemDecoration(this, null));
        mStaffAdapter = new StaffAdapter(this, mStaffList, mStaffRecyclerView);
        mStaffAdapter.setOnStaffClickListener(this);
        mStaffAdapter.setOnLoadMoreListener(this);
        mStaffRecyclerView.setAdapter(mStaffAdapter);
    }

    @Override
    public void onLoadMore() {
        if (mDepartmentId != INVALID_ID) {
            loadMoreStaff();
        }
    }

    @Override
    public void onStaffClick(Staff staff) {
        showStaffInfo(staff);
    }

    @Override
    public void onEditClick(Staff staff) {
        mIsBeingEditedIndex = mStaffList.indexOf(staff);
        startActivityForResult(getEditIntent(this, staff), EDIT_STAFF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode != EDIT_STAFF_REQUEST) {
                return;
            }
            Staff staff = data.getParcelableExtra(EXTRA_STAFF);
            if (mStaffDao.updateStaff(staff.getId(), staff)) {
                if (mStaffList.get(mIsBeingEditedIndex).getDepartmentId() != staff.getDepartmentId()) {
                    mStaffList.remove(mIsBeingEditedIndex);
                    mStaffAdapter.notifyItemRemoved(mIsBeingEditedIndex);
                } else {
                    mStaffList.set(mIsBeingEditedIndex, staff);
                    mStaffAdapter.notifyItemChanged(mIsBeingEditedIndex);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void retrieveIntent() {
        Intent intent = getIntent();
        mDepartmentId = intent.getIntExtra(MainActivity.EXTRA_DEPARTMENT_ID, INVALID_ID);
        mDepartmentName = intent.getStringExtra(MainActivity.EXTRA_DEPARTMENT_NAME);
        setTitle(mDepartmentName);
        if (mDepartmentId != INVALID_ID) {
            loadStaff();
        }
    }

    private void loadStaff() {
        List<Staff> staffList = mStaffDao.getStaffList(mDepartmentId, mOffset);
        mOffset += 30;
        if (staffList != null) {
            showStaff(staffList);
        }
    }

    private void showStaff(List<Staff> staffList) {
        mStaffList.clear();
        mStaffList.addAll(staffList);
        mStaffAdapter.notifyDataSetChanged();
    }

    private void loadMoreStaff() {
        List<Staff> staffList = mStaffDao.getStaffList(mDepartmentId, mOffset);
        if (staffList != null) {
            mOffset += 30; //increase page
            showMoreStaff(staffList);
        } else {
            //There's probably no staff remaining to be fetched so better remove the pagination listener
            mStaffAdapter.setLoaded();
            mStaffAdapter.removePagination(mStaffRecyclerView);
        }
    }

    private void showMoreStaff(List<Staff> staffList) {
        mStaffAdapter.setLoaded();
        int startPosition = mStaffList.size() + 1;
        int size = staffList.size();
        mStaffList.addAll(staffList);
        mStaffAdapter.notifyItemRangeInserted(startPosition, size);
    }

    private void showStaffInfo(Staff staff) {
        mDepartmentId = staff.getDepartmentId();
        StaffDetailDialog dialog = new StaffDetailDialog(this, staff, mDepartmentName);
        dialog.setOnEditListener(this);
        dialog.show();
    }

    public static Intent getEditIntent(Context context, Staff staff) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(EXTRA_STAFF, staff);
        intent.putExtra(EXTRA_DEPARTMENT_ID, mDepartmentId);
        intent.putExtra(EXTRA_DEPARTMENT_NAME, mDepartmentName);
        return intent;
    }
}