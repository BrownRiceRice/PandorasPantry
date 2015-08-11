package com.whatisthis.bryceandfriends.pandoraspantry;

import static com.whatisthis.bryceandfriends.pandoraspantry.MainActivity.EXTRA_LIST;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeBrowse extends ActionBarActivity {

    // intent extra that is passed to ReadingTxtFile
    static final String EXTRA_BOOK = "com.bryceandfriends.extras.BOOK";
    static final String LIST_KEY = "LIST";

    private static final String TAG = "RECIPE_BROWSE";
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_browse);

        Intent intent= getIntent();

        // if we came from the main screen, get it's intent
        if (list == null) {
            list = (ArrayList<String>) intent.getSerializableExtra(EXTRA_LIST);
        }

        // if we came from the previous screen, get the list from the bundle
        // TODO(bryce): Figure out why this crashes the app, when going back from ReadingTxt
        if (list == null) {
            list = savedInstanceState.getStringArrayList(LIST_KEY);
        }

        Log.d(TAG, "Got into Recipe Browse:  List is: " + list.size() +" long");
        // ViewGroup group = (ViewGroup)getLayoutInflater().inflate(R.id.relLayout);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View v, int position, long id){
                try {
                    lookAtRecipe(parent, v, position);
                }
                catch(IOException ex){
                    Log.d(TAG, ex.toString());
                }
            }
        });
    }

    protected void onResume(){
        super.onResume();
        Log.d(TAG, "Resumed Recipe Browse.  List is: " + list.size() + " long");
        //Don't recreate.
    }

    public void lookAtRecipe(AdapterView<?> parent, View v, int position)throws IOException {
        String requestedRecipe = (String) parent.getItemAtPosition(position);
        Log.d(TAG, "Trying to look at recipe:  "+ requestedRecipe);
        Intent i= new Intent(this, ReadingTxtFile.class);
        i.putExtra(EXTRA_BOOK, requestedRecipe);
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // save the list
        savedInstanceState.putStringArrayList(LIST_KEY, list);
    }
}