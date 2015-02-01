package com.pachecode.algorithms.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pachecode.algorithms.Vector;

public class VectorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEquals() {
		
		assertTrue((new Vector(2.3, 1.9)).equals(new Vector(2.3, 1.9)));		
	}
	
	@Test
	public void testDot() {
		assertEquals(new Vector(3, 3).dot(new Vector(5,7)),(double) 3*5 + 3*7, 0.0001 );
	}
	
	@Test
	public void testAdd() {
		assertEquals(new Vector(1, 2).add(new Vector(3, 4)), new Vector(1 + 3, 2 + 4) );
	}
	
	@Test
	public void testRotate() {
		assertEquals(new Vector(1, 0).rotate(Math.PI/2), new Vector(0, 1));
	}
	
	@Test
	public void testDotOrthogonal() {
		
		assertEquals("It two vectors are orthogonal the dot product should be 0",
				new Vector(1, 0).dot(new Vector(1,0).rotate(Math.PI/2)), 
				0,
				0.0001
				);
	}
	
	@Test
	public void testIdentityTrans(){
		assertEquals(
					new Vector(5.33, 4.233).trans(new double[][] {{1, 0}, {0, 1}}),
					new Vector(5.33, 4.233)
				);
		
	}
	
	@Test
	public void testReflection(){
		assertEquals(
				new Vector(1, 0).trans(new double[][] { {0, 1}, {1, 0} } ),
				new Vector(0, 1)
				);
		
		assertEquals(
				new Vector(0, 1).trans(new double[][] { {0, 1}, {1, 0} } ),
				new Vector(1, 0)
				);
		
	}
	
	
	

}
