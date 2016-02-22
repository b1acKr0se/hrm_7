package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.R;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity.BaseActivity;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.widget.SimpleDividerItemDecoration;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private RecyclerView mDepartmentRecyclerView;
    private FloatingActionButton mAddButton;

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_button_add:
                break;
        }
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mAddButton = (FloatingActionButton) findViewById(R.id.floating_button_add);
        mAddButton.setOnClickListener(this);
        mDepartmentRecyclerView = (RecyclerView) findViewById(R.id.recycler_department);
        mDepartmentRecyclerView.setHasFixedSize(true);
        mDepartmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDepartmentRecyclerView.addItemDecoration(
                new SimpleDividerItemDecoration(this, null));
    }
}