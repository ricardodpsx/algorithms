package com.pachecode.algorithms;

import static java.lang.Math.*;

/**
 * Matrix Walker Vector
 * A vector to work with Matrix a more natural way (Using i, j instead of translating everything)
 * See examples in main 
 * 
 * NOTICE: Should be a non-dented matrix!
 *  
 * @author ric-personal
 *
 */
public class MWVector {

	final int i, j;
	public MWVector(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	/*
	 * This sRotateXX functions are for Square-Rotations, that is
	 *   ***
	 *   *x*
	 *   ***
	 * So with this "rotations" you can rotate your vector CW or CCW thru all the diagonals adjascent to
	 * the x
	 *   
	 */
	public MWVector sRotate90CW(){
		Vector v = new Vector(i, j);
		v = v.rotate(-PI/2.);
		return new MWVector((int)v.x , (int)v.y );
	}
	public MWVector sRotate90CCW(){
		Vector v = new Vector(i, j);
		v = v.rotate(PI/2.);
		return new MWVector((int)v.x , (int)v.y );
	}
	
	public MWVector sRotate45CW(){
		Vector v = new Vector(i, j);
		v = v.rotate(-PI/4.);
		//We just want to have the sign of the rotated vector, not its magnitude
	    //The magnitud can not be bigger than the magnitud of any of the sides
		int pi = (int) (signum(round(v.x))*max(i, j));
		int pj = (int) (signum(round(v.y))*max(i, j));
		return new MWVector(pi, pj);
	}
	public MWVector sRotate45CCW(){
		Vector v = new Vector(i, j);
		v = v.rotate(PI/4.);
		
	   //We just want to have the sign of the rotated vector, not its magnitude
	   //The magnitud can not be bigger than the magnitud of any of the sides
	   int pi = (int) (signum(round(v.x))*max(i, j));
	   int pj = (int) (signum(round(v.y))*max(i, j));
		
		return new MWVector(pi, pj);
	}
	

	
	public MWVector add(MWVector v){
		return new MWVector(i + v.i, j + v.j);
	}
	
	//Check if the bounds of the matrix are not exceeded by this vector
	public <T>  boolean check(T[][] mat){
		return !outOfBounds(mat);
	}
	
	public <T>  boolean outOfBounds(T[][] mat){
		return  i >= mat.length || j >= mat[0].length || i < 0 || j < 0;
	}
	
	public <T> T get(T[][] mat) {
		return mat[i][j];
	}
	
	public <T> T get(T[][] mat, T defaultVal) {
		if(check(mat))
			return mat[i][j];
		return defaultVal;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof MWVector)) return false;
		MWVector v = (MWVector) o;
		return i == v.i && j == v.j;
	}
	
	public boolean is(int i, int j){
		return this.equals(new MWVector(i, j));
	}
	
	
	
	@Override
	public String toString(){
		return String.format("(%d, %d)", i,j);
	}
	
	public static void main(String [] args){
		
		Integer[][] someMat = new Integer[][]
				{
				 	{1,  2,  3,  4,  5},
				 	{6,  7,  8,  9,  10},
				 	{11, 12, 13, 14, 15},
				};
		
		System.out.println("Traversing the matrix from top-getLeft diagonal");
		/*
		 * Traverse the matrix from top-getLeft diagonal
		 * Normally you will need two vectors, a direction vector(How is the vector traversing the matrix)
		 * and a Position vector (Where is the vector getRight now)
		 */
		//
		MWVector dir = new MWVector(1, 1);
		MWVector pos = new MWVector(0, 0);
		
		while(pos.check(someMat)){
			System.out.println(someMat[pos.i][pos.j]);
			pos = pos.add(dir);
		}
		
		System.out.println("---\nGoing tru the borders of the matrix until it reachs the origin");
		
		//The cool thing is that you can rotate the direction
		pos = new MWVector(0, 0);
		dir = new MWVector(1, 0);
		do{
			if(pos.add(dir).outOfBounds(someMat))
				dir = dir.sRotate90CCW();
			else{
				pos = pos.add(dir);
				System.out.println(pos.get(someMat));
			}
			
		}
		while(!pos.is(0, 0));
		
	}
	
}
