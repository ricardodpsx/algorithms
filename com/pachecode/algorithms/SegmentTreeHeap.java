package com.pachecode.algorithms;

import java.util.Arrays;

/**
 * The <tt>SegmentTree</tt> class is an structure for efficient search of cummulative data.
 * It performs  Range Minimum Query and Range Sum Query in O(log(n)) time.
 * It can be easily customizable to support Range Max Query, Range Multiplication Query etc.
 *
 * Also it has been develop with  <tt>LazyPropagation</tt> for range updates, which means
 * when you perform update operations over a range, the update process affects the least nodes as possible
 * so that the bigger the range you want to update the less time it consumes to update it. Eventually those changes will be propagated
 * to the children and the whole array will be up to date.
 *
 *
 *
 * Example:
 *
 * SegmentTreeHeap st = new SegmentTreeHeap(new Integer[]{1,3,4,2,1, -2, 4});
 * st.update(0,3, 1)
 * In the above case only the node that represents the range [0,3] will be updated (and not their children) so in this case
 * the update task will be less than n*log(n)
 *
 * Warning: Due lazy propagation of changes the array used to initialize the SegmentTree will be no longer valid for using it externally.
 *
 */
public class SegmentTreeHeap {
    static Node[] heap;
    Integer[] array;
    int size;

    public SegmentTreeHeap(Integer[] array){
        this.array = array;
        int size = (int)(2 * Math.pow(2.0, Math.floor((Math.log((double) array.length) / Math.log(2.0)) + 1)));
        heap = new Node[size];
        build(1, 0, array.length);
    }

    void build(int v, int from, int size) {
        heap[v] = new Node();
        heap[v].from = from;
        heap[v].to = from + size -1;

        if( size == 1) {
            heap[v].sum = array[from];
            heap[v].min = array[from];
        }else{
            //Build childs
            build(2*v, from, size/2 );
            build( 2*v + 1, from + size/2, size - size/2 );

            heap[v].sum = heap[2*v].sum + heap[ 2*v + 1].sum;
            //min = min of the children
            heap[v].min = heap[2*v].min < heap[ 2*v + 1].min ? heap[2*v].min : heap[2*v + 1].min ;
        }
    }

    //Range Sum Query
    public int RSQ(int from, int to){
        return RSQ(1, from, to);
    }

    int RSQ(int v, int from, int to) {
        Node n = heap[v];


        //If you did a range update before, you can infer the RSQ of any range that falls inside this node
        if(n.pendingVal != null && contains(n.from, n.to, from, to) ) {
            return  ( to - from + 1) * n.pendingVal;
        }

        if( contains(from, to, n.from, n.to) ) {
            return heap[v].sum;
        }

        if(intersects(from, to, n.from, n.to)) {
            propagate(v);
            int leftSum = RSQ(2 * v, from, to);
            int rightSum = RSQ(2 * v + 1, from, to);

            return leftSum + rightSum;
        }

        return 0;
    }

    //Range Minimum Query
    public int RMinQ(int from, int to){
        return RMinQ(1, from, to);
    }

    int RMinQ(int v, int from, int to) {
        Node n = heap[v];


        //If you did a range update before then you can infer the RSQ of any range that falls inside it
        if( n.pendingVal != null && contains(n.from, n.to, from, to) ) {
            //If this node-range was part of a range update, the minimun value will be
            //the pendingVal itself
            return n.pendingVal;
        }

        if( contains(from, to, n.from, n.to) ) {
            return heap[v].min;
        }

        if(intersects(from, to, n.from, n.to)) {
            propagate(v);
            int leftMin = RMinQ(2 * v, from, to);
            int rightMin = RMinQ(2 * v + 1, from, to);

            return Math.min(leftMin, rightMin);
        }

        return 0;
    }


    public void update(int from, int to, int value) {
        update(1, from, to, value);
    }
    void update(int v, int from, int to, int value) {

        Node n = heap[v];

        if( contains(from, to, n.from, n.to) ) {
            change(n, value);
            return;
        }

        if(n.size() == 1) return;

        if(intersects(from, to, n.from, n.to)) {
            propagate(v);
            update(2*v, from, to, value);
            update(2*v + 1, from, to, value);
            n.sum = heap[2*v].sum + heap[2*v + 1].sum;
            n.min = Math.min(heap[2*v].min, heap[2*v + 1].min);

        }
    }

    void propagate(int v){
        Node n = heap[v];

        if(n.pendingVal != null) {
            change( heap[2*v] , n.pendingVal);
            change( heap[2*v + 1], n.pendingVal);
            n.pendingVal = null; //unset the pending propagation value
        }
    }

    void change(Node n, int value){
        n.pendingVal = value;
        n.sum = n.size()*value;
        n.min = value;
        array[n.from] = value;
    }

    //Test if the range1 contains range2
    boolean contains(int from1, int to1, int from2, int to2 ) {
        return from2 >= from1 && to2 <= to1;
    }

    //Inclusive intersection, test if range1[from1, to1] intersects range2[from2, to2]
    boolean intersects(int from1, int to1, int from2, int to2 ){
        return from1 <= from2 && to1 >=from2   //  (.[..)..] or (.[...]..)
                || from1 >= from2 && from1 <= to2 ; // [.(..]..) or [..(..)..
    }

    static class Node{
        int sum;
        int min;
        Integer pendingVal = null; //Value to propage lazily
        int from;
        int to;

        int size(){
            return to - from + 1;
        }
    }


}
