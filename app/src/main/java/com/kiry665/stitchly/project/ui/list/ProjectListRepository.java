package com.kiry665.stitchly.project.ui.list;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.kiry665.stitchly.database.AppDatabase;
import com.kiry665.stitchly.project.model.ProjectDao;
import com.kiry665.stitchly.project.model.ProjectEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProjectListRepository {

    private final ProjectDao projectDao;

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    public ProjectListRepository(Context context) {
        AppDatabase database =
                AppDatabase.getInstance(context);

        projectDao = database.projectDao();
    }

    public LiveData<List<ProjectEntity>> getProjects() {
        return projectDao.getProjects();
    }

    public void createProject(String name) {
        executor.execute(() -> {

            long now = System.currentTimeMillis();

            ProjectEntity project =
                    new ProjectEntity();

            project.name = name;
            project.createdAt = now;
            project.updatedAt = now;

            projectDao.insert(project);
        });
    }

}
