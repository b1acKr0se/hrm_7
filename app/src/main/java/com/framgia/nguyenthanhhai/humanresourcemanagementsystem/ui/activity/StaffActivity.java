package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity;

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
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.listener.OnLoadMoreListener;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.listener.OnStaffClickListener;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.widget.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class StaffActivity extends BaseActivity implements OnStaffClickListener, OnLoadMoreListener {
    public static final int INVALID_ID = -1;
    private Toolbar mToolbar;
    private RecyclerView mStaffRecyclerView;
    private StaffAdapter mStaffAdapter;
    private List<Staff> mStaffList = new ArrayList<>();
    private StaffDao mStaffDao = new StaffDao(this);
    private int mDepartmentId;
    private String mDepartmentName;
    private int mOffset = 0; //for pagination of sql result

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
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
        mStaffAdapter = new StaffAdapter(this, mStaffList, this, mStaffRecyclerView);
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
        mStaffList.add(null); //dummy object to show progress bar
        mStaffAdapter.notifyItemInserted(mStaffList.size() - 1); //show progress bar
        List<Staff> staffList = mStaffDao.getStaffList(mDepartmentId, mOffset);
        if (staffList != null) {
            mOffset += 30; //increase page
            showMoreStaff(staffList);
        } else {
            //There's probably no staff remaining to be fetched so better remove the pagination listener
            mStaffAdapter.removePagination(mStaffRecyclerView);
        }
    }

    private void showMoreStaff(List<Staff> staffList) {
        mStaffList.remove(mStaffList.size() - 1);
        mStaffAdapter.notifyItemRemoved(mStaffList.size()); //remove progress bar
        mStaffAdapter.setLoaded();
        int startPosition = mStaffList.size() + 1;
        int size = staffList.size();
        mStaffList.addAll(staffList);
        mStaffAdapter.notifyItemRangeInserted(startPosition, size);
    }

    private void showStaffInfo(Staff staff) {
        StaffDetailDialog dialog = new StaffDetailDialog(this, staff, mDepartmentName);
        dialog.show();
    }
}