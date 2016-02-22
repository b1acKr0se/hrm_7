package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.R;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Department;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.listener.OnDepartmentClickListener;
import java.util.List;

/**
 * Adapter for the department list
 */
public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {
    private Context mContext;
    private OnDepartmentClickListener mDepartmentClickListener;
    private List<Department> mDepartmentList;

    public DepartmentAdapter(Context context, List<Department> departmentList, OnDepartmentClickListener departmentClickListener) {
        this.mContext = context;
        this.mDepartmentList = departmentList;
        this.mDepartmentClickListener = departmentClickListener;
    }

    @Override
    public DepartmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_department, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DepartmentAdapter.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(holder);
        holder.department = mDepartmentList.get(position);
        holder.mDepartmentNameTv.setText(holder.department.getName());
    }

    @Override
    public int getItemCount() {
        return mDepartmentList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public Department department;
        private TextView mDepartmentNameTv;

        public ViewHolder(View view) {
            super(view);
            mDepartmentNameTv = (TextView) view.findViewById(R.id.text_department_name);
        }

        @Override
        public void onClick(View v) {
            mDepartmentClickListener.onDepartmentClick(department);
        }
    }
}