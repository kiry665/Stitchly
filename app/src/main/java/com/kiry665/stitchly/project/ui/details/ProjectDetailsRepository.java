package com.kiry665.stitchly.project.ui.details;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.kiry665.stitchly.database.AppDatabase;
import com.kiry665.stitchly.part.PartDao;
import com.kiry665.stitchly.part.PartEntity;
import com.kiry665.stitchly.project.model.ProjectDao;
import com.kiry665.stitchly.project.model.ProjectEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProjectDetailsRepository {

    private final ProjectDao projectDao;
    private final PartDao partDao;

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    public ProjectDetailsRepository(Context context) {
        AppDatabase database =
                AppDatabase.getInstance(context);

        projectDao = database.projectDao();
        partDao    = database.partDao();
    }

    public LiveData<ProjectEntity> getProject(long projectId){
        return projectDao.getProject(projectId);
    }

    public LiveData<List<PartEntity>> getParts(long projectId){
        return partDao.getParts(projectId);
    }

    public void createPart(String name, Integer targetRows, long projectId) {
        executor.execute(() -> {

            long now = System.currentTimeMillis();

            PartEntity part =
                    new PartEntity();

            part.projectId = projectId;
            part.name = name;
            part.targetRows = targetRows;
            part.currentRow = 0;
            part.createdAt = now;
            part.updatedAt = now;

            partDao.insert(part);
        });
    }

    public void renameProject(String name, long projectId){
        executor.execute(() -> {
            long now = System.currentTimeMillis();
            projectDao.renameProject(projectId, name, now);
        });
    }

    public void deleteProject(long projectId){
        executor.execute(() -> {
            projectDao.deleteProject(projectId);
        });
    }

}
