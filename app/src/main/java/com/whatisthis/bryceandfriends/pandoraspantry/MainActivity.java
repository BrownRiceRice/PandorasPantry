package com.whatisthis.bryceandfriends.pandoraspantry;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    // the extras contained in the various intents that are passed around
    static final String EXTRA_INVOCATING_BUTTON =
            "com.bryceandfriends.extras.EXTRA_INVOCATING_BUTTON";
    static final String EXTRA_LIST =
            "com.bryceandfriends.extras.EXTRA_LIST";
    public static final String EXTRA_NEW_INGREDIENT =
            "com.bryceandfriends.extras.EXTRA_NEW_INGREDIENT";

    private static final String TAG = "MAIN_ACTIVITY";
    ArrayList<String> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ingredients= new ArrayList<>();
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

    /**
     * When the enter button on the add ingredient is pressed, this method is called.
     * TODO(bryce): describe in more detail
     * @param requestCode
     * @param resultCode
     * @param newItem the new ingredient to add to the list
     */
    public void onActivityResult(int requestCode, int resultCode, Intent newItem){
        // gets the new item from the new window
        Log.d(TAG, Integer.toString(requestCode));
        if(requestCode == 0){

            Log.d(TAG, Integer.toString(resultCode));
            Log.d(TAG, Integer.toString(RESULT_OK));
            if(resultCode == RESULT_OK){
                Log.d(TAG, "We added: "+ newItem.getStringExtra(EXTRA_NEW_INGREDIENT));
                // we got the item!  Save it to the arrayList!
                ingredients.add(newItem.getStringExtra(EXTRA_NEW_INGREDIENT));
            }
        }

    }

    /**
     * Opens a new activity with the chosen group, sends the current list of ingredients and group
     * items.
     * @param view the view that was clicked on the screen
     */
    public void goToGroup(View view){
        Intent intent = new Intent(this, EnterIngredient.class);
        String invocatingButton= "";

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

        intent.putExtra(EXTRA_INVOCATING_BUTTON, invocatingButton);

        startActivityForResult(intent, 0);
    }

    /**
     * All ingredients should be entered, sumbit the list and open the activity to browse recipes
     * @param view the view that was clicked on the screen
     */
    public void submit(View view){
        Intent intent= new Intent(this, RecipeBrowse.class);

        ArrayList<String> recipes = new ArrayList<>();
        BufferedReader read;
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

        intent.putExtra(EXTRA_LIST, recipes);

        Log.d(TAG, "Going to RecipeBrowse Class, List has "+ ingredients.size() + " ingredients");
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
