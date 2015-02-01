package com.pachecode.algorithms.test;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import com.pachecode.algorithms.MultiTreeSet;


public class MultiTreeSetTest {
	MultiTreeSet<Integer> m, mi;
	@Before
	public void setUp(){
		m = new MultiTreeSet<>();
		m.add(1);
		m.add(3);
		m.add(1);
		m.add(1);
		m.add(2);
		
		mi = new MultiTreeSet<Integer>(Collections.reverseOrder());
		mi.add(1);
		mi.add(1);
		mi.add(3);
		mi.add(1);
		mi.add(2);
		
	}
	
	@Test
	public void testAdding(){
		String str = "";
		for(Integer i: m){
			str += i;
		}
		assertEquals("11123",str);
	}
	@Test
	public void traverseReverse(){
		String str = "";
		for(Integer i: mi){
			str += i;
		}
		assertEquals("32111",str);
	}
	
	@Test
	public void testMaxAndMin(){
		m.add(30);
		m.add(-1);
		assertEquals(30,(int) m.last());
		assertEquals(-1,(int) m.first());
	}
	@Test
	public void testRemove(){
		assertEquals(0, m.count(5));
		m.add(5);
		m.add(5);
		m.add(5);
		assertEquals(3, m.count(5));
		m.remove(5);
		assertEquals(2, m.count(5));
		m.remove(5);
		assertEquals(1, m.count(5));
		m.remove(5);
		assertEquals(0, m.count(5));
	}
	@Test
	public void testRemoveSeveral(){
		assertEquals(0, m.count(5));
		m.add(5);
		m.add(5);
		m.add(5);
		assertEquals(3, m.count(5));
		m.remove(5, 3);
		assertEquals(0, m.count(5));
	}
	
	@Test(expected= NoSuchElementException.class)
	public void testRemoveExeption(){
		assertEquals(0, m.count(5));
		m.remove(5);
	}
	
	@Test
	public void testCount(){
		m.remove(1, 3);
		assertEquals(0, m.count(1));
		m.add(1);
		assertEquals(1, m.count(1));
		m.add(1);
		m.add(1);
		m.add(1);
		assertEquals(4, m.count(1));
		m.remove(1);
		assertEquals(3, m.count(1));
	}
	@Test(expected=NoSuchElementException.class)
	public void testCountException(){
		m.remove(1, 4);
	}
	
	public void testSize(){
		assertEquals(5, m.size());
		m.remove(1,2);
		assertEquals(5 - 2, m.size());
		m.add(9, 11);
		assertEquals(5 - 2 + 11, m.size());
	}
	
	@Test
	public void testIterator(){
		Iterator<Integer> it = m.iterator();
		assertEquals(1, (int) it.next());
		assertEquals(1, (int) it.next());
		assertEquals(1, (int) it.next());
		assertEquals(2, (int) it.next());
		assertEquals(3, (int) it.next());
		assertFalse(it.hasNext());
		
	}
	
	@Test
	public void testAddDelete(){
		m = new MultiTreeSet<>();
		m.add(20);
		m.add(10);
		m.remove(10);
		m.add(10);
		m.add(10);
		m.remove(20);
		assertEquals("10 10 ", m.toString());
		
	
		
	}
	
	@Test
	public void testTraversingNull(){
	
		try{
			for(Integer i: m){
				System.out.println(i);
			}
		}catch(Exception e){
			fail("Traversing an empty set shouldn't cause exceptions");
		}
		
	}
}