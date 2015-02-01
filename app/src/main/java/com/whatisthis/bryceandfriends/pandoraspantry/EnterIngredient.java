package com.whatisthis.bryceandfriends.pandoraspantry;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class EnterIngredient extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String group = intent.getStringExtra("INVOCATING_BUTTON");

        //Set the background to be the image that used to be the button
        RelativeLayout daLayout = (RelativeLayout)(findViewById(R.id.rightHere));
        int backImage=R.drawable.ic_launcher;
        if(group == "spice") {
            backImage = R.drawable.spices;
        }
        //daLayout.setBackgroundResource(R.drawable.ic_launcher);

        //Set the title
        setTitle(group);

        setContentView(R.layout.activity_enter_ingredient);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_ingredient, menu);
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
            openSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openSettings(){
        Intent intent= new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void addIngredient(View view){
        Intent intent = new Intent();
        EditText ingredientField = (EditText) findViewById(R.id.ingredientText);
        intent.putExtra("NEW_INGREDIENT", ingredientField.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}


//File blah = new File("/sthosndsnt/nths");
//Intent i = new Intent();
//i.setDataAndType(Uri.fromFile(blah), "text/plain");
//startActivity(i);
