package com.kiry665.stitchly.project.ui.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import com.kiry665.stitchly.part.PartEntity;
import com.kiry665.stitchly.project.model.ProjectEntity;

import java.util.List;

public class ProjectDetailsViewModel extends AndroidViewModel {

    private final long projectId;
    private final ProjectDetailsRepository repository;
    private final LiveData<ProjectEntity> project;
    private final LiveData<List<PartEntity>> parts;

    public ProjectDetailsViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);

        projectId = savedStateHandle.get("projectId");

        repository = new ProjectDetailsRepository(application);
        project = repository.getProject(projectId);
        parts = repository.getParts(projectId);
    }

    public LiveData<ProjectEntity> getProject() {
        return project;
    }

    public LiveData<List<PartEntity>> getParts() {
        return parts;
    }

    public void createPart(String name, Integer targetRows) {
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        repository.createPart(name.trim(), targetRows, projectId);
    }

    public void renameProject(String name){
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        repository.renameProject(name, projectId);
    }

    public void deleteProject(){
        repository.deleteProject(projectId);
    }

}
