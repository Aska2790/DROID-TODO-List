package com.aska.development.todolist.ui.main.tasks.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.aska.development.todolist.databinding.TaskItemViewBinding;
import com.aska.development.todolist.ui.main.tasks.TaskItemViewModel;

import java.util.List;

public class TaskItemViewAdapter extends  RecyclerView.Adapter<TaskItemViewAdapter.ViewHolder>{
    //region Fields
    protected List<TaskItemViewModel> mItems;
    //endregion

    //region Properties

    @Override
    public int getItemCount() {
        return (mItems !=null)? mItems.size() : 0;
    }

    public TaskItemViewModel getItem(int position) {
        if(mItems != null && (position >= 0 && position < mItems.size())){
            return mItems.get(position);
        }
        return null;
    }

    public void setItems(@Nullable List<TaskItemViewModel> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    //endregion

    //region Constructors
    //endregion

    //region Methods

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TaskItemViewBinding binding = TaskItemViewBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        TaskItemViewModel item = mItems.get(position);
        holder.bind(item);
    }

    //endregion

    //region Inner

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TaskItemViewBinding mBinding;

        public ViewHolder(TaskItemViewBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(TaskItemViewModel item) {
            mBinding.setItem(item);
            mBinding.executePendingBindings();
        }
    }
    //endregion
}
