package com.shinejoseph.to_doapp.view;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shinejoseph.to_doapp.R;
import com.shinejoseph.to_doapp.data.entity.TaskEntity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private ArrayList<TaskEntity> taskList;
    private TaskListener listener;

    public TasksAdapter(ArrayList<TaskEntity> taskList, TaskListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tasks, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (taskList.get(position).getTask_name() != null && !taskList.get(position).getTask_name().equals(""))
            holder.tvTask.setText(taskList.get(position).getTask_name());
        else
            holder.tvTask.setText("no task");

        if (taskList.get(position).isIs_completed()) {
            holder.checkBox.setChecked(true);
            holder.tvTask.setPaintFlags(holder.tvTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.checkBox.setChecked(false);
            holder.tvTask.setPaintFlags(holder.tvTask.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        holder.checkBox.setOnClickListener(v -> {
            if (holder.checkBox.isChecked()) {
                taskList.get(position).setIs_completed(true);
                holder.tvTask.setPaintFlags(holder.tvTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                listener.onCheckBoxTap(position, true);
            } else {
                taskList.get(position).setIs_completed(false);
                holder.tvTask.setPaintFlags(holder.tvTask.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                listener.onCheckBoxTap(position, false);
            }

        });

        holder.cardView.setOnLongClickListener(v -> {
            listener.onLongTap(position);
            return false;
        });


    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    interface TaskListener {
        void onLongTap(int position);

        void onCheckBoxTap(int position, boolean isCompeted);
        }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTask;
        private CheckBox checkBox;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tv_task);
            checkBox = itemView.findViewById(R.id.cb);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
