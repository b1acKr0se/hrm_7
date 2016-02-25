package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.R;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.local.DepartmentDao;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.local.StaffDao;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Department;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Position;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Staff;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Status;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.adapter.DepartmentAdapter;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.adapter.SearchCursorAdapter;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.dialog.StaffDetailDialog;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.listener.OnDepartmentClickListener;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.listener.OnEditClickListener;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.widget.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        OnDepartmentClickListener, OnEditClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    static final String EXTRA_STAFF = "com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity.EXTRA_STAFF";
    static final String EXTRA_DEPARTMENT_ID = "com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity.EXTRA_DEPARTMENT_ID";
    static final String EXTRA_DEPARTMENT_NAME = "com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity.EXTRA_DEPARTMENT_NAME";
    static final int EDIT_STAFF_REQUEST = 1;
    static final int ADD_STAFF_REQUEST = 2;
    static final int LOADER_ID = 1;
    private Toolbar mToolbar;
    private RecyclerView mDepartmentRecyclerView;
    private FloatingActionButton mAddButton;
    private DepartmentAdapter mDepartmentAdapter;
    private List<Department> mDepartmentList = new ArrayList<>();
    private SearchCursorAdapter mResultAdapter;
    private StaffDao mStaffDao;
    private DepartmentDao mDepartmentDao;
    private String mQuery;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStaffDao = new StaffDao(this);
        mDepartmentDao = new DepartmentDao(this);
        mResultAdapter = new SearchCursorAdapter(this, null);
        LoaderManager loaderManager = this.getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);
        bindViews();
        createDummyData();
    }

    private void createDummyData() {
        SharedPreferences sharedpreferences = getSharedPreferences("DummyData", Context.MODE_PRIVATE);
        boolean isInserted = sharedpreferences.getBoolean("inserted", false);
        if (!isInserted) {
            mDepartmentDao.addDepartment(new Department("Division 1"));
            mDepartmentDao.addDepartment(new Department("Division 2"));
            mStaffDao.insertStaff(new Staff("Bruce Wayne", "America", "25/12/2000", "043543543", Status.CURRENT, Position.INTERNSHIP, 1));
            mStaffDao.insertStaff(new Staff("Peter Parker", "America", "25/12/2000", "043543543", Status.CURRENT, Position.INTERNSHIP, 2));
            mStaffDao.insertStaff(new Staff("Clark Kent", "America", "25/12/2000", "043543543", Status.LEFT, Position.TRAINEE, 1));
            mStaffDao.insertStaff(new Staff("Wade Wilson", "America", "25/12/2000", "043543543", Status.LEFT, Position.INTERNSHIP, 2));
            mStaffDao.insertStaff(new Staff("Winter Soldier", "America", "25/12/2000", "043543543", Status.CURRENT, Position.INTERNSHIP, 1));
            mStaffDao.insertStaff(new Staff("Black Widow", "America", "25/12/2000", "043543543", Status.CURRENT, Position.OFFICIAL_STAFF, 2));
            for (int i = 0; i < 200; i++) {
                if (i % 2 == 0)
                    mStaffDao.insertStaff(new Staff("Winter Soldier", "America", "25/12/2000", "043543543", Status.CURRENT, Position.INTERNSHIP, 1));
                else
                    mStaffDao.insertStaff(new Staff("Wonder Woman", "Canada", "21/01/1900", "04353443", Status.LEFT, Position.TRAINEE, 1));
            }
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("inserted", true);
            editor.apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.btn_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        int autoCompleteTextViewID = getResources().getIdentifier("search_src_text", "id", getPackageName());
        AutoCompleteTextView searchAutoCompleteTextView = (AutoCompleteTextView) mSearchView.findViewById(autoCompleteTextViewID);
        searchAutoCompleteTextView.setThreshold(1);
        mSearchView.setSuggestionsAdapter(mResultAdapter);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    mQuery = newText;
                    getSupportLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);
                }
                return true;
            }
        });
        mSearchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                showStaffInfo(new Staff((Cursor) mResultAdapter.getItem(position)));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDepartment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Staff staff = data.getParcelableExtra(EXTRA_STAFF);
            switch (requestCode) {
                case ADD_STAFF_REQUEST:
                    if (mStaffDao.insertStaff(staff)) {
                        showMessage(getString(R.string.successful_add_staff));
                    }
                    break;
                case EDIT_STAFF_REQUEST:
                    if (mStaffDao.updateStaff(staff.getId(), staff)) {
                        showMessage(getString(R.string.successful_update_staff));
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_button_add:
                startActivityForResult(getAddIntent(this), ADD_STAFF_REQUEST);
                break;
        }
    }

    @Override
    public void onEditClick(Staff staff) {
        startActivityForResult(getEditIntent(this, staff, staff.getDepartmentId(),
                mDepartmentDao.getDepartmentName(staff.getDepartmentId())), EDIT_STAFF_REQUEST);
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
        List<Department> departmentList = mDepartmentDao.getDepartmentList();
        showDepartment(departmentList);
    }

    private void showDepartment(List<Department> departmentList) {
        mDepartmentList.clear();
        mDepartmentList.addAll(departmentList);
        mDepartmentAdapter.notifyDataSetChanged();
    }

    private void showStaffInfo(Staff staff) {
        StaffDetailDialog dialog = new StaffDetailDialog(this, staff, mDepartmentDao.getDepartmentName(staff.getDepartmentId()));
        dialog.setOnEditListener(this);
        dialog.show();
    }

    public static Intent getStaffIntent(Context context, Department department) {
        Intent intent = new Intent(context, StaffActivity.class);
        intent.putExtra(EXTRA_DEPARTMENT_ID, department.getId());
        intent.putExtra(EXTRA_DEPARTMENT_NAME, department.getName());
        return intent;
    }

    public static Intent getAddIntent(Context context) {
        return new Intent(context, EditActivity.class);
    }

    public static Intent getEditIntent(Context context, Staff staff, int departmentId, String departmentName) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(EXTRA_STAFF, staff);
        intent.putExtra(EXTRA_DEPARTMENT_ID, departmentId);
        intent.putExtra(EXTRA_DEPARTMENT_NAME, departmentName);
        return intent;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new ResultLoader(this, mQuery, mStaffDao);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mResultAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mResultAdapter.swapCursor(null);
    }

    private static class ResultLoader extends AsyncTaskLoader<Cursor> {
        private String mQuery;
        private StaffDao mStaffDao;

        public ResultLoader(Context context, String query, StaffDao staffDao) {
            super(context);
            mQuery = query;
            mStaffDao = staffDao;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            this.forceLoad();
        }

        @Override
        public Cursor loadInBackground() {
            return mStaffDao.searchStaff(mQuery);
        }
    }
}