//package self.is.ccahill.com.au.shapelist;
package com.cse403.reverserecipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ShapeAdapter extends ArrayAdapter<self.is.ccahill.com.au.shapelist.Shape>
{

    public ShapeAdapter(Context context, int resource, List<self.is.ccahill.com.au.shapelist.Shape> shapeList)
    {
        super(context,resource,shapeList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        self.is.ccahill.com.au.shapelist.Shape shape = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shape_cell, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.shapeName);
        ImageView iv = (ImageView) convertView.findViewById(R.id.shapeImage);

        tv.setText(shape.getId() + " - " + shape.getName());
        iv.setImageResource(shape.getImage());


        return convertView;
    }
}
