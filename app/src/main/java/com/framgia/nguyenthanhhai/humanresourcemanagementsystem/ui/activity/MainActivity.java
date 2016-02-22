package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.R;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.local.DepartmentDao;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Department;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.adapter.DepartmentAdapter;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.listener.OnDepartmentClickListener;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.widget.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, OnDepartmentClickListener {
    static final String EXTRA_DEPARTMENT_ID = "com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity.MainActivity.EXTRA_DEPARTMENT_ID";
    static final String EXTRA_DEPARTMENT_NAME = "com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity.MainActivity.EXTRA_DEPARTMENT_NAME";
    private Toolbar mToolbar;
    private RecyclerView mDepartmentRecyclerView;
    private FloatingActionButton mAddButton;
    private DepartmentAdapter mDepartmentAdapter;
    private List<Department> mDepartmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDepartment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_button_add:
                break;
        }
    }

    @Override
    public void onDepartmentClick(Department department) {
        startActivity(getStaffIntent(this, department));
    }

    @Override
    protected void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mAddButton = (FloatingActionButton) findViewById(R.id.floating_button_add);
        mAddButton.setOnClickListener(this);
        mDepartmentRecyclerView = (RecyclerView) findViewById(R.id.recycler_department);
        mDepartmentRecyclerView.setHasFixedSize(true);
        mDepartmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDepartmentRecyclerView.addItemDecoration(
                new SimpleDividerItemDecoration(this, null));
        mDepartmentAdapter = new DepartmentAdapter(this, mDepartmentList, this);
        mDepartmentRecyclerView.setAdapter(mDepartmentAdapter);
    }

    private void loadDepartment() {
        DepartmentDao departmentDao = new DepartmentDao(this);
        List<Department> departmentList = departmentDao.getDepartmentList();
        showDepartment(departmentList);
    }

    private void showDepartment(List<Department> departmentList) {
        mDepartmentList.clear();
        mDepartmentList.addAll(departmentList);
        mDepartmentAdapter.notifyDataSetChanged();
    }

    public static Intent getStaffIntent(Context context, Department department) {
        Intent intent = new Intent(context, StaffActivity.class);
        intent.putExtra(EXTRA_DEPARTMENT_ID, department.getId());
        intent.putExtra(EXTRA_DEPARTMENT_NAME, department.getName());
        return intent;
    }
}