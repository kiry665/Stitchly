package com.kiry665.stitchly.history;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("""
        SELECT * FROM history WHERE partId = :partId ORDER BY time DESC
    """)
    LiveData<List<HistoryEntity>> getHistory(long partId);

    @Insert
    long insert (HistoryEntity history);

}
