package com.pachecode.algorithms;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 *
 * A Multi Tree Set useful for Sorted Multi Groups.
 *
 * For example you have numbers like 3,3,2,2,1
 *
 * You want to save them in a sorted way with taking in account how much items do you have for each.
 * So if you make multiTreeSet.delete(3) you still have ONE remaining 3.
 *
 *
 * @param <T>
 */

public class MultiTreeSet <T> implements Iterable <T> {
	
	private TreeMap<T, Integer> ts = new TreeMap<T, Integer>();
	private int size = 0;
	
	public MultiTreeSet(){
		ts = new TreeMap<T, Integer>();
	}
	
	public MultiTreeSet(Comparator<? super T> comparator){
		ts = new TreeMap<T, Integer>(comparator);
	}
	
	public int size(){
		return size;
	}
	
	public void add(T k, int count){
		Integer val =  ts.get(k);
		if( val != null )
			ts.put(k, val + count);			
		else
			ts.put(k, count);
			
		
		size += count;
	}

	public void add(T k){
		add(k, 1);
	}
	

	public T first() {
		return ts.firstKey();
	}

	public T last() {
		return ts.lastKey();
	}
	
	public int count(T key){
		Integer val = ts.get(key); 
		return val == null ? 0: val ;
	}
	
	
	
	//Remove one item
	public void remove(T k){
		remove(k, 1);
	}
	//Remove several items
	public void remove(T k, int rmCount){
		Integer count =  ts.get(k);
		
		if(count == null)
			throw new NoSuchElementException();
		
		if( count - rmCount < 0 )
			throw new NoSuchElementException("Trying to remove " + rmCount + " items, only " + count + " available");		
		else if( count - rmCount == 0){
			ts.remove(k);
			size -= count - rmCount;
		}
		else{
			ts.put(k, count - rmCount);
			size -= rmCount;
		}
	}
	
	 
	
	public boolean contains(T k){
		return ts.containsKey(k);
	}

	@Override
	public Iterator<T> iterator() {
		return new MultiTreeSetIterator();
	}	
	
	class MultiTreeSetIterator implements Iterator<T>{
		Iterator<T> it;
		int count;
		T current;
		
		public MultiTreeSetIterator(){
			it = ts.keySet().iterator();	
			 
			 if(it.hasNext()){			 
				 current = it.next();
				 count = ts.get(current);
			 }else{
				 count = 0;
			 }
		}
		@Override
		public boolean hasNext() {
			return it.hasNext() || count > 0;
		}

		@Override
		public T next() {
			if(count > 0){
				count--;
				return current;
			}else{
				current = it.next();
				count = ts.get(current); 
				count--;
				return current;
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();			
		}
		
	}
	@Override
	public String toString(){
		StringBuilder out = new StringBuilder();
		for(T i: this) {
			out.append(i + " ");
		}
		return out.toString();
	}
	
}
