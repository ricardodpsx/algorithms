package com.pachecode.algorithms.old;


import com.pachecode.algorithms.old.IntegerSegmentTree;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by ricardodpsx@gmail.com on 8/01/15.
 */
public class IntegerSegmentTreeTest {


    Integer[][] arrays;
    Integer[] mins, sums;

    static PrintStream out = System.out;

    @Before
    public void setUp() {

    }


    @Test
    public void testLazy(){
        Integer[] ar = new Integer[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        IntegerSegmentTree s = new IntegerSegmentTree(ar);

        s.update(0, 3, 0);

        out.println(s);
        out.println();
      //  out.println(ar[s.search(0, 0)]);

        s.update(0, -4);
        out.println(s);
    }

    @Test
    public void testRangeUpdateAndNormalUpdateTogether(){
        Integer[] ar = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};

        IntegerSegmentTree s = new IntegerSegmentTree(ar);

        s.update(0, 3, 1); //1 1 1 1 0 0 0 0
        s.update(1, 2);//1 2 1 1 0 0 0 0

        out.println(s);

        assertEquals(5, s.aggregation(0, 4));

        s.update(3, new Integer[]{5, 5, 5, 5}); //1 2 1 5 5 5 5 0

        out.println(s);

        assertEquals(6, s.aggregation(2, 3));

        s.update(2, 6,  0); //1 2 0 0 0 0 0 0

        out.println(s);
        assertEquals(3, s.aggregation(0, 4));
        assertEquals(0, s.aggregation(4, 8));

    }

    @Test
    public void testRangeUpdateAndSearch(){

        Integer[] ar = new Integer[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        IntegerSegmentTree s = new IntegerSegmentTree(ar);

        s.update(0, 6, 0);//0 0 0 0 0 0 0 -1 7
        s.update(4, 8, 1);//0 0 0 0 1 1 1 1 1



        assertEquals(0, s.aggregation(0, 3));
        assertEquals(5, s.aggregation(4, 8));

        s.update(3, 7, 2);//0 0 0 2 2 2 2 2 1


        assertEquals(2*5 + 1, s.aggregation(0, 8));


        s.update(0, 0, 100);//100 0 0 2 2 2 2 2 1

        assertEquals(2*5 + 1 + 100, s.aggregation(0, 8));

        s.update(0, 3, 100);//100 0 0 2 2 2 2 2 1
        s.update(1, 3, 1);//100 1 1 1 2 2 2 2 1

        assertEquals(1, (int)ar[s.search(0, 3)]);
        assertEquals(2, (int) ar[s.search(4, 6)]);

        out.println(s);


    }


    /***
     * Testing Inherit ed Behaviors
     */


    @Test
    public void testSearch(){

        //Integer[] ar = new Integer[] {1};

        //SegmentTree<Integer> s = new SegmentTree<>(ar, SegmentTree.min(), SegmentTree.RSQ());


        Integer[] ar = new Integer[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        IntegerSegmentTree s = new IntegerSegmentTree(ar);

        assertEquals(1,(int) ar[ s.search(0, 0)] );
        assertEquals(8,(int) ar[ s.search(8, 8)] );
        assertEquals(-2, (int)ar[ s.search(0, 8)] );
        assertEquals(-2, (int)ar[ s.search(0, 4)] );
        assertEquals(-1, (int)ar[ s.search(4, 8)] );
        assertEquals(-2, (int)ar[ s.search(3, 8)] );
        assertEquals(-1, (int)ar[ s.search(4, 6)] );

    }

    @Test
    public void testSearchUpdate(){

        //Integer[] ar = new Integer[] {1};

        //SegmentTree<Integer> s = new SegmentTree<>(ar, SegmentTree.min(), SegmentTree.RSQ());


        Integer[] ar = new Integer[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        IntegerSegmentTree s = new IntegerSegmentTree(ar);

        s.update(3, 4);

        //System.out.println(s);

        assertEquals(-1,(int) ar[ s.search(0, 8)] );
        assertEquals( 1,(int) ar[ s.search(0, 4)] );
        assertEquals(-1, (int)ar[ s.search(4, 8)] );

        s.update(8, -10);
        assertEquals(-10, (int)ar[ s.search(0, 8)] );
        assertEquals(-10, (int)ar[ s.search(4, 8)] );

    }

    @Test
    public void testAgregation(){

        Integer[] ar = new Integer[]{1, 1, 1, 1, 2, 2, 2, 2, 2};

        IntegerSegmentTree s = new IntegerSegmentTree(ar);

        assertEquals(1 * 4 + 2 * 5, s.aggregation(0, 8));
        assertEquals(1*4 , s.aggregation(0, 3) );
        assertEquals(2*5 , s.aggregation(4, 8) );
        assertEquals(1 + 2 , s.aggregation(3, 4) );
        assertEquals(1*2 + 2*2 , s.aggregation(2, 5) );
    }

    @Test
    public void testAgregationUpdate(){

        Integer[] ar = new Integer[]{1, 1, 1, 1, 2, 2, 2, 2, 2};

        IntegerSegmentTree s = new IntegerSegmentTree(ar);

        s.update(4, 0); // {1, 1, 1, 1, 0, 2, 2, 2, 2};
        //System.out.println(s);

        assertEquals(0, s.aggregation(4, 4));
        assertEquals(1 * 4 + 2*4 , s.aggregation(0, 8) );
        assertEquals(1*4 , s.aggregation(0, 3) );
        assertEquals(2*4 , s.aggregation(4, 8) );
        assertEquals(1 , s.aggregation(3, 4) );
        assertEquals(1*2 + 1*2 , s.aggregation(2, 5) );


    }

    @Test
    public void test3SizeArray() {
        Integer[] ar = new Integer[]{1, 2, 3};
        IntegerSegmentTree s = new IntegerSegmentTree(ar);

        assertEquals(6,  s.aggregation(0, 2) );
        assertEquals(3, s.aggregation(0, 1) );

        assertEquals(1, (int)ar[ s.search(0, 2) ] );
        assertEquals(5, s.aggregation(1, 2) );

        s.update(1, 3);// {1, 3, 3 }

        assertEquals(1, (int)ar[  s.search(0, 2)] );
        assertEquals(6,  s.aggregation(1, 2) );
        assertEquals(7, s.aggregation(0, 2) );

        s.update(2, 2); // {1, 3, 2 }
        assertEquals(6, s.aggregation(0, 2) );
        assertEquals(4,  s.aggregation(0, 1) );


    }

    @Test
    public void test1SizeArray() {
        Integer[] ar = new Integer[]{1};
        IntegerSegmentTree s = new IntegerSegmentTree(ar);

        assertEquals(1,  s.aggregation(0, 0) );
        assertEquals(1, (int)ar[ (int) s.search(0, 0)] );

        s.update(0, 3);

        assertEquals(3, s.aggregation(0, 0) );
        assertEquals(3,(int) ar[ (int) s.search(0, 0)] );

    }

    @Test
    public void test2SizeArray() {
        Integer[] ar = new Integer[]{1, 2};
        IntegerSegmentTree s = new IntegerSegmentTree(ar);

        assertEquals(3, s.aggregation(0, 1) );
        assertEquals(2,s.aggregation(1, 1) );


        assertEquals(1,(int) ar[ (int) s.search(0, 1)] );
        assertEquals(2, (int)ar[ (int) s.search(1, 1)] );

        s.update(0, 3);

        assertEquals(3, s.aggregation(0, 0));
        assertEquals(5,  s.aggregation(0, 1) );
        assertEquals(2, (int)ar[(int) s.search(0, 1)] );

    }




    public static void main(String args[]) {
        Integer[] ar = new Integer[]{1, 3, 45, 2, 13, 40, 100, 1, 32};

        IntegerSegmentTree s = new IntegerSegmentTree(ar);

        out.println(s);

        s.update(6,-2);

        out.println();
        out.println(s);

        out.println(ar[s.search(1, 5)]);

        out.close();

    }

}
