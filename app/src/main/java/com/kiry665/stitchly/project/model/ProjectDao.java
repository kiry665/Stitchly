package com.kiry665.stitchly.project.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM projects WHERE id = :projectId LIMIT 1")
    LiveData<ProjectEntity> getProject(long projectId);

    @Query("SELECT * FROM projects ORDER BY createdAt DESC")
    LiveData<List<ProjectEntity>> getProjects();

    @Insert
    long insert(ProjectEntity project);

    @Query(
            "UPDATE projects " +
                    "SET name = :name, updatedAt = :time " +
                    "WHERE id = :projectId"
    )
    void renameProject(long projectId, String name, long time);

    @Query("DELETE FROM projects WHERE id = :projectId")
    void deleteProject(long projectId);

}
