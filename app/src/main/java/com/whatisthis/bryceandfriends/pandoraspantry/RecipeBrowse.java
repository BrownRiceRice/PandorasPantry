package com.whatisthis.bryceandfriends.pandoraspantry;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class RecipeBrowse extends ActionBarActivity {

    private static final String TAG = "RECIPE_BROWSE";
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_browse);

        Intent intent= getIntent();

        list= (ArrayList<String>) intent.getSerializableExtra("LIST");
        Log.d(TAG, "Got into Recipe Browse:  List is: " + list.toString());
       // ViewGroup group = (ViewGroup)getLayoutInflater().inflate(R.id.relLayout);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View v, int position, long id){
                lookAtRecipe(parent, v, position, id);
            }
        });
    }



//File blah = new File("/.../...");
//Intent i = new Intent();
//i.setDataAndType(Uri.fromFile(blah), "text/plain");
//startActivity(i);
    public void lookAtRecipe(AdapterView<?> parent, View v, int position, long id)throws IOException {
        Log.d(TAG, "Trying to look at recipe:  "+ parent.getItemAtPosition(position));
        String ret = (String)parent.getItemAtPosition(position);
        File blah = new File(this.getAssets().+""+ret+".txt");
        Log.d(TAG, "Made the file rep of the recipe!: "+ blah.getAbsolutePath().toString());
        Intent i= new Intent();
        i.setDataAndType(Uri.fromFile(blah), "text/plain");
        i.putExtra("STREAM,", (Serializable)this.getAssets().open("/txtLists/"+ ret+".txt"));
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_browse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}