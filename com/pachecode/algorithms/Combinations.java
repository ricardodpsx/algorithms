package com.pachecode.algorithms;

import princeton.algs4.algorithms.Interval1D;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by ricardo on 30/12/14.
 */
public class Combinations {

    //Find the ith  combination of size k of a Given sorted subArray
    public static String ithCombination( String[] items , int ith,int k){
        if(k == 1)
            return items[ith];

        int q = ith/items.length;
        int r = ith%items.length;

        return ithCombination(items, q, k - 1) + items[r];
    }

    public static int combinationIndex(String[] pattern, HashMap<String, Integer> reference){
        if( pattern.length == 0)
            return 0;

        String symbol = pattern[ pattern.length - 1];
        pattern = Arrays.copyOf(pattern, pattern.length - 1);


        return reference.size()*combinationIndex(pattern, reference) + reference.get(symbol);

    }


    public static void main(String args[]) {

        //Example of getting the ith combination
        String[] items = new String[]{"A","B","C"};
        System.out.println( ithCombination(items,0, 3) ); //the 0th combination of size 3 is AAA
        System.out.println(ithCombination(items, 1, 3));


        //Example of getting the index of a combination

        HashMap<String, Integer> map = new HashMap<>();

        map.put("A",0);
        map.put("B",1);
        map.put("C",2);



        System.out.println( combinationIndex(new String[]{"C", "B", "A"}, map) ); //this shows 21, lets see...
        System.out.println( ithCombination(items,21, 3) );//Yeah! it shows CBA

        System.out.println( combinationIndex(new String[]{"A","A","A"}, map) ); //this is the 0th Combination

    }



}
