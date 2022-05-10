package com.cse403.reverserecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cse403.reverserecipes.IngredientSearchFragment;

public class DetailActivity extends AppCompatActivity
{
    self.is.ccahill.com.au.shapelist.Shape selectedShape;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSelectedShape();
        setValues();

    }

    private void getSelectedShape()
    {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedShape = getParsedShape(parsedStringID);
    }

    private self.is.ccahill.com.au.shapelist.Shape getParsedShape(String parsedID)
    {
        for (self.is.ccahill.com.au.shapelist.Shape shape : IngredientSearchFragment.shapeList)
        {
            if(shape.getId().equals(parsedID))
                return shape;
        }
        return null;
    }

    private void setValues()
    {
        TextView tv = (TextView) findViewById(R.id.shapeName);
        ImageView iv = (ImageView) findViewById(R.id.shapeImage);

        tv.setText(selectedShape.getId() + " - " + selectedShape.getName());
        iv.setImageResource(selectedShape.getImage());
    }
}