package com.whatisthis.bryceandfriends.pandoraspantry;

import static com.whatisthis.bryceandfriends.pandoraspantry.RecipeBrowse.EXTRA_BOOK;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadingTxtFile extends ActionBarActivity {
    private static final String TAG = "READING_TXT_FILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Got to Reading Txt.");
        super.onCreate(savedInstanceState);
        Intent intent= getIntent();
        String message = intent.getStringExtra(EXTRA_BOOK);
        String content = "";
        BufferedReader blah = null;
        TextView text =new TextView(this);
        try{
            blah = new BufferedReader(new InputStreamReader(this.getAssets().open(message+".txt")));
            String line= blah.readLine();

            while(line != null){
                content += line + "\n";
                line= blah.readLine();
            }
            text.setText(content);
            blah.close();
        }catch(IOException ex) {
            Log.d(TAG, ex.toString());
        }
        setContentView(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reading_txt_file, menu);
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
    public void onBackPressed() {
        finish();
    }
}
