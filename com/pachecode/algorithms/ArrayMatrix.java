package com.pachecode.algorithms;

import java.util.Iterator;

/**
 * A Matrix that can be traversed as a traditional matrix or a normal vector
 * @author ricardodpsx@gmail.com
 *
 * @param <T> the time of the matrix
 */
public class ArrayMatrix <T> implements Iterable<T>{

	public static void main(String[] args) {
		
		ArrayMatrix<Integer> m = new ArrayMatrix<>(new Integer[][]{{1,2,3},{4,5,6},{7,8,9},{10,11,12}});
		
		
		for(Integer val : m){
			System.out.println(val);
		}
		

	}
	
	private T[] array;
	
	public final int width, height;
	
	public ArrayMatrix(int width, int height){
		this.width = width;
		this.height = height;
		array = (T[]) new Object[width * height];
	}
	
	public ArrayMatrix(T[][] m){
		this(m[0].length, m.length);
		
		for(int i =0; i < height; i++)
			for(int j =0; j < width; j++)
				this.put(i,j,m[i][j]);
	}
	
	public boolean outOfBounds(int i, int j){
		return i >= height || j >= width || i < 0 || j < 0;
	}
	
	public void put(int i, T val ){
		array[i] = val;
	}
	
	public void put(int i, int j,T val){
		array[i*width + j] = val;
	}
	public T get(int i, int j){
		if(outOfBounds(i,j))
			throw new IndexOutOfBoundsException();
		
		
		return array[i*width + j];
	}
	
	public T get(int i, int j, T def){
		if(outOfBounds(i,j))
			return def;
		
		return array[i*width + j];
	}
	
	
	public T get(int i){
		return array[i];
	}

	@Override
	public Iterator<T> iterator() {

		return new ArrayMatrixIterator();
	}
	
	public class  ArrayMatrixIterator implements Iterator<T>{
		int i =0 ;
		@Override
		public boolean hasNext() {
			return i < array.length;
		}

		@Override
		public T next() {
			return array[i++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	public interface Each<T>{
		public void each(int i, int j, T val, boolean rowEnd);
	}


}
