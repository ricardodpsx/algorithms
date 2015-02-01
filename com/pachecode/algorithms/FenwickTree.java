package com.pachecode.algorithms;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ricardodpsx@gmail.com on 4/01/15.
 *
 *  WARNING: This works with 1 indexed subArray!
 *
 * TODO: Unit Testing
 */
public class FenwickTree {
    Integer[] array;
    public FenwickTree(int size) {
        array =  new Integer[size + 1];
        Arrays.fill(array, 0);
    }

    /**
     * RSQ (Range Sum Query)
     * @param a
     * @return Range Sum from [1, b]
     */
    public Integer rsq(int a) {

        Integer sum = 0;
        while(a > 0 ) {
            sum += array[a];
            a -= a&(-a);
        }

        return sum;
    }

    /**
     * RSQ (Range Sum Query)
     * @return Range Sum from [a,b]
     */
    public Integer rsq(int a, int b) {
        assert b >= a;

        return rsq(b) - (a == 1? 0 : rsq(a - 1));
    }


    public void update(int k, Integer value) {
        while(k < array.length ){
            array[k] += value;
            k += k&(-k);
        }
    }



}
