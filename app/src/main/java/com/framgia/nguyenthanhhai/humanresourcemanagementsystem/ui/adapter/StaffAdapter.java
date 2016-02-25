package com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.R;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.data.model.Staff;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.listener.OnLoadMoreListener;
import com.framgia.nguyenthanhhai.humanresourcemanagementsystem.ui.listener.OnStaffClickListener;

import java.util.List;

/**
 * Adapter for  staff RecyclerView
 */
public class StaffAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROGRESS = 0;
    private Context mContext;
    private List<Staff> mStaffList;
    private OnStaffClickListener mOnStaffClickListener;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int mVisibleThreshold = 5;
    private int mLastVisibleItem, mTotalItemCount;
    private boolean mIsLoading;
    private RecyclerView.OnScrollListener mOnScrollListener;

    public StaffAdapter(Context context, List<Staff> staffList, RecyclerView recyclerView) {
        this.mContext = context;
        this.mStaffList = staffList;

        setUpPagination(recyclerView);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public void setOnStaffClickListener(OnStaffClickListener onStaffClickListener) {
        this.mOnStaffClickListener = onStaffClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mStaffList.get(position) != null ? VIEW_ITEM : VIEW_PROGRESS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_staff, parent, false);
            viewHolder = new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_progress_bar, parent, false);
            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder staffViewHolder = (ViewHolder) holder;
            staffViewHolder.itemView.setOnClickListener(staffViewHolder);
            staffViewHolder.staff = mStaffList.get(position);
            staffViewHolder.mStaffName.setText(staffViewHolder.staff.getName());
            staffViewHolder.mStaffPosition.setText(staffViewHolder.staff.getPosition().toString());
            switch (staffViewHolder.staff.getStatus()) {
                case CURRENT:
                    staffViewHolder.mStaffBackgroundView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.current_staff));
                    break;
                case LEFT:
                    staffViewHolder.mStaffBackgroundView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.left_staff));
                    break;
                default:
                    staffViewHolder.mStaffBackgroundView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.current_staff));
                    break;
            }
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mStaffList.size();
    }

    private void setUpPagination(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            mOnScrollListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    mTotalItemCount = linearLayoutManager.getItemCount();
                    mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!mIsLoading && mTotalItemCount < (mLastVisibleItem + mVisibleThreshold)) {
                        //reach the end
                        if (mOnLoadMoreListener != null) {
                            mIsLoading = true;
                            mOnLoadMoreListener.onLoadMore();
                        }
                    }
                }
            };
            recyclerView.addOnScrollListener(mOnScrollListener);
        }
    }

    public void removePagination(RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(mOnScrollListener);
    }

    public void setLoaded() {
        mIsLoading = false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Staff staff;
        private View mStaffBackgroundView;
        private TextView mStaffName;
        private TextView mStaffPosition;

        public ViewHolder(View view) {
            super(view);
            mStaffBackgroundView = view.findViewById(R.id.linear_staff_background);
            mStaffName = (TextView) view.findViewById(R.id.text_staff_name);
            mStaffPosition = (TextView) view.findViewById(R.id.text_staff_position);
        }

        @Override
        public void onClick(View v) {
            mOnStaffClickListener.onStaffClick(staff);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        }
    }
}