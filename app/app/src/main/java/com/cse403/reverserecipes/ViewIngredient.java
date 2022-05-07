package com.cse403.reverserecipes;

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

    public boolean getSelected() {
        return mSelected;
    }
}
