package com.pachecode.algorithms.test;

import static org.junit.Assert.*;

import com.pachecode.algorithms.BinarySearch;
import com.pachecode.algorithms.PartitionArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by ricardo on 31/12/14.
 */

public class BinnarySearchTest {

    Integer[] array;
    Integer[] arrayOne;
    Integer[] arrayTwo;
    Integer[] arrayThree;
    Integer[] arrayFour;
    Integer[] arrayFive;

    Integer[] repeatedArray;
    Integer[] alternateArray;

    Integer[][] allUniqueArrays;


    @Before
    public void setUp() {
        array = new Integer[]{3,1,4,2,5,10,15};
        arrayOne = new Integer[]{3};
        arrayTwo = new Integer[]{3,2};
        arrayThree = new Integer[]{1, 3,2};
        arrayFour = new Integer[]{1,100, 3,2};
        arrayFive = new Integer[]{1,100, 3,2};


        repeatedArray = new Integer[]{1,2,1,2,1,2,4};
        Arrays.sort(repeatedArray);

        alternateArray = new Integer[]{1,3,5,7};

        allUniqueArrays = new Integer[][]{array, arrayOne, arrayTwo, arrayThree, arrayFour, arrayFive};

        for(Integer[] ar :allUniqueArrays)
            Arrays.sort(ar);
    }

    @Test
    public void testSearchObviousHit(){

        //Testing that search(array[i]) is equal to i
        for(Integer[] ar: allUniqueArrays) {
            for(int i =0; i < ar.length; i++) {

                assertEquals("Searching " + ar[i] + " in " + Arrays.toString(ar) + " Should be " + i,
                        i, BinarySearch.search(ar, ar[i] ));
            }
        }


    }

    @Test
    public void testSearchRepeatingArrays(){
        //Testing Search in repeated array
        assertTrue(-1 != BinarySearch.search(repeatedArray, 1));
        assertTrue(-1 != BinarySearch.search(repeatedArray, 2));

        assertEquals(-1, BinarySearch.search(repeatedArray, 3 ));
    }

    @Test
    public void testSearchNonExistent(){

        //Testing not found (no array have 0)
        for(Integer[] ar: allUniqueArrays) {
            for(int i =0; i < ar.length; i++) {
                assertEquals(-1, BinarySearch.search(ar, 0 ));
            }
        }
    }

    @Test
    public void testSearchAlternate(){

        for(int i =0; i < alternateArray.length; i++) {
            assertTrue(BinarySearch.search(alternateArray, alternateArray[i] + 1) == -1);
        }

    }

    @Test
    public void testSearchBoundaries(){
        //Testing  Boundaries
        for(Integer[] ar: allUniqueArrays) {

            assertEquals(-1, BinarySearch.search(ar, ar[0] - 1 )); //Lower than the lowest
            assertEquals(-1, BinarySearch.search(ar, ar[ar.length - 1] + 1 )); //Bigger than the biggest

        }
    }


    @Test
    public void testCeilBoundaries(){


        for(Integer[] ar: allUniqueArrays) {

            //if you have a = [0,1,2,3] and you ceil(a, -1) you will get -1
            assertEquals(0, BinarySearch.ceil(ar, ar[0]));
            assertEquals(-1, BinarySearch.ceil(ar, ar[0] - 1)); //Lower than the lowest
            assertEquals(-1, BinarySearch.ceil(ar, ar[0] - 123232));

            //if you have a = [0,1,2,3] and you ceil(a, 1000) you will get 3
            assertEquals(ar.length - 1, BinarySearch.ceil(ar, ar[ar.length - 1]  ));
            assertEquals(ar.length - 1, BinarySearch.ceil(ar, ar[ar.length - 1] + 1 ));
            assertEquals(ar.length - 1, BinarySearch.ceil(ar, ar[ar.length - 1] + 1000 ));

        }

    }

    @Test
    public void testCeilExactMatches(){
        for(Integer[] ar: allUniqueArrays) {
            for(int i =0; i < ar.length; i++) {
                assertEquals(i, BinarySearch.ceil(ar, ar[i]));
            }
        }

    }


    @Test
    public void testCeilAlternates(){

        Integer[][] alternateArrays = new Integer[][]{
                new Integer[]{2,4},
                new Integer[]{2, 4,6},
                new Integer[]{2, 4,6, 8},
                new Integer[]{2, 8},
                new Integer[]{2, 8,16},
                new Integer[]{2, 8, 16, 24,32}
        };

        for(Integer[] ar: alternateArrays) {
            for(int i =0; i < ar.length; i++) {
                //if you have a = [x, y, z ] and you do ceil(a, x + 1) you will always get x , assuming y is never equal to x + 1
                assertEquals(i, BinarySearch.ceil(ar, ar[i] + 1));
            }
        }

        for(Integer[] ar: alternateArrays) {
            for(int i =0; i < ar.length - 1; i++) {
                //if you have a = [x, y, z ] and you do ceil(a, (x + y)/2) you will always get x
                assertEquals(i, BinarySearch.ceil(ar, (ar[i] + ar[i + 1])/2  ));
            }
        }
    }

    @Test
    public void testFloorBoundaries(){


        for(Integer[] ar: allUniqueArrays) {

            //if you have a = [0,1,2,3] and you floor(a, 0) you will get 0
            assertEquals(0, BinarySearch.floor(ar, ar[0]));

            //if you have a = [0,1,2,3] and you floor(a, 4) you will get -1
            assertEquals(-1, BinarySearch.floor(ar, ar[ar.length - 1] + 1));

            assertEquals(-1, BinarySearch.floor(ar, ar[ar.length - 1] + 2000));

            //if you have a = [0,1,2,3] and you ceil(a, -1000) you will get 0
            assertEquals(0, BinarySearch.floor(ar, ar[0]));
            assertEquals(0, BinarySearch.floor(ar, ar[0] - 1));
            assertEquals(0, BinarySearch.floor(ar, ar[0] - 1000));

        }

    }

    @Test
    public void testFloorExactMatches(){
        for(Integer[] ar: allUniqueArrays) {
            for(int i =0; i < ar.length; i++) {
                assertEquals(i, BinarySearch.floor(ar, ar[i]));
            }
        }

    }


    @Test
    public void testFloorAlternates(){

        Integer[][] alternateArrays = new Integer[][]{
                new Integer[]{2,4},
                new Integer[]{2, 4,6},
                new Integer[]{2, 4,6, 8},
                new Integer[]{2, 8},
                new Integer[]{2, 8,16},
                new Integer[]{2, 8, 16, 24,32}
        };

        for(Integer[] ar: alternateArrays) {
            for(int i = 0; i < ar.length; i++) {
                //if you have a = [x, y, z ] and you do ceil(a, x - 1) you will always get x , assuming y is never equal to x - 1
                assertEquals(i, BinarySearch.floor(ar, ar[i] - 1));
            }
        }

        for(Integer[] ar: alternateArrays) {
            for(int i =0; i < ar.length - 1; i++) {
                //if you have a = [x, y, z ] and you do ceil(a, (x + y)/2) you will always get y
                assertEquals(i + 1, BinarySearch.floor(ar, (ar[i] + ar[i + 1])/2  ));
            }
        }
    }
}
