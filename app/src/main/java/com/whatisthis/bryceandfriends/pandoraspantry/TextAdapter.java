package com.whatisthis.bryceandfriends.pandoraspantry;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TextAdapter extends BaseAdapter {
    private Context mContext;

    private String str[];
    private ArrayList<String> recipes;

    public TextAdapter(Context c) {
        mContext = c;
        //recipes= new ArrayList<String>();
       // recipes.add("Test1");
       // recipes.add("Test2");
         //this.recipes = recipes;
    }

    public int getCount() {
        return 1;//recipes.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //textView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            textView.setPadding(8, 8, 8, 8);
        } else {
            textView = (TextView) convertView;
        }

        ArrayList<String> list =ListHolder.getList();
     //   if(list== null || list.size()< position)
      //      textView.setText("HI");
      //  else
            textView.setText(ListHolder.getList().get(position));//recipes.get(position));
       // textView.setImageResource(mThumbIds[position]);
        return textView;
    }

   // references to our images
}
