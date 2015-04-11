package com.pachecode.algorithms.test;

/**
 * Created by ricardo on 31/12/14.
 */
import static org.junit.Assert.*;

import com.pachecode.algorithms.PartitionArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;


public class PartitionArrayTest {

    PartitionArray<Integer> array;

    @Before
    public void setUp() {
        array = new PartitionArray<>(new Integer[]{1,2,3,4,5});
    }

    @Test
    public void testCreation(){
        array = new PartitionArray<>(new Integer[]{1,2,3,4,5});
        assertEquals(array.getArray(), new Integer[]{1, 2, 3, 4, 5});
        assertEquals(5, array.size());
        assertEquals(2, (int) array.get(1));

        array = new PartitionArray<>(20);

        assertEquals(20, array.size());

        array.set(15, 99);

        assertEquals((int) array.get(15), 99);


        try {
            array = new PartitionArray<>(new Integer[]{1, 2, 3, 4, 5}, 0, 6);
            fail();
        }
        catch(IndexOutOfBoundsException e){
            //Passs
        }

        array = new PartitionArray<>(new Integer[]{1, 2, 3, 4, 5}, 2, 1); // [3]
        assertEquals(1, (int) array.size());
        assertEquals(3, (int) array.get(0));

    }

    @Test
    public void testRightLeft() {

        assertEquals(array.left().getArray(), new Integer[]{1, 2});
        assertEquals(array.left().left().getArray(), new Integer[]{1});
        assertEquals(array.left().right().getArray(), new Integer[]{2});

        assertTrue(array.canSplit());
        assertTrue(array.left().canSplit());
        assertTrue(array.right().canSplit());

        assertFalse(array.left().left().left().left().left().canSplit());
        assertFalse(array.left().left().left().canSplit());
        assertFalse(array.left().left().right().canSplit());



        assertEquals(array.right().getArray(), new Integer[]{3, 4, 5});
        assertEquals(array.right().left().getArray(), new Integer[]{3});
        assertEquals(array.right().right().getArray(), new Integer[]{4, 5});
        assertEquals(array.right().right().left().getArray(), new Integer[]{4});
        assertEquals(array.right().right().right().getArray(), new Integer[]{5});

        assertFalse(array.right().left().right().canSplit());

    }


    @Test
    public void testAccess(){

        assertEquals(1, (int) array.left().get(0));
        assertEquals(2, (int) array.left().get(1));
        assertEquals(2, (int) array.left().size());

        assertEquals(3, (int) array.right().get(0));
        assertEquals(5, (int) array.right().get(2));
        assertEquals(3, (int) array.right().size());


        try{
            array.left().get(2);
            fail("This should cause an exception becouse position 2 is bigger than the size of the left (Which is 2)");
        }catch (IndexOutOfBoundsException e) {
            //Pass
        }


        try{
            array.left().get(3);
            fail("This should cause an exception becouse position 3 is bigger than the size of the right (Which is 3)");
        }catch (IndexOutOfBoundsException e) {
            //Pass
        }

    }

    @Test
    public void testSet(){
        array.left().set(0, 100);
        assertEquals(100,(int)  array.get(0));

        array.right().set(0, 99);
        assertEquals(99, (int) array.get(2));

        array.right().right().set(0, 98);
        assertEquals(98, (int) array.get(3));

    }

    @Test
    public void testIterable(){
        String out = "";
        for(Integer i: array) {
            out += i;
        }
        assertEquals("12345", out);
    }

    @Test
    public void testSlice() {
        PartitionArray<Integer> array2 = array.slice(1,3);

        assertEquals(3, array2.size());
        assertEquals(array2.getArray(), new Integer[]{2, 3, 4});
        assertEquals(2,(int)  array2.get(0));

    }

    @Test
    public void testJoin() {
        assertEquals(array.left().join(array.right()).getArray(), array.getArray());

        assertEquals(array.right().join(array.left()).getArray(), array.getArray());

        assertEquals(new Integer[]{2, 3, 4, 5} , array.right().join(array.left().right()).getArray() ); //{1, 2, 3, 4, 5}

    }

    @Test
    public void testPartitionWithPivot() {
        //{1, 2, 3, 4, 5}

        assertEquals(new Integer[]{1, 2}, array.leftWithPivot().getArray());
        assertEquals( new Integer[]{3} ,array.pivot().getArray());
        assertEquals( new Integer[]{4, 5 } ,array.rightWithPivot().getArray());


        //Odd array
        PartitionArray<Integer> oddArray = new PartitionArray<Integer>(new Integer[]{1,2,3,4});

        assertEquals(new Integer[]{1, 2}, oddArray.leftWithPivot().getArray());
        assertEquals( new Integer[]{3} ,oddArray.pivot().getArray());
        assertEquals( new Integer[]{4} ,oddArray.rightWithPivot().getArray());


        //Small array
        PartitionArray<Integer> smallArray = new PartitionArray<Integer>(new Integer[]{1});

        assertEquals(new Integer[]{}, smallArray.leftWithPivot().getArray());
        assertEquals( new Integer[]{1} ,smallArray.pivot().getArray());
        assertEquals( new Integer[]{} ,smallArray.rightWithPivot().getArray());



        //Size two array
        PartitionArray<Integer> sizeTwo = new PartitionArray<Integer>(new Integer[]{1,2});

        assertEquals(new Integer[]{1}, sizeTwo.leftWithPivot().getArray());
        assertEquals( new Integer[]{2} ,sizeTwo.pivot().getArray());
        assertEquals( new Integer[]{} ,sizeTwo.rightWithPivot().getArray());


        //Size three array
        PartitionArray<Integer> sizeThree = new PartitionArray<Integer>(new Integer[]{1,2,3});

        assertEquals(new Integer[]{1}, sizeThree.leftWithPivot().getArray());
        assertEquals( new Integer[]{2} ,sizeThree.pivot().getArray());
        assertEquals( new Integer[]{3} ,sizeThree.rightWithPivot().getArray());


    }

    static PrintWriter out = new PrintWriter(System.out, true);
    static public void main(String args[]) {
        PartitionArray<Integer> array = new PartitionArray<>(new Integer[]{1,2,3,4,5});

        array.print();

        array.left().print();
        array.right().print();

        for(Integer i: array) {
            out.print(i + ", ");
        }
        out.println();


        array.left().set(0, 100);
        array.right().set(0, 100);

        array.left().print();
        array.print();



        out.close();
    }




}
