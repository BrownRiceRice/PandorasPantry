package com.whatisthis.bryceandfriends.pandoraspantry;

public class IndexedData {
    private int index;
    private boolean data = false;

    public IndexedData(int position, boolean element) {
        this.index = position;
        this.data = element;
    }

    public int getIndex() {
        return this.index;
    }

    public boolean getData() {
        return this.data;
    }
}
