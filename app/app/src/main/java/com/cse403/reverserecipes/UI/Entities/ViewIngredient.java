package com.cse403.reverserecipes.UI.Entities;

public class ViewIngredient {
    private int mId;
    private String mName;
    private boolean mSelected;

    public ViewIngredient(int id, String name, boolean selected) {
        mId = id;
        mName = name;
        mSelected = selected;
    }

    public int getId() { return mId; }

    public String getName() {
        return mName;
    }

    public boolean isSelected() {
        return mSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ViewIngredient) {
            ViewIngredient other = (ViewIngredient) o;
            return
                    this.mId == other.mId &&
                    this.mName.equals(other.mName) &&
                    this.mSelected == other.mSelected;
        } else {
            return false;
        }
    }
}
