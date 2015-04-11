package com.pachecode.algorithms.test;

import com.pachecode.algorithms.SegmentTree;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by ricardodpsx@gmail.com on 5/02/15.
 */
public class SegmentTreeTest {

    Integer[][] arrays;
    Integer[] mins, sums;

    static PrintStream out = System.out;

    @Before
    public void setUp() {

    }



    /***
     * Testing Inherit ed Behaviors
     */


    @Test
    public void testSearch(){

        //Integer[] ar = new Integer[] {1};

        //SegmentTreeHeap<Integer> s = new SegmentTreeHeap<>(ar, SegmentTreeHeap.min(), SegmentTreeHeap.RSQ());


        int[] ar = new int[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        SegmentTree s = new SegmentTree(ar);

        assertEquals(1, s.RMinQ(0, 0) );
        assertEquals(8,  s.RMinQ(8, 8) );
        assertEquals(-2,  s.RMinQ(0, 8) );
        assertEquals(-2, s.RMinQ(0, 4) );
        assertEquals(-1, s.RMinQ(4, 8) );
        assertEquals(-2, s.RMinQ(3, 8) );
        assertEquals(-1, s.RMinQ(4, 6) );

    }

    @Test
    public void testSearchUpdate(){

        //Integer[] ar = new Integer[] {1};

        //SegmentTreeHeap<Integer> s = new SegmentTreeHeap<>(ar, SegmentTreeHeap.min(), SegmentTreeHeap.RSQ());


        int[] ar = new int[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        SegmentTree s = new SegmentTree(ar);

        s.update(3, 3, 4);

        //System.out.println(s);

        assertEquals(-1,  s.RMinQ(0, 8) );
        assertEquals( 1,  s.RMinQ(0, 4) );
        assertEquals(-1,  s.RMinQ(4, 8));

        s.update(8, 8, -10);
        assertEquals(-10, s.RMinQ(0, 8) );
        assertEquals(-10, s.RMinQ(4, 8) );

    }

    @Test
    public void testAgregation(){

        int[] ar = new int[]{1, 1, 1, 1, 2, 2, 2, 2, 2};

        SegmentTree s = new SegmentTree(ar);

        assertEquals(1 * 4 + 2 * 5, s.RSQ(0, 8));
        assertEquals(1*4 , s.RSQ(0, 3) );
        assertEquals(2*5 , s.RSQ(4, 8) );
        assertEquals(1 + 2 , s.RSQ(3, 4) );
        assertEquals(1*2 + 2*2 , s.RSQ(2, 5) );
    }

    @Test
    public void testAgregationUpdate(){

        int[] ar = new int[]{1, 1, 1, 1, 2, 2, 2, 2, 2};

        SegmentTree s = new SegmentTree(ar);

        s.update(4,4, 0); // {1, 1, 1, 1, 0, 2, 2, 2, 2};
        //System.out.println(s);

        assertEquals(0, s.RSQ(4, 4));
        assertEquals(1 * 4 + 2*4 , s.RSQ(0, 8) );
        assertEquals(1*4 , s.RSQ(0, 3) );
        assertEquals(2*4 , s.RSQ(4, 8) );
        assertEquals(1 , s.RSQ(3, 4) );
        assertEquals(1*2 + 1*2 , s.RSQ(2, 5) );


    }

    @Test
    public void test3SizeArray() {
        int[] ar = new int[]{1, 2, 3};
        SegmentTree s = new SegmentTree(ar);

        assertEquals(6,  s.RSQ(0, 2) );
        assertEquals(3, s.RSQ(0, 1) );

        assertEquals(1,  s.RMinQ(0, 2)  );
        assertEquals(5, s.RSQ(1, 2) );

        s.update(1,1, 3);// {1, 3, 3 }

        assertEquals(1,  s.RMinQ(0, 2) );
        assertEquals(6,  s.RSQ(1, 2) );
        assertEquals(7, s.RSQ(0, 2) );

        s.update(2,2, 2); // {1, 3, 2 }
        assertEquals(6, s.RSQ(0, 2) );
        assertEquals(4,  s.RSQ(0, 1) );


    }

    @Test
    public void test1SizeArray() {
        int[] ar = new int[]{1};
        SegmentTree s = new SegmentTree(ar);

        assertEquals(1,  s.RSQ(0, 0) );
        assertEquals(1,  (int) s.RMinQ(0, 0));

        s.update(0,0, 3);

        assertEquals(3, s.RSQ(0, 0) );
        assertEquals(3, (int) s.RMinQ(0, 0) );

    }

    @Test
    public void test2SizeArray() {
        int[] ar = new int[]{1, 2};
        SegmentTree s = new SegmentTree(ar);

        assertEquals(3, s.RSQ(0, 1) );
        assertEquals(2,s.RSQ(1, 1) );


        assertEquals(1,  (int) s.RMinQ(0, 1) );
        assertEquals(2, (int) s.RMinQ(1, 1) );

        s.update(0,0, 3);

        assertEquals(3, s.RSQ(0, 0));
        assertEquals(5,  s.RSQ(0, 1) );
        assertEquals(2, s.RMinQ(0, 1));

    }



}
