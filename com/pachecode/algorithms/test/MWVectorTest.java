package com.pachecode.algorithms.test;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.signum;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pachecode.algorithms.MWVector;


public class MWVectorTest {
	
	static Integer[][] M = new Integer[][]
			{
			 	{1,  2,  3,  4,  5},
			 	{6,  7,  8,  9,  10},
			 	{11, 12, 13, 14, 15},
			};
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEquals() {		
		assertTrue((new MWVector(0, 0)).equals(new MWVector(0, 0)));		
	}
	
	@Test
	public void testBoundChecking(){
		
		
		MWVector pos = new MWVector(0, 0);
		assertTrue(pos.check(M));
		
		pos = new MWVector(0, M[0].length);
		assertTrue(pos.outOfBounds(M));
		assertFalse(pos.check(M));
		
		pos = new MWVector(M.length, 0);
		assertTrue(pos.outOfBounds(M));
		assertFalse(pos.check(M));
		
		pos = new MWVector(M.length - 1, M[0].length - 1);
	}
	
	@Test
	public void testGet(){
		MWVector pos = new MWVector(0, 0);		
		assertEquals(1, (int)pos.get(M));
		assertEquals(2,(int)pos.add(new MWVector(0,1) ).get(M));
		
		
		assertEquals(2, (int)pos.add( new MWVector(0, 1)).get(M, -10) );		
		assertEquals(-10, (int)pos.add(new MWVector(0, 1000)).get(M, -10));
	}

	@Test
	public void testRotation(){

		assertEquals(new MWVector(-1,1), new MWVector(0,1).sRotate45CCW());
		assertEquals(new MWVector(-1,0), new MWVector(0,1).sRotate45CCW().sRotate45CCW());
		assertEquals(new MWVector(-1,0), new MWVector(0,1).sRotate90CCW());
		//A 360 rotation
		assertEquals(new MWVector(1,0), new MWVector(1,0).sRotate90CCW().sRotate45CCW().sRotate45CCW().sRotate90CCW().sRotate90CCW());
		
		//Rotate and go back
		assertEquals(new MWVector(1,0), new MWVector(1,0).sRotate90CW().sRotate90CCW());
		assertEquals(new MWVector(1,0), new MWVector(1,0).sRotate45CW().sRotate45CW().sRotate90CCW());
		
		assertEquals(new MWVector(1,1), new MWVector(1,0).sRotate45CCW());
		assertEquals(new MWVector(1,1), new MWVector(0,1).sRotate45CW());
		
		assertEquals(new MWVector(2,2), new MWVector(0,2).sRotate45CW());
	}
	
	
	
	
}
