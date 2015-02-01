package com.pachecode.algorithms;

import java.util.Comparator;

/**
 * A clean implementation of MergeSort using PartitionArray
 *
 * Created by ricardo on 31/12/14.
 */
public class MergeSort {

    public static<T extends Comparable> void  sort(T[] array){

        //Using Default comparator
        sort(array, new Comparator<Comparable>() {
            @Override
            public int compare(Comparable o1, Comparable o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public static<T extends Comparable> void  sort(T[] array, Comparator<T> comparator){

        PartitionArray<T> p = new PartitionArray<>(array);

        sort(p, comparator);
    }



    private static<T extends Comparable> void  sort(PartitionArray<T> array, Comparator<T> comparator){

        if(!array.canSplit()) return;

        sort(array.left(), comparator);
        sort(array.right(), comparator);

        merge(array.left(), array.right(), comparator);
    }



    private static <T> void merge(PartitionArray<T> left, PartitionArray<T> right, Comparator<T> comparator) {

        T[] copy = (T[]) new Object[ left.size() + right.size()];

        int iLeft = 0;
        int iRight = 0;

        //Merging into a copy subArray
        for(int i = 0; i < copy.length; i++) {

            if(iRight >= right.size())//if there are no more items in getRight copy the rest of getLeft
                copy[i] = left.get(iLeft++);
            else if(iLeft >= left.size()) //if there are no more items in getLeft copy the rest of getRight
                copy[i] = right.get(iRight++);
            else if(  comparator.compare(left.get(iLeft), right.get(iRight) ) <= 0  )
                copy[i] = left.get(iLeft++);
            else //If getLeft item is bigger than getRight item
                copy[i] = right.get(iRight++);

        }

        //Copying the merged subArray back
        PartitionArray<T> all  = left.join(right);
        for(int i =0; i < copy.length; i++) {
            all.set(i, copy[i]);
        }


    }


}
