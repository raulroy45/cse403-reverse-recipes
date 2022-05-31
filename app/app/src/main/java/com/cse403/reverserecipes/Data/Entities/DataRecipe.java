package com.cse403.reverserecipes.Data.Entities;

import java.util.List;

public class DataRecipe {
    private final int mRid;

    private final String mImage;

    private final String mLink;

    private final String mTitle;

    private final int mTotalTime;

    private final int mYields;

    private final List<String> mIngredients;

    private final List<String> mInstructions;

    public DataRecipe(int rid, String image, String link, String title,
                      int totalTime, int yields, List<String> ingredients, List<String> instructions) {
        mRid = rid;
        mImage = image;
        mLink = link;
        mTitle = title;
        mTotalTime = totalTime;
        mYields = yields;
        mIngredients = ingredients;
        mInstructions = instructions;
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

    public List<String> getIngredients() {return mIngredients;}

    public List<String> getInstructions() {return mInstructions; }
}
