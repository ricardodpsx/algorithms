package com.pachecode.algorithms.old;

import com.pachecode.algorithms.old.SegmentTree;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

/**
 * Created by ricardodpsx@gmail.com on 4/01/15.
 */
public class SegmentTreeTest {

    Integer[][] arrays;
    Integer[] mins, sums;

    static PrintStream out = System.out;

    @Before
    public void setUp() {

    }

    @Test
    public void testLazy(){
        Integer[] ar = new Integer[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        SegmentTree s = new SegmentTree(ar, SegmentTree.naturalOrder, SegmentTree.sum());

        out.println( ar[s.search(0, 3)]);
        out.println(s);


        s.update(0, new Integer[]{1,1,1, 1} );
        s.update(4, new Integer[]{1,1} );

        out.println(s);
    }

    @Test
    public void testSearch(){

        //Integer[] ar = new Integer[] {1};

        //SegmentTree<Integer> s = new SegmentTree<>(ar, SegmentTree.min(), SegmentTree.sum());


        Integer[] ar = new Integer[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        SegmentTree s = new SegmentTree(ar, SegmentTree.naturalOrder, SegmentTree.sum());//adding minimal as indexer and sum as aggregator

        assertEquals(1, ar[ s.search(0, 0)] );
        assertEquals(8, ar[ s.search(8, 8)] );
        assertEquals(-2, ar[ s.search(0, 8)] );
        assertEquals(-2, ar[ s.search(0, 4)] );
        assertEquals(-1, ar[ s.search(4, 8)] );
        assertEquals(-2, ar[ s.search(3, 8)] );
        assertEquals(-1, ar[ s.search(4, 6)] );

    }

    @Test
    public void testSearchUpdate(){

        //Integer[] ar = new Integer[] {1};

        //SegmentTree<Integer> s = new SegmentTree<>(ar, SegmentTree.min(), SegmentTree.sum());


        Integer[] ar = new Integer[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        SegmentTree s = new SegmentTree(ar, SegmentTree.naturalOrder, SegmentTree.sum());//adding minimal as indexer and sum as aggregator

        s.update(3, 4);

        System.out.println(s);

        assertEquals(-1, ar[ s.search(0, 8)] );
        assertEquals( 1, ar[ s.search(0, 4)] );
        assertEquals(-1, ar[ s.search(4, 8)] );

        s.update(8, -10);
        assertEquals(-10, ar[ s.search(0, 8)] );
        assertEquals(-10, ar[ s.search(4, 8)] );

    }

    @Test
    public void testAgregation(){

        Integer[] ar = new Integer[]{1, 1, 1, 1, 2, 2, 2, 2, 2};

        SegmentTree s = new SegmentTree(ar, SegmentTree.naturalOrder, SegmentTree.sum());


        assertEquals(1*4 + 2*5 , s.aggregation(0, 8) );
        assertEquals(1*4 , s.aggregation(0, 3) );
        assertEquals(2*5 , s.aggregation(4, 8) );
        assertEquals(1 + 2 , s.aggregation(3, 4) );
        assertEquals(1*2 + 2*2 , s.aggregation(2, 5) );
    }

    @Test
    public void testAgregationUpdate(){

        Integer[] ar = new Integer[]{1, 1, 1, 1, 2, 2, 2, 2, 2};

        SegmentTree s = new SegmentTree(ar, SegmentTree.naturalOrder, SegmentTree.sum());

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
        SegmentTree s = new SegmentTree(ar, SegmentTree.naturalOrder, SegmentTree.sum());//adding minimal as indexer and sum as aggregator

        assertEquals(6,  s.aggregation(0, 2) );
        assertEquals(3, s.aggregation(0, 1) );

        assertEquals(1, ar[ s.search(0, 2) ] );
        assertEquals(5, s.aggregation(1, 2) );

        s.update(1, 3);// {1, 3, 3 }

        assertEquals(1, ar[  s.search(0, 2)] );
        assertEquals(6,  s.aggregation(1, 2) );
        assertEquals(7, s.aggregation(0, 2) );

        s.update(2, 2); // {1, 3, 2 }
        assertEquals(6, s.aggregation(0, 2) );
        assertEquals(4,  s.aggregation(0, 1) );


    }

    @Test
    public void test1SizeArray() {
        Integer[] ar = new Integer[]{1};
        SegmentTree s = new SegmentTree(ar, SegmentTree.naturalOrder, SegmentTree.sum());//adding minimal as indexer and sum as aggregator

        assertEquals(1,  s.aggregation(0, 0) );
        assertEquals(1, ar[ (int) s.search(0, 0)] );

        s.update(0, 3);

        assertEquals(3, s.aggregation(0, 0) );
        assertEquals(3, ar[ (int) s.search(0, 0)] );

    }

    @Test
    public void test2SizeArray() {
        Integer[] ar = new Integer[]{1, 2};
        SegmentTree s = new SegmentTree(ar, SegmentTree.naturalOrder, SegmentTree.sum());//adding minimal as indexer and sum as aggregator

        assertEquals(3, s.aggregation(0, 1) );
        assertEquals(2,s.aggregation(1, 1) );


        assertEquals(1, ar[ (int) s.search(0, 1)] );
        assertEquals(2, ar[ (int) s.search(1, 1)] );

        s.update(0, 3);

        assertEquals(3, s.aggregation(0, 0));
        assertEquals(5,  s.aggregation(0, 1) );
        assertEquals(2, ar[(int) s.search(0, 1)] );

    }

    @Test
    public void testRangeUpdate(){
        Integer[] ar = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0};


        SegmentTree s = new SegmentTree(ar, SegmentTree.naturalOrder, SegmentTree.sum());

        s.update(0, new Integer[]{1,1,1, 1} );
        s.update(4, new Integer[]{1,1} );


        out.println(s);

        assertEquals(5, (Integer) s.aggregation(0, 4) );
        out.println(s);

        s.update(7, new Integer[]{2, 2} );

        assertEquals(0, ar[ (Integer) s.search(6, 8) ] );

        s.update(0, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8 } );

        out.println(s);
        assertEquals(0, ar[ (Integer) s.search(0, 5) ] );
        assertEquals(5, ar[ (Integer) s.search(5, 8) ] );

    }

    public static void main(String args[]) {
        Integer[] ar = new Integer[]{1, 3, 45, 2, 13, 40, 100, 1, 32};

        SegmentTree s = new SegmentTree(ar, SegmentTree.naturalOrder, SegmentTree.sum());//adding minimal as indexer and sum as aggregator

        out.println(s);

        s.update(6,-2);

        out.println();
        out.println(s);

        out.println(ar[s.search(1, 5)]);

        out.close();

    }
}
