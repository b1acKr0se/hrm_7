package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {
    private void setTitle(String title) {
        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setTitle(title);
    }

    private void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}