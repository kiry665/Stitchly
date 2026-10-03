package com.kiry665.stitchly.part;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.kiry665.stitchly.database.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PartRepository {

    private final PartDao partDao;

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    public PartRepository(Context context) {
        AppDatabase database =
                AppDatabase.getInstance(context);

        partDao = database.partDao();
    }

    public LiveData<PartEntity> getPart(long partId){
        return partDao.getPart(partId);
    }

    public void increment(long partId){
        executor.execute(() -> {
            long now = System.currentTimeMillis();
            partDao.incrementRow(partId, now);
        });
    }

    public void decrement(long partId){
        executor.execute(() -> {
            long now = System.currentTimeMillis();
            partDao.decrementRow(partId, now);
        });
    }

    public void reset(long partId) {
        executor.execute(() -> {
            long now = System.currentTimeMillis();
            partDao.reset(partId, now);
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
