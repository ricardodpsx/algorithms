package com.pachecode.algorithms;

/**
 * Created by ricardodpsx@gmail.com on 3/02/15.
 */
public class Utils {

    //Test if the range1 contains range2
    boolean contains(int from1, int to1, int from2, int to2 ) {
        return from2 >= from1 && to2 <= to1;
    }

    //Inclusive intersection
    boolean intersects(int from1, int to1, int from2, int to2 ){
        return from1 <= from2 && to1 >=from2   //  (.[..)..] or (.[...]..)
                || from1 >= from2 && from1 <= to2 ; // [.(..]..) or [..(..)..
    }

}
