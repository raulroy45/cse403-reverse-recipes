package com.cse403.reverserecipes.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class DataRecipe {
    private final int mRid;

    private final String mImage;

    private final String mLink;

    private final String mTitle;

    private final int mTotalTime;

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
