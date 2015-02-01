package com.whatisthis.bryceandfriends.pandoraspantry;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final String TAG= "MAIN_ACTIVITY";
    ArrayList<String> ingredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // ingredients.add("Test");
        setContentView(R.layout.activity_main);
        ingredients= new ArrayList<String>();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onActivityResult(int requestCode, int resultCode, Intent newItem){
        //gets the new item from the new window
        Log.d(TAG, Integer.toString(requestCode));
        if(requestCode == 0){

            Log.d(TAG, Integer.toString(resultCode));
            Log.d(TAG, Integer.toString(RESULT_OK));
            if(resultCode == RESULT_OK){
                //print when we get here
                Log.d(TAG, "We added: "+ newItem.getStringExtra("NEW_INGREDIENT"));
                //we got the item!  Save it to the arrayList!
                ingredients.add(newItem.getStringExtra("NEW_INGREDIENT"));
            }
        }

    }


    public void goToGroup(View view){
        //open a new activity with the chosen group, send the current list of ingredients
        //and the group items
        Intent intent = new Intent(this, EnterIngredient.class);
        String invocatingButton="";

        //is this necessary?  probably not
        switch(view.getId()) {
            case R.id.spiceButton:
                invocatingButton = "spice";
                break;

            case R.id.bakingButton:
                invocatingButton = "baking";
                break;

            case R.id.dairyButton:
                invocatingButton = "dairy";
                break;

            case R.id.meatButton:
                invocatingButton = "meat";
                break;

            case R.id.produceButton:
                invocatingButton = "produce";
                break;

            case R.id.grainButton:
                invocatingButton = "grain";
                break;
        }

        intent.putExtra("INVOCATING_BUTTON", invocatingButton);

        startActivityForResult(intent, 0);
    }

    public void submit(View view){
        //call the function on the arrayList
        //ListHolder.setList(ingredients);
        //something(ingredients);
        //go to the new activity
        Intent intent= new Intent(this, RecipeBrowse.class);

        ArrayList<String> recipes = new ArrayList<String>();
        BufferedReader read= null;
        String[] temp = null;
        try {
            read = new BufferedReader(new InputStreamReader(this.getAssets().open("book.txt")));
            temp = Main.startRecp(ingredients, read);
        }
        catch(IOException ex){
            Log.d(TAG, ex.toString());
        }
        // needs recipesignatures
        //String[] temp = placeHolderRecipes(ingredients);

        for(int i = 0; i < temp.length; i++){
            recipes.add(temp[i]);
        }

       // intent.putExtra("LIST", ingredients);
        intent.putExtra("LIST", recipes);

        Log.d(TAG, "Going to the RecipeBrowse Class! List is: "+ ingredients.size());
        //Log.v(TAG, "Going to the RecipeBrowse Class! List is: "+ ingredients.size());
        //Log.w(TAG, "Going to the RecipeBrowse Class! List is: "+ ingredients.size());
        startActivity(intent);

    }


    private String[] placeHolderRecipes(ArrayList<String> list){
        String[] temp = new String[4];
        for(int j= 0; j< 4; j++)
            temp[j]="";
        int i=0;
        for(String item: list){
            if(item.equals("eggs"))
                temp[i++]="Scrambled Eggs";
            if(item.equals("flour"))
                temp[i++]="Cake";
            if(item.equals("lettuce"))
                temp[i++]="Salad";
            if(item.equals("grapes"))
                temp[i++]="Grape Juice";
        }
        return temp;
    }
}
