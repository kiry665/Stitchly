package com.kiry665.stitchly.part;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import com.kiry665.stitchly.project.ui.details.ProjectDetailsRepository;

public class PartViewModel extends AndroidViewModel {

    private final long partId;
    private final PartRepository repository;
    private final LiveData<PartEntity> part;

    public PartViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);

        partId = savedStateHandle.get("partId");
        repository = new PartRepository(application);
        part = repository.getPart(partId);
    }

    public LiveData<PartEntity> getPart() {
        return part;
    }

    public void increment(){
        repository.increment(partId);
    }

    public void decrement(){
        repository.decrement(partId);
    }

    public void reset(){
        repository.reset(partId);
    }

    public void deletePart(){
        repository.deletePart(partId);
    }

    public void updatePart(String name, Integer targetRows) {
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        repository.updatePart(partId, name.trim(), targetRows);
    }

}
