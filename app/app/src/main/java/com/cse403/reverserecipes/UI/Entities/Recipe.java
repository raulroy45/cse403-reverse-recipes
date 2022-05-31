package com.cse403.reverserecipes.UI.Entities;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    private final int mRid;

    private final String mImage;

    private final String mLink;

    private final String mTitle;

    private final int mTotalTime;

    private final int mYields;
    private final List<String> mIngredients;

    private final List<String> mInstructions;

    private final boolean mSaved;

    public Recipe(int rid, String image, String link, String title,
                  int totalTime, int yields,
                  List<String> ingredients, List<String> instructions, boolean saved) {
        mRid = rid;
        mImage = image;
        mLink = link;
        mTitle = title;
        mTotalTime = totalTime;
        mYields = yields;
        mIngredients = ingredients;
        mInstructions = instructions;
        mSaved = saved;
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

    public List<String> getIngredients() {return mIngredients; }

    public List<String> getInstructions() {return mInstructions; }

    public boolean isSaved() {
        return mSaved;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Recipe) {
            Recipe other = (Recipe) o;
            return
                    this.mRid == other.mRid &&
                    this.mImage.equals(other.mImage) &&
                    this.mLink.equals(other.mLink) &&
                    this.mTitle.equals(other.mTitle) &&
                    this.mTotalTime == other.mTotalTime &&
                    this.mYields == other.mYields &&
                    this.mSaved == other.mSaved;
        } else {
            return false;
        }
    }
}
