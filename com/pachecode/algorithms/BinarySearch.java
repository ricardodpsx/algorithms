package com.pachecode.algorithms;

import java.io.PrintWriter;
import java.util.Comparator;

/**
 * Clean Implementation of Binary Search
 * Created by ricardo on 31/12/14.
 */
public class BinarySearch {

    /**
     * @param array  The sorted subArray
     * @param search The searchComparator
     * @param <T>
     * @return position of the searchComparator or -1 if it doesn't exists
     */
    public static int search(Comparable[] array, Comparable search) {

        return search(array, 0, array.length - 1, search);
    }

    static int search(Comparable[] array, int from, int to, Comparable search) {

        if (to - from < 0) return -1;
        int mid = (from + to) / 2;
        Comparable val = array[mid];

        if (val.compareTo(search) == 0) return mid;
        else if (search.compareTo(val) < 0) return search(array, from, mid - 1, search);
        else if (search.compareTo(val) > 0) return search(array, mid + 1, to, search);

        return -1;
    }


    public static int ceil(Comparable[] array, Comparable search) {
        return ceil(array, 0, array.length - 1, search);
    }

    public static int ceil(Comparable[] array, int from, int to, Comparable search) {

        if (to - from < 0)
            return -1;

        int mid = (from + to) / 2;
        Comparable val = array[mid];


        if (search.compareTo(val) == 0) return mid;
        else if (val.compareTo(search) > 0) return ceil(array, from, mid - 1, search);
        //else:
        int ind = ceil(array, mid + 1, to, search);

        if (ind == -1) return mid;
        //else
        return ind;
    }

    public static int floor(Comparable[] array, Comparable search) {
        return floor(array, 0, array.length - 1, search);
    }

    public static int floor(Comparable[]  array, int from, int to, Comparable search) {

        if (to - from < 0)
            return -1;

        int mid = (to + from)/2;
        Comparable val = array[mid];

        if (search.compareTo(val) == 0)         return mid;
        else if (val.compareTo(search) < 0)     return floor(array, mid + 1, to, search);
        //else

        int ind = floor(array, from, mid - 1, search);

        if (ind == -1)  return mid;
        else            return ind;

    }


    static PrintWriter out = new PrintWriter(System.out, true);

    public static void main(String args[]) {
        Integer[] array = new Integer[]{1, 2, 3, 6, 100, 101};


        out.println(search(array, 100));
        out.println(search(array, 99));
        out.println(search(array, 0));


        Double[] array2 = new Double[]{1d, 2d, 3d, 4d, 5d};

        out.println("Ceil " + BinarySearch.ceil(array2, 0.));//-1
        out.println("Ceil " + array2[BinarySearch.ceil(array2, 1.5)]);//1
        out.println("Ceil " + array2[BinarySearch.ceil(array2, 2.5)]);//2
        out.println("Ceil " + array2[BinarySearch.ceil(array2, 5d)]); //5
        out.println("Ceil " + array2[BinarySearch.ceil(array2, 5d)]); //5
        out.println("Ceil " + array2[BinarySearch.ceil(array2, 1000d)]); //5


        out.println("Floor " + array2[BinarySearch.floor(array2, -1000d)]);//1
        out.println("Floor " + array2[BinarySearch.floor(array2, 1.5)]);//2
        out.println("Floor " + array2[BinarySearch.floor(array2, 2.5)]);//3
        out.println("Floor " + array2[BinarySearch.floor(array2, 5d)]); //5
        out.println("Floor " + BinarySearch.floor(array2, 6d)); //-1


    }


}
