package com.whatisthis.bryceandfriends.pandoraspantry;

import java.util.TreeMap;

class IngArray {
    private TreeMap<String, Integer> array = new TreeMap();
    private int count = 0;

    /**
     * Puts ingredient in and creates the index for it.
     *
     * @param ingName   Name of the ingredient to put in.
     */
    public void putIng(String ingName) {
        this.array.put(ingName, count);
        count ++;
    }

    /**
     * get index of the ingredient given the ingredient name.
     * If the ingredient does not exist in the list, return -1.
     *
     * @param ingName   Name of the ingredient whose index will be returned
     * @return          Index of the ingredient
     */
    public int getIndex(String ingName) {
        Integer indexVal = this.array.get(ingName);
        if (indexVal == null)
            return -1;
        else
            return indexVal.intValue();
    }

    public int getLength() {
        return count;
    }
}