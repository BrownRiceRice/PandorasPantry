package com.whatisthis.bryceandfriends.pandoraspantry; /**
 * Created by jong-hwilee on 1/31/15.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipeSignature implements Comparable<RecipeSignature> {
    static IngArray allIngs = new IngArray();
    private String name = "";
    private SparseArray ingList = new SparseArray();
    private int viable = 0;
    private int score = 0;

    public RecipeSignature(String data) {
        boolean nameFlag = true;
        int ingIndex = 0;
        String ingBuffer = "";

        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '\n') {
                if (nameFlag)
                    nameFlag = false;
                else {
                    if ((ingIndex = allIngs.getIndex(ingBuffer)) != -1)
                        ingList.put(ingIndex, true);
                    else {
                        allIngs.putIng(ingBuffer);
                        ingList.put(allIngs.getIndex(ingBuffer), true);
                    }
                    ingBuffer = "";
                }
            }
            else if (nameFlag)
                name += data.charAt(i);
            else
                ingBuffer += data.charAt(i);
        }

        if (!ingBuffer.equals("")) {
            if ((ingIndex = allIngs.getIndex(ingBuffer)) != -1)
                ingList.put(ingIndex, true);
            else {
                allIngs.putIng(ingBuffer);
                ingList.put(allIngs.getIndex(ingBuffer), true);
            }
        }
    }
    /**
     * Constructor for RecipeSignatures.
     * Reads the given XML file and saves it as recipe signatures.
     */
    public RecipeSignature(File data) {
        if (allIngs == null)
            allIngs = new IngArray();

        try {
            BufferedReader readData = new BufferedReader(new FileReader(data));
            String nextLine;
            String saveIng;

            Pattern recipeTitle = Pattern.compile("<title>(.+)</title>");
            Pattern ingTitle = Pattern.compile("<i>(.+)</i>");

            while ((nextLine = readData.readLine())!= null) {
                Matcher titleMatch = recipeTitle.matcher(nextLine);
                Matcher ingMatch = ingTitle.matcher(nextLine);
                if (titleMatch.find())
                    name = titleMatch.group(1);
                else if (ingMatch.find()) {
                    saveIng = ingMatch.group(1);
                    int ingIndex;
                    if ((ingIndex = allIngs.getIndex(saveIng)) != -1)
                        ingList.put(ingIndex, true);
                    else {
                        allIngs.putIng(saveIng);
                        ingList.put(allIngs.getIndex(saveIng), true);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public SparseArray getIngList() {
        return ingList;
    }

    public int getViable() {
        return viable;
    }

    /**
     * Vote up.
     */
    public void upVote() {
        viable += 1;
    }

    /**
     * Vote down.
     */
    public void downVote() {
        viable -= 1;
    }
    /**
     * Calculates similarity of ingredients needed for this recipe and fridge ingredients list.
     *
     * @param fridgeList    Ingredients in the fridge
     * @return              Edit distance.
     */
    public int differsBy(SparseArray fridgeList) {
        int count = 0;
        int checkLength = fridgeList.getLength() > ingList.getLength() ? fridgeList.getLength(): ingList.getLength();

        for (int i = 0; i < checkLength; i++) {
            if (fridgeList.get(i) != ingList.get(i))
                count ++;
        }

        return count;
    }

    /**
     * Checks if all ingredients needed for this recipe(R) is in the fridge(F).
     * F    R   F||(!R)
     * T    F   T
     * T    T   T
     * F    T   F
     * F    F   T
     *
     * @param fridgeList    Ingredients in the fridge
     * @return              Whether the user can make this food only with the ingredients in fridge.
     */
    public boolean contains(SparseArray fridgeList) {
        for (int i = 0; i < ingList.getLength(); i++) {
            if (!(fridgeList.get(i) || !(ingList.get(i))))
                return false;
        }

        return true;
    }

    public int containsNum(SparseArray fridgeList) {
        int count = 0;

        for (int i = 0; i < ingList.getLength(); i++) {
            if (fridgeList.get(i) && (fridgeList.get(i) == ingList.get(i)))
                count ++;
        }

        return count;
    }
    // Show 10 recipes ranked by the following priority.
    // 1. You can make this recipe only with the ingredients you entered.
    // 2. Edit distance.
    // if two recipes tie in 1, use # of votes.
    public void updateScore(SparseArray fridgeList) {
        if (ingList.getLength() < 2)
            score = 0;
        else if (contains(fridgeList))
            score = 5000;
        else {
            score += (10 * containsNum(fridgeList));
            score +=  differsBy(fridgeList);
        }

        score += viable;
    }

    public int getScore() {
        return score;
    }
    public int compareTo(RecipeSignature otherRecipe) {
        return otherRecipe.getScore() - this.getScore();
    }
}
