package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.R;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.widget.SimpleDividerItemDecoration;

public class StaffActivity extends BaseActivity {
    private Toolbar mToolbar;
    private RecyclerView mStaffRecyclerView;

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
    }

    private void retrieveIntent() {
        Intent intent = getIntent();
        int departmentId = intent.getIntExtra(MainActivity.EXTRA_DEPARTMENT_ID, -1);
        String departmentName = intent.getStringExtra(MainActivity.EXTRA_DEPARTMENT_NAME);
        setTitle(departmentName);
    }
}
