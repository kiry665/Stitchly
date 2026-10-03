package com.kiry665.stitchly.part;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PartDao {

    @Query("""
        SELECT * FROM parts
        WHERE projectId = :projectId
        ORDER BY createdAt ASC
    """)
    LiveData<List<PartEntity>> getParts(long projectId);

    @Query("""
        SELECT * FROM parts
        WHERE id = :partId
        LIMIT 1
    """)
    LiveData<PartEntity> getPart(long partId);

    @Insert
    long insert(PartEntity part);

    @Query("""
        UPDATE parts
        SET currentRow = currentRow + 1,
            updatedAt = :time
        WHERE id = :partId
    """)
    void incrementRow(long partId, long time);

    @Query("""
        UPDATE parts
        SET currentRow =
            CASE
                WHEN currentRow > 0
                THEN currentRow - 1
                ELSE 0
            END,
            updatedAt = :time
        WHERE id = :partId
    """)
    void decrementRow(long partId, long time);

    @Query("""
        UPDATE parts
        SET
            currentRow = 0,
            updatedAt = :time
        WHERE id = :partId
            
    """)
    void reset(long partId, long time);

    @Query(
            "UPDATE parts " +
                    "SET name = :name, updatedAt = :time " +
                    "WHERE id = :partId"
    )
    void renamePart(long partId, String name, long time);

    @Query("DELETE FROM parts WHERE id = :partId")
    void deletePart(long partId);

    @Query("""
        UPDATE parts
            SET name = :name, targetRows = :targetRows, updatedAt = :time
            WHERE id = :partId
    """)
    void updatePart(long partId, String name, Integer targetRows, long time);

}
