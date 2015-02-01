package com.pachecode.algorithms;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * An abstraction to partition arrays in a way that you FEEL like having different arrays
 * (but you will actually work with the same)
 *
 * Useful for algorithms that deal with partitioned arrays like: MergeSort, BinnarySearch etc.
 *
 * This only make sense with fixed arrays.
 *
 * No matter how much splits you do, the internal subArray is always the same! no duplications
 *
 * If the subArray is odd and you slice it in two pieces (using getLeft/getRight) the bigger one will be always at getRight
 *
 * Created by ricardodpsx@gmail.com on 31/12/14.
 */
public class PartitionArray<T> implements Iterable<T>  {

    private final T[] array;
    private final Integer from;
    private final Integer length;

    public PartitionArray(T[] array) {

        from = 0;
        length = array.length;
        this.array = array;

    }

    public PartitionArray(int size) {
        array = (T[]) new Object[size];
        from = 0;
        length = size;

    }

    public PartitionArray(T[] array, int from, int length  ){

        if(length < 0 )
            throw new IndexOutOfBoundsException("length can not be negative");

        if(from + length > array.length)
            throw new IndexOutOfBoundsException("from + length can not be bigger than the subArray");

        if( from < 0 )
            throw new IndexOutOfBoundsException("from can not be negative");


        this.from = from;
        this.length = length;
        this.array = array;


    }


    public void set(int i, T value){
        checkBounds(i);
        array[from + i] = value;
    }

    public T get(int i){
        checkBounds(i);
        return array[from + i];
    }

    //Get/Set real subArray position
    public T getReal(int i){
        return array[i];
    }
    public void setReal(int i, T val) {
        array[i] = val;
    }


    public int size(){
        return length;
    }

    public boolean canSplit(){
        return this.size() > 1;
    }

    /**
     *
     * @return the segment of the subArray (not the original!)
     */
    public T[] getArray(){
        T[] res =(T[]) new Object[length];

        for(int i =0; i < length; i++) {
            res[i] = array[from + i];
        }

        return res;
    }

    public PartitionArray<T> slice(int from, int length){
        return new PartitionArray<T>(array,this.from + from, length);
    }

    public PartitionArray<T> left(){
        return new PartitionArray<>(array, leftFrom(), leftSize());
    }
    public int leftFrom(){    return  from;  };
    public int leftSize() {
        return length/2;
    }
    public int leftTo(){  return leftFrom() + leftSize() -1;  }



    public PartitionArray<T> right(){
        return new PartitionArray<>(array, rightFrom(),rightSize());
    }
    public int rightFrom(){
        return  from + length/2;
    };
    public int rightSize(){
        return  length - length/2;
    };
    public int rightTo(){ return rightFrom() + rightSize() - 1; }




    /** This three functions are to be used together, useful for BinnarySearch or QuickSort **/

    public PartitionArray<T> leftWithPivot() {
        return new PartitionArray<>(array, from, length/2);
    }

    /**
     * The getRight with pivot will be smaller or equal than the getLeft:
     * That is, the next always will be true:
     * getRight.size() == getLeft.size() OR getRight.size() - 1 = getLeft.size()
     * @return
     */
    public PartitionArray<T> rightWithPivot() {
        return new PartitionArray<>(array, from + length/2 + 1, length - length/2 - 1);
    }

    /**
     *
     * @return a PartitionArray of size 1, so you can join them
     */
    public PartitionArray<T> pivot(){
        return new PartitionArray<T>(array,from + length/2, 1);
    }

    /**
     *
     * @return a PartitionArray of size 1, so you can join them
     */
    public T pivotVal(){
        return pivot().get(0);
    }



    /** The absolute from of the subArray **/
    public int getFrom(){
        return from;
    }

    public int getTo() {
        return from + size() - 1;
    }


    /**
     *
     * @param other The subArray you want to join with this one
     * @return
     */
    public PartitionArray<T> join(PartitionArray<T> other) {
        return join(this, other);
    }

    /**
     * Join back to sliced arrays together.
     * The subArray reference should be the same AND they should be **Adjacent**
     * IE: this is valid join(subArray.getLeft(), subArray.getRight());
     *
     * @param a, b Two adjacent arrays to Join
     * @return The new joined subArray
     */
    public static<T> PartitionArray<T> join(PartitionArray<T> a, PartitionArray<T> b){


        if(!(a.array == b.array)) throw new UnsupportedOperationException("Only can join arrays that have Wraps same source subArray");

        PartitionArray<T> left, right;

        //Just swaping to have the getLeft on a and the getRight on b
        if(a.from > b.from){
            left = b;
            right = a;
        }
        else{
            left = a;
            right = b;
        }

        //Verify they are adjascent
        boolean areAdjascent = left.from + left.length == right.from;

        if(!areAdjascent) throw new UnsupportedOperationException("The arrays to join should be adjascent");

        return new PartitionArray<T>(left.array, left.from, right.length + left.length);
    }




    @Override
    public String toString(){
        return Arrays.toString(getArray());
    }


    public void print(){
        System.out.println(this.toString());
    }


    private void checkBounds(int pos) {
        if( from + pos < from || pos >= length ) throw new IndexOutOfBoundsException("Invalid pos: " + pos);
    }


    @Override
    public Iterator<T> iterator() {
        return new PartitionArrayIterator();
    }



    public class PartitionArrayIterator implements Iterator<T> {

        int i = 0;

        @Override
        public boolean hasNext() {
            return from + i < length;
        }

        @Override
        public T next() {
            return array[from + i++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }



}
