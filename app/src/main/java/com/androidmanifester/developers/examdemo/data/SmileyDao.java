package com.androidmanifester.developers.examdemo.data;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Room data access object.
 */
@Dao
public interface SmileyDao {

    /**
     * Returns all data in table for Paging.
     */
    @Query("select * from smiley order by 1 asc")
    DataSource.Factory<Integer,Smiley> getAll();

    /**
     * Returns LiveData of random Smileys.
     *
     * @param limit number of return
     */
    @Query("select * from smiley order by random() limit :limit")
    LiveData<List<Smiley>> getRandom(int limit);

    /**
     * Returns a random Smiley.
     */
    @Query("select * from smiley order by random() limit 1")
    Smiley getSmiley();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Smiley... smiley);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Smiley smiley);

    @Delete
    void delete(Smiley smiley);

}
