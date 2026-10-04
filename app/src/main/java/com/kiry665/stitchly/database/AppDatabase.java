package com.kiry665.stitchly.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kiry665.stitchly.history.HistoryDao;
import com.kiry665.stitchly.history.HistoryEntity;
import com.kiry665.stitchly.part.PartDao;
import com.kiry665.stitchly.part.PartEntity;
import com.kiry665.stitchly.project.model.ProjectDao;
import com.kiry665.stitchly.project.model.ProjectEntity;

@Database(
        entities = {
                ProjectEntity.class,
                PartEntity.class,
                HistoryEntity.class
        },
        version = 2,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract ProjectDao projectDao();
    public abstract PartDao partDao();
    public abstract HistoryDao historyDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "stitchly.db"
                    ).build();
                }
            }
        }

        return INSTANCE;
    }

}
