package com.pachecode.algorithms;

import java.io.PrintWriter;

/**
 * Clean Implementation of Binary Search
 * Created by ricardo on 31/12/14.
 */
public class BinarySearch {

    /**
     *
     * @param array The sorted subArray
     * @param search The searchComparator
     * @param <T>
     * @return position of the searchComparator or -1 if it doesn't exists
     */
    public static<T extends Comparable> int search(T[] array, T search){

        return search(new PartitionArray<Comparable>(array), search);
    }

    private static<T extends Comparable> Integer search(PartitionArray<T> array, T search){

        if(array.size() == 0) return -1;

        T val= array.pivot().get(0);

        if(val.compareTo(search) == 0)
            return array.pivot().getFrom();
        else if(search.compareTo(val) < 0 )
            return search(array.leftWithPivot(), search);
        else if(search.compareTo(val) > 0)
            return search(array.rightWithPivot(), search);

        return -1;
    }


    public static<T extends Comparable> int ceil(T[] array, T search){


        return ceil(new PartitionArray<T>(array), search);
    }

    public static<T extends Comparable> int ceil(PartitionArray<T> array, T search){

        if(array.size() == 0)
            return - 1;

        T val = array.pivotVal();


        if( search.compareTo(val) == 0 )
            return array.pivot().getFrom();
        else if ( val.compareTo( search ) > 0 )
            return ceil(array.leftWithPivot(), search);
        else {

            int ind = ceil(array.rightWithPivot(), search);

            if(ind == -1) return array.pivot().getFrom();
            else
                return ind;

        }


    }

    /**
     *
     * @param array
     * @param search
     * @param <T>
     * @return the strictly bigger item index searchComparator <= item  floor(1.5) = 2
     */
    public static<T extends Comparable> int floor(T[] array, T search){

        return floor(new PartitionArray<T>(array), search);
    }

    public static<T extends Comparable> int floor(PartitionArray<T> array, T search){

        if(array.size() == 0)
            return - 1;

        T val = array.pivotVal();


        if( search.compareTo(val) == 0 )
            return array.pivot().getFrom();
        else if ( val.compareTo( search ) < 0 )
            return floor(array.rightWithPivot(), search);
        else {

            int ind = floor(array.leftWithPivot(), search);

            if(ind == -1)
                return array.pivot().getFrom();
            else
                return ind;

        }


    }



    static PrintWriter out = new PrintWriter(System.out, true);
    public static void main(String args[]) {
        Integer[] array = new Integer[]{1,2,3,6,100,101};


        out.println(search(array, 100));
        out.println(search(array, 99));
        out.println(search(array, 0));


        Double[] array2 = new Double[]{1d,2d,3d,4d,5d};

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
