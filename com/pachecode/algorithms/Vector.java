package com.pachecode.algorithms;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * A class to easy play with Vectors in R2, for more complex
 * applications use princeton.algs4.algorithms.Vector
 * @author ricardodpsx@gmai.com
 *
 */
public class Vector{
	public final double x, y;
	
	private final static double EPSILON = 0.00001;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;		
	}
	
	public Vector add(Vector v){
		return new Vector(x + v.x, y + v.y);
	}
	
	
	public double dot(Vector v){
		return v.x * x + v.y * y;
	}
	
	//Matrix transform in R2
	public Vector trans(double[][] mat){
		return new Vector( mat[0][0]*x + mat[0][1]*y, mat[1][0]*x + mat[1][1]*y );
	}
	
	
	public Vector rotate(double rot){
		//Rotation matrix
		double[][] r = 
			{{cos(rot), -sin(rot)},
			 {sin(rot) , cos(rot)}};
		
		return this.trans(r);
	}
	
	public double magnitude(){
		return Math.sqrt(x*x + y*y);
	}

	@Override
	public boolean equals(Object o){
		
		if(!(o instanceof Vector)) return false;
		
		Vector v = (Vector) o;
		
		return Math.abs(x - v.x) <= EPSILON && Math.abs(y - v.y) <= EPSILON;
		
	}
	
	@Override
	public String toString(){
		return String.format("(%.3f, %.3f)", x, y);
	}
}