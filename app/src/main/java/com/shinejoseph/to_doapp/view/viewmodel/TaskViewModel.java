package com.shinejoseph.to_doapp.view.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import com.shinejoseph.to_doapp.data.AppDatabase;
import com.shinejoseph.to_doapp.data.dao.TaskDao;
import com.shinejoseph.to_doapp.data.entity.TaskEntity;
import com.shinejoseph.to_doapp.model.Task;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TaskViewModel extends AndroidViewModel {
    private LiveData<List<TaskEntity>> mTasks;

    private TaskDao mTaskDao;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        mTaskDao = AppDatabase.getDatabaseInstance(application).taskDao();

        mTasks = mTaskDao.loadTasks();
    }

    public LiveData<List<TaskEntity>> getTasks() {
        return mTasks;
    }

    public void updateTask(TaskEntity taskEntity) {
        new UpdateAsync(taskEntity).execute();
    }

    public void insertTask(Task task) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(task.getId());
        taskEntity.setTask_name(task.getTaskName());
        taskEntity.setTime(task.getTime());
        taskEntity.setIs_completed(task.isCompleted());
        new InsertAsync(taskEntity).execute();
    }

    public void delete(TaskEntity taskEntity) {
        new DeleteAsync(taskEntity).execute();
    }

    class InsertAsync extends AsyncTask<Void, Void, Void> {

        TaskEntity taskEntity;

        public InsertAsync(TaskEntity taskEntity) {
            this.taskEntity = taskEntity;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mTaskDao.insert(taskEntity);
            return null;
        }
    }

    class DeleteAsync extends AsyncTask<Void, Void, Void> {

        TaskEntity taskEntity;

        public DeleteAsync(TaskEntity taskEntity) {
            this.taskEntity = taskEntity;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mTaskDao.deleteTask(taskEntity);
            return null;
        }
    }

    class UpdateAsync extends AsyncTask<Void, Void, Void> {

        TaskEntity taskEntity;

        public UpdateAsync(TaskEntity taskEntity) {
            this.taskEntity = taskEntity;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mTaskDao.updateTask(taskEntity);
            return null;
        }
    }
}
