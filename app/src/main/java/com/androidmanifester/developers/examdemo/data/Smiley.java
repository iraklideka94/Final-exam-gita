package com.androidmanifester.developers.examdemo.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * A Model class that holds information about the emoji.
 * Class defines a table for the Room database with primary key the {@see #mCode}.
 */
@Entity(tableName = DataSmileyName.TABLE_NAME)
public class Smiley {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = DataSmileyName.COL_UNICODE)
    private String mCode;
    private String RandomData;

    @ColumnInfo(name = DataSmileyName.COL_NAME)
    private String mName;

    @ColumnInfo(name = DataSmileyName.COL_EMOJI)
    private String mEmoji;

    public Smiley(@NonNull String code, String name, String emoji, String RandomData) {
        this.mCode = code;
        this.mName = name;
        this.mEmoji = emoji;
        this.RandomData = RandomData;
    }

    public String getRandomData() {
        return RandomData;
    }

    public void setRandomData(String randomData) {
        RandomData = randomData;
    }

    @NonNull
    public String getCode() {
        return mCode;
    }

    public String getName() {
        return mName;
    }

    public String getEmoji() {
        return mEmoji;
    }

}
