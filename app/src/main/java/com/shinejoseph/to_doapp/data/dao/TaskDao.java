package com.shinejoseph.to_doapp.data.dao;

import com.shinejoseph.to_doapp.data.entity.TaskEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskEntity taskEntity);

    @Query("SELECT * FROM tasks")
    LiveData<List<TaskEntity>> loadTasks();

    @Update
    void updateTask(TaskEntity taskEntity);

    @Delete
    void deleteTask(TaskEntity taskEntity);
}
