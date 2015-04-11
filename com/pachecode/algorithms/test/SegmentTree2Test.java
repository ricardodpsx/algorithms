package com.pachecode.algorithms.test;


import com.pachecode.algorithms.SegmentTree2;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by ricardodpsx@gmail.com on 8/01/15.
 */
public class SegmentTree2Test {
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

        //SegmentTree<Integer> s = new SegmentTree<>(ar, SegmentTree.min(), SegmentTree.RSQ());


        Integer[] ar = new Integer[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        SegmentTree2 s = new SegmentTree2(ar);

        assertEquals(1, (int) ar[ s.RMQ(0, 0)] );
        assertEquals(8, (int) ar[ s.RMQ(8, 8)] );
        assertEquals(-2,(int) ar[ s.RMQ(0, 8)] );
        assertEquals(-2, (int) ar[ s.RMQ(0, 4)] );
        assertEquals(-1, (int)ar[ s.RMQ(4, 8)] );
        assertEquals(-2,  (int)ar[ s.RMQ(3, 8)] );
        assertEquals(-1, (int) ar[ s.RMQ(4, 6)] );

    }

    @Test
    public void testSearchUpdate(){

        //Integer[] ar = new Integer[] {1};

        //SegmentTree<Integer> s = new SegmentTree<>(ar, SegmentTree.min(), SegmentTree.RSQ());


        Integer[] ar = new Integer[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        SegmentTree2 s = new SegmentTree2(ar);

        s.update(3, 4);

        //System.out.println(s);

        assertEquals(-1, (int) ar[ s.RMQ(0, 8)] );
        assertEquals( 1,(int) ar[ s.RMQ(0, 4)] );
        assertEquals(-1, (int) ar[ s.RMQ(4, 8)] );

        s.update(8, -10);
        assertEquals(-10,(int) ar[ s.RMQ(0, 8)] );
        assertEquals(-10, (int)ar[ s.RMQ(4, 8)] );

    }

    @Test
    public void testAgregation(){

        Integer[] ar = new Integer[]{1, 1, 1, 1, 2, 2, 2, 2, 2};

        SegmentTree2 s = new SegmentTree2(ar);

        assertEquals(1 * 4 + 2 * 5, s.RSQ(0, 8));
        assertEquals(1*4 , s.RSQ(0, 3) );
        assertEquals(2*5 , s.RSQ(4, 8) );
        assertEquals(1 + 2 , s.RSQ(3, 4) );
        assertEquals(1*2 + 2*2 , s.RSQ(2, 5) );
    }

    @Test
    public void testAgregationUpdate(){

        Integer[] ar = new Integer[]{1, 1, 1, 1, 2, 2, 2, 2, 2};

        SegmentTree2 s = new SegmentTree2(ar);

        s.update(4, 0); // {1, 1, 1, 1, 0, 2, 2, 2, 2};
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
        Integer[] ar = new Integer[]{1, 2, 3};
        SegmentTree2 s = new SegmentTree2(ar);

        assertEquals(6,  s.RSQ(0, 2) );
        assertEquals(3, s.RSQ(0, 1) );

        assertEquals(1, (int)ar[ s.RMQ(0, 2) ] );
        assertEquals(5, s.RSQ(1, 2) );

        s.update(1, 3);// {1, 3, 3 }

        assertEquals(1, (int)ar[  s.RMQ(0, 2)] );
        assertEquals(6,  s.RSQ(1, 2) );
        assertEquals(7, s.RSQ(0, 2) );

        s.update(2, 2); // {1, 3, 2 }
        assertEquals(6, s.RSQ(0, 2) );
        assertEquals(4,  s.RSQ(0, 1) );


    }

    @Test
    public void test1SizeArray() {
        Integer[] ar = new Integer[]{1};
        SegmentTree2 s = new SegmentTree2(ar);

        assertEquals(1,  s.RSQ(0, 0) );
        assertEquals(1, (int)ar[ (int) s.RMQ(0, 0)] );

        s.update(0, 3);

        assertEquals(3, s.RSQ(0, 0) );
        assertEquals(3, (int)ar[ (int) s.RMQ(0, 0)] );

    }

    @Test
    public void test2SizeArray() {
        Integer[] ar = new Integer[]{1, 2};
        SegmentTree2 s = new SegmentTree2(ar);

        assertEquals(3, s.RSQ(0, 1) );
        assertEquals(2,s.RSQ(1, 1) );


        assertEquals(1, (int)ar[ (int) s.RMQ(0, 1)] );
        assertEquals(2, (int)ar[ (int) s.RMQ(1, 1)] );

        s.update(0, 3);

        assertEquals(3, s.RSQ(0, 0));
        assertEquals(5,  s.RSQ(0, 1) );
        assertEquals(2, (int)ar[(int) s.RMQ(0, 1)] );

    }




    public static void main(String args[]) {
        Integer[] ar = new Integer[]{1, 3, 45, 2, 13, 40, 100, 1, 32};

        SegmentTree2 s = new SegmentTree2(ar);

        out.println(s);

        s.update(6,-2);

        out.println();
        out.println(s);

        out.println(ar[s.RMQ(1, 5)]);

        out.close();

    }

}
