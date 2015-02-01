package com.whatisthis.bryceandfriends.pandoraspantry;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class IndexedIterator implements Iterator<IndexedData> {
    private Iterator<Entry<Integer, Boolean>> iter;

    public IndexedIterator(TreeMap<Integer, Boolean> treemap) {
        this.iter = treemap.entrySet().iterator();
    }

    public boolean hasNext() {
        return this.iter.hasNext();
    }

    public IndexedData next() {
        Entry entry = (Entry)this.iter.next();
        return new IndexedData(((Integer)entry.getKey()).intValue(), ((Boolean)entry.getValue()).booleanValue());
    }

    public void remove() throws UnsupportedOperationException {
        this.iter.remove();
    }
}
