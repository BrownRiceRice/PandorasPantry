package com.whatisthis.bryceandfriends.pandoraspantry;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static String[] startRecp(ArrayList<String> list, BufferedReader read) throws IOException {
        RecipeSignature[] listTests = new RecipeSignature[600];
        System.out.println(-1);
        ArrayList<String> recipeData = parseBook(read);

        System.out.println("0");
        for (int i = 0; i < 600; i++) {
            if (i == 1000)
                System.out.println("0.3");
            if (i == 2000)
                System.out.println("0.6");
            listTests[i] = new RecipeSignature(recipeData.get(i));
        }

        System.out.println("1");
        ArrayList<String> ingInput = list;
       // ingInput.add("eggs");
       // ingInput.add("butter");
       // ingInput.add("flour");

        String[] results = findRecipes(ingInput, listTests);
       // System.out.println("2");
       // for (int i = 0; i < 10; i++) {
       //     System.out.println(results[i]);
       // }
        // if you pass the xml file to the recipeSignatures,
        //System.out.println("3");
        return results;
    }

    /**
     * Given list of ingredients, return list of recipe names.
     */
    public static String[] findRecipes (ArrayList<String> ingredients, RecipeSignature[] recipes) {
        // Total ingredient list
        IngArray allIngredients = RecipeSignature.allIngs;
        String ing;
        SparseArray fridge = new SparseArray();

        // Make a sparseArray fridgeList
        for (int i = 0; i < ingredients.size(); i++) {
            fridge.put(allIngredients.getIndex(ingredients.get(i)), true);
        }

        ArrayList<RecipeSignature> prior = new ArrayList<RecipeSignature>();

        // Show 10 recipes ranked by the following priority.
        // 1. You can make this recipe only with the ingredients you entered.
        // 2. Edit distance.
        // if two recipes tie in 1, use # of votes.
        for (int i = 0; i < recipes.length; i++) {
            recipes[i].updateScore(fridge);
            prior.add(recipes[i]);
        }

        Collections.sort(prior);

        String[] retString = new String[10];

        for (int i = 0; i < 10; i++) {
            retString[i] = prior.get(i).getName();
        }

        return retString;
    }

    public static ArrayList<String> parseBook(BufferedReader read) {
        ArrayList<String> book = new ArrayList<String>();
        int count = 0;
        int count2= 0;
        try {
            BufferedReader readData = read; //new BufferedReader(new FileReader(myBook));
            String nextLine;

            Pattern start = Pattern.compile("<cookbook>");
            Pattern recipe = Pattern.compile("<recipe>");
            Pattern closeRecipe = Pattern.compile("</recipe>");
            Pattern end = Pattern.compile("</cookbook>");
            Pattern recipeTitle = Pattern.compile("<title>(.+)</title>");
            Pattern ingTitle = Pattern.compile("<i>(.+)</i>");

            while ((nextLine = readData.readLine()) != null) {
                Matcher startMatch = start.matcher(nextLine);
                if (startMatch.find()) {
                    nextLine = readData.readLine();
                    Matcher endMatch = end.matcher(nextLine);
                    while (!endMatch.find()) {
                        Matcher recipeMatch = recipe.matcher(nextLine);
                        if (recipeMatch.find()) {
                            String myrecipe = "";
                            nextLine = readData.readLine();
                            Matcher closeMatch = closeRecipe.matcher(nextLine);
                            while (!closeMatch.find()) {
                                Matcher titleMatch = recipeTitle.matcher(nextLine);
                                Matcher ingMatch = ingTitle.matcher(nextLine);

                                if (titleMatch.find()) {
                                    count2 = 0;
                                    myrecipe = myrecipe + titleMatch.group(1) + "\n";
                                    count ++;
                                    if (count % 100 == 0)
                                   //     System.out.println(count);
                                    if (count > 2522)
                                        break;
                                    nextLine = readData.readLine();
                                }
                                else if (ingMatch.find()) {
                                    count2++;
                                    myrecipe = myrecipe + ingMatch.group(1) + "\n";
                                    nextLine = readData.readLine();
                                }
                                closeMatch=closeRecipe.matcher(nextLine);
                            }
                            if (count > 2522)
                                break;
                            if (!(count2 == 0)){
                                book.add(myrecipe);
                            }

                            nextLine = readData.readLine();
                            recipeMatch = recipe.matcher(nextLine);

                        }
                        endMatch = end.matcher(nextLine);
                    }
                    if (count > 2522)
                        break;

                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }
}
