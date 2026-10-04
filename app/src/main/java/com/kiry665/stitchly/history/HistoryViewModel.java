package com.kiry665.stitchly.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import com.kiry665.stitchly.part.PartEntity;
import com.kiry665.stitchly.part.PartRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private final long partId;
    private final HistoryRepository repository;
    private final LiveData<List<HistoryEntity>> history;

    public HistoryViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);

        Long id = savedStateHandle.get("partId");

        if (id == null) {
            throw new IllegalArgumentException(
                    "partId is required"
            );
        }

        partId = id;
        repository = new HistoryRepository(application);
        history = repository.getHistory(partId);
    }

    public LiveData<List<HistoryEntity>> getHistory() {
        return history;
    }

}
