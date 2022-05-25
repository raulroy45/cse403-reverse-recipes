package com.cse403.reverserecipes.Data.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_recipe_table")
public class DataRecipe {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "rid")
    private final int mRid;

    @ColumnInfo(name = "image")
    private final String mImage;

    @ColumnInfo(name = "link")
    private final String mLink;

    @ColumnInfo(name = "title")
    private final String mTitle;

    @ColumnInfo(name = "total_time")
    private final int mTotalTime;

    @ColumnInfo(name = "yields")
    private final int mYields;

    public DataRecipe(int rid, String image, String link, String title, int totalTime, int yields) {
        mRid = rid;
        mImage = image;
        mLink = link;
        mTitle = title;
        mTotalTime = totalTime;
        mYields = yields;
    }

    public int getRid() {
        return mRid;
    }

    public String getImage() {
        return mImage;
    }

    public String getLink() {
        return mLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getTotalTime() {
        return mTotalTime;
    }

    public int getYields() {
        return mYields;
    }
}
