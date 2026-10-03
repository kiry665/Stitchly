package com.kiry665.stitchly.project.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.kiry665.stitchly.part.PartEntity;

import java.util.List;

public class ProjectWithParts {

    @Embedded
    public ProjectEntity project;

    @Relation(
            parentColumn = "id",
            entityColumn = "projectId"
    )
    public List<PartEntity> parts;
}
