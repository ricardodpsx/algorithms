package com.pachecode.algorithms.test;

import com.pachecode.algorithms.FenwickTree;

import java.io.PrintWriter;

/**
 * Created by ricardodpsx@gmail.com on 4/01/15.
 */
public class FenwickTreeTest {

    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String args[]) {


        /**Creating the Cumulative Frequency table of array:
         *
         * Array to be Summed (1-indexed)
         * 1 => 0
         * 2 => 1
         * 3 => 0
         * 4 => 1
         * 5 => 2
         * 6 => 3
         * 7 => 2
         * 8 => 1
         *
         * Cumulative Sum:
         * 1 => 0
         * 2 => 1
         * 3 => 1
         * 4 => 2
         * 5 => 4
         * 6 => 7
         * 7 => 9
         * 8 => 10
         */

        FenwickTree ft = new FenwickTree(8);

        ft.update(2, 1);
        ft.update(4, 1);
        ft.update(5, 2);
        ft.update(6, 3);
        ft.update(7, 2);
        ft.update(8, 1);

        for(int i =1; i <= 8; i++)
            out.println( ft.rsq(i)); //Should show 'Cumulative Frequencies' data


        out.println( ft.rsq(6, 8) );

        out.close();
    }
}
