package com.whatisthis.bryceandfriends.pandoraspantry; /**
 * Created by jong-hwilee on 1/31/15.
 */

import java.util.TreeMap;

class SparseArray {
    private TreeMap<Integer, Boolean> array = new TreeMap();
    private int length = 0;

    public void put(int position, boolean element) {
        if (length < position + 1)
            length = position + 1;
        this.array.put(Integer.valueOf(position), element);
    }

    public boolean get(int position) {
        Boolean val = this.array.get(Integer.valueOf(position));
        if (val == null)
            return false;
        else
            return true;
    }

    public IndexedIterator iterator() {
        return new IndexedIterator(this.array);
    }

    public int getLength() {
        return length;
    }

    public String getString() {
        String retString = "";
        IndexedIterator iter = iterator();
        boolean[] retBool = new boolean[length];

        while (iter.hasNext()) {
            IndexedData nextData = iter.next();
            retBool[nextData.getIndex()] = nextData.getData();
        }

        if (length > 0)
            retString += "[ "+retBool[0];
        for (int i = 1; i < length; i++) {
            retString += ", "+retBool[i];
        }
        retString += " ]";

        return retString;
    }
}
