package com.kiry665.stitchly.history;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.kiry665.stitchly.part.PartEntity;

import org.jetbrains.annotations.NotNull;

@Entity(
        tableName = "history",
        foreignKeys = @ForeignKey(
                entity = PartEntity.class,
                parentColumns = "id",
                childColumns = "partId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("partId")
)
public class HistoryEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public long partId;

    @NonNull
    public HistoryAction action;
    public int oldValue;
    public int newValue;

    @NotNull
    public long time;

}
