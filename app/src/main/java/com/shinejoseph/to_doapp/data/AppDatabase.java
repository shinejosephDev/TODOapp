package com.shinejoseph.to_doapp.data;

import android.content.Context;

import com.shinejoseph.to_doapp.data.dao.TaskDao;
import com.shinejoseph.to_doapp.data.entity.TaskEntity;
import com.shinejoseph.to_doapp.utils.Constants;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TaskEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase sInstance;

    // Get a database instance
    public static synchronized AppDatabase getDatabaseInstance(Context context) {
        if (sInstance == null) {
            sInstance = create(context);
        }
        return sInstance;
    }

    // Create the database
    static AppDatabase create(Context context) {
        RoomDatabase.Builder<AppDatabase> builder = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class,
                Constants.TABLE_TASK);
        return builder.build();
    }

    public abstract TaskDao taskDao();
}
