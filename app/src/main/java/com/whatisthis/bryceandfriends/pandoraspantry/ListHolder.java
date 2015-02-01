package com.whatisthis.bryceandfriends.pandoraspantry;

import java.util.ArrayList;

public class ListHolder{
    private static ArrayList<String> list;
    public static ArrayList<String> getList() {return list;}
    public static void setList(ArrayList<String> list) { list= list;}

    //private static final ListHolder holder = new ListHolder();
    //public static ListHolder getInstance() {return holder;}
}
