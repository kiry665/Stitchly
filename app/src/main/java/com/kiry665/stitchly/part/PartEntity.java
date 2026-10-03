package com.kiry665.stitchly.part;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.kiry665.stitchly.project.model.ProjectEntity;

@Entity(
        tableName = "parts",
        foreignKeys = @ForeignKey(
                entity = ProjectEntity.class,
                parentColumns = "id",
                childColumns = "projectId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index("projectId")
        }
)
public class PartEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long projectId;

    @NonNull
    public String name;

    public int currentRow;
    public Integer targetRows;

    public long createdAt;
    public long updatedAt;
}
