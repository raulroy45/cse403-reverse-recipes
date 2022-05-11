package com.cse403.reverserecipes.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "result_recipe_table")
public class ResultRecipe {
    @PrimaryKey
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

    public ResultRecipe(int rid, String image, String link, String title, int totalTime, int yields) {
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
        return mTotalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ResultRecipe) {
            ResultRecipe other = (ResultRecipe) o;
            return
                    this.mRid == other.mRid &&
                    this.mImage.equals(other.mImage) &&
                    this.mLink.equals(other.mLink) &&
                    this.mTitle.equals(other.mTitle) &&
                    this.mTotalTime == other.mTotalTime &&
                    this.mYields == other.mYields;
        } else {
            return false;
        }
    }
}
