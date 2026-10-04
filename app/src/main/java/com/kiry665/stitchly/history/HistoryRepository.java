package com.kiry665.stitchly.history;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.kiry665.stitchly.database.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryRepository {

    private final HistoryDao historyDao;

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    public HistoryRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        historyDao = database.historyDao();
    }

    public LiveData<List<HistoryEntity>> getHistory(long partId){
        return historyDao.getHistory(partId);
    }

}
