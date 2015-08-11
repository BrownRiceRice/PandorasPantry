package com.whatisthis.bryceandfriends.pandoraspantry;

import static com.whatisthis.bryceandfriends.pandoraspantry.MainActivity.EXTRA_NEW_INGREDIENT;
import static com.whatisthis.bryceandfriends.pandoraspantry.MainActivity.EXTRA_INVOCATING_BUTTON;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class EnterIngredient extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String group = intent.getStringExtra(EXTRA_INVOCATING_BUTTON);

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
        intent.putExtra(EXTRA_NEW_INGREDIENT, ingredientField.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
