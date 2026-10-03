package com.kiry665.stitchly.project.ui.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kiry665.stitchly.project.model.ProjectEntity;

import java.util.List;

public class ProjectListViewModel extends AndroidViewModel {

    private final ProjectListRepository repository;
    private final LiveData<List<ProjectEntity>> projects;

    public ProjectListViewModel(@NonNull Application application) {
        super(application);

        repository = new ProjectListRepository(application);
        projects = repository.getProjects();
    }

    public LiveData<List<ProjectEntity>> getProjects() {
        return projects;
    }

    public void createProject(String name) {
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        repository.createProject(name.trim());
    }


}
