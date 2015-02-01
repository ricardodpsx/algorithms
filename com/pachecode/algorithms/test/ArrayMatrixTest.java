package com.pachecode.algorithms.test;
import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.signum;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pachecode.algorithms.ArrayMatrix;


public class ArrayMatrixTest {
	Integer[][] m;
	ArrayMatrix<Integer> am;

	@Before
	public void setUp() throws Exception {
		m = new Integer[][]{{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
		am = new ArrayMatrix<>(m);
	}

	
	@Test
	public void traversing(){
	
		int c = 0;
		for(int i =0; i < am.height; i++)
			for(int j =0; j < am.width; j++){
				assertEquals(m[i][j], am.get(i,j));
				assertEquals(m[i][j], am.get(c++));
			}
	}
	
	@Test
	public void traversing2(){
	
		int c = 0;
		String str1 = "", str2 = "";
		for(Integer in: am){
			str1 += in;
		}
		for(int i =0; i < am.height; i++)
			for(int j =0; j < am.width; j++){
				str2 += m[i][j];
			}
		
		assertEquals(str2, str1);
	}
	

	
	@Test(expected=IndexOutOfBoundsException.class)
	public void verifyArrayOutOfBounds(){
		am.get(12);
	}
	@Test
	public void verifyArrayBounds(){
		assertEquals(12, (int) am.get(11));
	}
	@Test
	public void verifyMatrixBounds(){
		assertEquals(12, (int) am.get(3,2));
	}
	@Test(expected=IndexOutOfBoundsException.class)
	public void verifyMatrixBoundsException(){
		assertEquals(12, (int) am.get(3,3));
	}
	@Test(expected=IndexOutOfBoundsException.class)
	public void verifyMatrixBoundsException2(){
		am.get(0,3);
	}
	
	
}
