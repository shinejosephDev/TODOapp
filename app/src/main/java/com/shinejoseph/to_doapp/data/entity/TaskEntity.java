package com.shinejoseph.to_doapp.data.entity;

import com.shinejoseph.to_doapp.utils.Constants;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = Constants.TABLE_TASK)
public class TaskEntity {
    @PrimaryKey()
    private int id;
    private String task_name;
    private String time;
    private boolean is_completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isIs_completed() {
        return is_completed;
    }

    public void setIs_completed(boolean is_completed) {
        this.is_completed = is_completed;
    }
}
