package com.kiry665.stitchly.part;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.kiry665.stitchly.database.AppDatabase;
import com.kiry665.stitchly.history.HistoryAction;
import com.kiry665.stitchly.history.HistoryDao;
import com.kiry665.stitchly.history.HistoryEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PartRepository {

    private final AppDatabase database;
    private final PartDao partDao;
    private final HistoryDao historyDao;

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    public PartRepository(Context context) {
        database = AppDatabase.getInstance(context);
        partDao = database.partDao();
        historyDao = database.historyDao();
    }

    public LiveData<PartEntity> getPart(long partId){
        return partDao.getPart(partId);
    }

    public void increment(long partId){
        executor.execute(() -> {
            long now = System.currentTimeMillis();

            int oldValue = partDao.getCurrentRow(partId);
            int newValue = oldValue + 1;

            partDao.incrementRow(partId, now);

            HistoryEntity history = new HistoryEntity();
            history.partId = partId;
            history.action = HistoryAction.INCREMENT;
            history.oldValue = oldValue;
            history.newValue = newValue;
            history.time = now;

            historyDao.insert(history);
        });
    }

    public void decrement(long partId) {
        executor.execute(() -> {

            database.runInTransaction(() -> {

                int oldValue = partDao.getCurrentRow(partId);

                if (oldValue <= 0) {
                    return;
                }

                int newValue = oldValue - 1;
                long now = System.currentTimeMillis();

                partDao.decrementRow(partId, now);

                HistoryEntity history = new HistoryEntity();
                history.partId = partId;
                history.action = HistoryAction.DECREMENT;
                history.oldValue = oldValue;
                history.newValue = newValue;
                history.time = now;

                historyDao.insert(history);
            });
        });
    }

    public void reset(long partId) {
        executor.execute(() -> {

            database.runInTransaction(() -> {

                int oldValue = partDao.getCurrentRow(partId);

                if (oldValue == 0) {
                    return;
                }

                long now = System.currentTimeMillis();

                partDao.reset(partId, now);

                HistoryEntity history = new HistoryEntity();
                history.partId = partId;
                history.action = HistoryAction.RESET;
                history.oldValue = oldValue;
                history.newValue = 0;
                history.time = now;

                historyDao.insert(history);
            });
        });
    }

    public void deletePart(long partId){
        executor.execute(() -> {
            partDao.deletePart(partId);
        });
    }

    public void updatePart(long partId, String name, Integer targetRows){
        executor.execute(() -> {
            long now = System.currentTimeMillis();
            partDao.updatePart(partId, name, targetRows, now);
        });
    }

}
