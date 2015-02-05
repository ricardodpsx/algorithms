package com.pachecode.algorithms.test;


import com.pachecode.algorithms.SegmentTreeLP;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static com.pachecode.algorithms.Dbg.pr;
import static org.junit.Assert.assertEquals;

/**
 * Created by ricardodpsx@gmail.com on 8/01/15.
 */
public class SegmentTreeLPTest {
    Integer[][] arrays;
    Integer[] mins, sums;

    static PrintStream out = System.out;

    @Before
    public void setUp() {

    }

    @Test
    public void main(){
        Integer[] ar = new Integer[]{1,0, 0, 0 , 1, 1};
        SegmentTreeLP s = new SegmentTreeLP(ar);

        s.updateRange(0,2, 1);
        pr(s.toArray());

    }


    @Test
    public void testRangeUpdate() {
        Integer[] ar = new Integer[]{0,0,0,0,0,0,0,0,0};
        SegmentTreeLP s = new SegmentTreeLP(ar);

        s.updateRange(3,5, 1);//0 0 0 1 1 1 0 0 0

        assertEquals(3, s.RSQ(0, 9));
        assertEquals(1, s.RMinQ(3, 5));
        assertEquals(0, s.RMinQ(0, 5));

        s.updateRange(0,6, 1);//1 1 1 1 1 1 1 0 0
        assertEquals(7, s.RSQ(0, 9));

        s.updateRange(2, 4, 0);//1 1 0 0 0 1 1 0 0

        //out.println(s);
        assertEquals(4, s.RSQ(0, 9));

        s.updateRange(0,9, new SegmentTreeLP.Toggle()); //0 0 1 1 1 0 0 1 1

        assertEquals(5, s.RSQ(0, 9));
        s.RSQ(0, 2);
        assertEquals(1, s.RSQ(0, 2));

    }

    @Test
    public void testRangeUpdate2() {
        Integer[] ar = new Integer[]{0,0,0,0,0,0,0,0,0};
        SegmentTreeLP s = new SegmentTreeLP(ar);
        s.updateRange(0,3, 1);//1 1 1 1 0 0 0 0 0

        assertEquals(1, s.RMinQ(1,2));
        assertEquals(2, s.RSQ(1, 2));


        s.updateRange(0,5, new SegmentTreeLP.Toggle()); //0 0 0 0 1 1 0 0 0
        s.updateRange(0,5, new SegmentTreeLP.Toggle());
        s.updateRange(0,5, new SegmentTreeLP.Toggle()); //0 0 0 0 1 1 0 0 0
        //out.println(s);
        assertEquals(0, s.RSQ(0,2));
        assertEquals(2, s.RSQ(0,8));

        s.updateRange(3,8, new SegmentTreeLP.Toggle());//0 0 0 1 0 0 1 1 1
        s.updateRange(2, 4, 2); //0 0 2 2 2 0 1 1 1
        s.updateRange(5, 7, new SegmentTreeLP.Toggle());//0 0 2 2 2 1 0 0 1
        assertEquals(5, s.RSQ(3, 7));
        assertEquals(2*3 + 1, s.RSQ(0, 5));
        assertEquals(2*3 + 1*2, s.RSQ(0, 9));

    }



    /***
     * Testing Inherit ed Behaviors
     */
    @Test
    public void testRMQ(){

        //Integer[] ar = new Integer[] {1};

        //SegmentTreeLP<Integer> s = new SegmentTreeLP<>(ar, SegmentTreeLP.min(), SegmentTreeLP.sum());


        Integer[] ar = new Integer[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        SegmentTreeLP s = new SegmentTreeLP(ar);

        assertEquals(1,  s.RMinQ(0, 0) );
        assertEquals(8,  s.RMinQ(8, 8) );
        assertEquals(-2, s.RMinQ(0, 8) );
        assertEquals(-2, s.RMinQ(0, 4));
        assertEquals(-1, s.RMinQ(4, 8) );
        assertEquals(-2, s.RMinQ(3, 8) );
        assertEquals(-1, s.RMinQ(4, 6) );

    }

    @Test
    public void testRMQUpdate(){


        Integer[] ar = new Integer[]{1, 2, 3, -2, 4, 5, -1, 7, 8};

        SegmentTreeLP s = new SegmentTreeLP(ar);

        s.update(3, 4);

        //System.out.println(s);

        assertEquals(-1, s.RMinQ(0, 8));
        assertEquals( 1,  s.RMinQ(0, 4) );
        assertEquals(-1, s.RMinQ(4, 8) );

        s.update(8, -10);
       // System.out.println(s);
        assertEquals(-10, s.RMinQ(0, 8) );
        assertEquals(-10, s.RMinQ(4, 8) );

    }

    @Test
    public void testRSQ(){

        Integer[] ar = new Integer[]{1, 1, 1, 1, 2, 2, 2, 2, 2};

        SegmentTreeLP s = new SegmentTreeLP(ar);

        assertEquals(1 * 4 + 2 * 5, s.RSQ(0, 8));
        assertEquals(1*4 , s.RSQ(0, 3) );
        assertEquals(2*5 , s.RSQ(4, 8) );
        assertEquals(1 + 2 , s.RSQ(3, 4) );
        assertEquals(1*2 + 2*2 , s.RSQ(2, 5) );
    }

    @Test
    public void testRSQUpdate(){

        Integer[] ar = new Integer[]{1, 1, 1, 1, 2, 2, 2, 2, 2};

        SegmentTreeLP s = new SegmentTreeLP(ar);

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
        SegmentTreeLP s = new SegmentTreeLP(ar);

        assertEquals(6,  s.RSQ(0, 2) );
        assertEquals(3, s.RSQ(0, 1) );

        assertEquals(1, s.RMinQ(0, 2) );
        assertEquals(5, s.RSQ(1, 2) );

        s.update(1, 3);// {1, 3, 3 }

        assertEquals(1, s.RMinQ(0, 2) );
        assertEquals(6,  s.RSQ(1, 2) );
        assertEquals(7, s.RSQ(0, 2) );

        s.update(2, 2); // {1, 3, 2 }
        assertEquals(6, s.RSQ(0, 2) );
        assertEquals(4,  s.RSQ(0, 1) );


    }

    @Test
    public void test1SizeArray() {
        Integer[] ar = new Integer[]{1};
        SegmentTreeLP s = new SegmentTreeLP(ar);

        assertEquals(1,  s.RSQ(0, 0) );
        assertEquals(1, s.RMinQ(0, 0));

        s.update(0, 3);

        assertEquals(3, s.RSQ(0, 0) );
        assertEquals(3, s.RMinQ(0, 0));

    }

    @Test
    public void test2SizeArray() {
        Integer[] ar = new Integer[]{1, 2};
        SegmentTreeLP s = new SegmentTreeLP(ar);

        assertEquals(3, s.RSQ(0, 1) );
        assertEquals(2,s.RSQ(1, 1) );


        assertEquals(1, s.RMinQ(0, 1) );
        assertEquals(2,  s.RMinQ(1, 1) );

        s.update(0, 3);

        assertEquals(3, s.RSQ(0, 0));
        assertEquals(5,  s.RSQ(0, 1) );
        assertEquals(2, s.RMinQ(0, 1) );

    }




    public static void main(String args[]) {
        Integer[] ar = new Integer[]{1, 3, 45, 2, 13, 40, 100, 1, 32};

        SegmentTreeLP s = new SegmentTreeLP(ar);

        out.println(s);

        s.update(6,-2);

        out.println();
        out.println(s);

        out.println( s.RMinQ(1, 5) );

        out.close();

    }

}
