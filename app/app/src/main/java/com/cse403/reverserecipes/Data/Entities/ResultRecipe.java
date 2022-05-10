package com.cse403.reverserecipes.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "result_recipe_table")
public class ResultRecipe {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private final int mId;

    @ColumnInfo(name = "name")
    private final String mName;

    public ResultRecipe(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ResultRecipe) {
            ResultRecipe other = (ResultRecipe) o;
            return
                    this.mId == other.mId && this.mName.equals(other.mName);
        } else {
            return false;
        }
    }
}
