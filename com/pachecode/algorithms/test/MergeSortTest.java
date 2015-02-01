package com.pachecode.algorithms.test;

import com.pachecode.algorithms.MergeSort;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import princeton.algs4.algorithms.Insertion;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * Created by ricardo on 31/12/14.
 */
public class MergeSortTest {

    Integer[] array;
    Integer[] arrayOne;
    Integer[] arrayTwo;
    Integer[] arrayThree;
    Integer[] arrayFour;
    Integer[] arrayFive;
    Integer[] repeatedArray;

    Integer[][] allArrays;


    PrintWriter out = new PrintWriter(System.out, true);

    @Before
    public void setUp(){

        array = new Integer[]{3,1,4,2,5,10,15};
        arrayOne = new Integer[]{3};
        arrayTwo = new Integer[]{3,2};
        arrayThree = new Integer[]{1, 3,2};
        arrayFour = new Integer[]{1,100, 3,2};
        arrayFive = new Integer[]{1,100, 3,2, 1};
        repeatedArray = new Integer[]{1,2,1,2,1,2};

        allArrays = new Integer[][]{array, arrayOne, arrayTwo, arrayThree, arrayFour, arrayFive, repeatedArray};
    }

    @After
    public  void close(){

    }

    @Test
    public void defaultSortTest() {

        for(Integer[] ar: allArrays){
            MergeSort.sort(ar);
            assertTrue(testAscendingSort(ar));
        }

    }

    @Test
    public void sortTest(){


        for(Integer[] ar: allArrays) {
            Integer[] array2 = Arrays.copyOf(ar, ar.length);
            //Comparing with the default java function
            Arrays.sort(array2);
            MergeSort.sort(ar);
            assertEquals(array2, ar);
        }

        //Testing a modified sort
        for(Integer[] ar: allArrays) {
            Integer[] array2 = Arrays.copyOf(ar, ar.length);
            //Comparing with the default java function
            Arrays.sort(array2);
            Collections.reverse(Arrays.asList(array2));

            //Reverse soring with this mergeSort
            MergeSort.sort(ar, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });
            assertEquals(array2, ar);
        }



    }



    @Test(timeout = 3000) //3 seconds is a good time for it because log(1000000) is just 13.81
    public void bigArrayTest(){
        Double[] bigArray = new Double[1000000];



        for(int i =0; i < bigArray.length; i++){
            bigArray[i] = Math.random();
        }

        //If the complexity would be O(n^2) it will stuck for a longtime
        MergeSort.sort(bigArray);

        //Insertion.sort(bigArray); Try with this one XD

        assertTrue(testAscendingSort(bigArray));
    }

    private static<T extends Comparable> boolean testAscendingSort(T[] array) {
        for(int i =0; i < array.length -1; i++)
            if(array[i].compareTo(array[i + 1]) > 0) return false;

        return  true;
    }




}
