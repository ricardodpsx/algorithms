package com.pachecode.algorithms;

/**
 * Created by ricardodpsx@gmail.com on 1/02/15.
 *
 * SegmentTree with Lazy Initialization
 *
 *
 */

import java.util.Stack;

/**
 * Created by ricardodpsx@gmail.com on 31/01/15.
 */
public class
        SegmentTree2 {
    Node root;
    Integer[] array;


    public static void main(String args[]){
        SegmentTree2 st = new SegmentTree2(new Integer[]{1,2,-4,8,1,3,4,2,1});
        System.out.println(st.RMQ(0, 5));
        System.out.println(st.RSQ(0, 3));
        System.out.println(st);

        st.update(0,new Integer[]{0,0});
        System.out.println(st);
    }

    public SegmentTree2(Integer[] array) {
        root = new Node(new PartitionArray<Integer>(array));
        this.array = array;
    }

    public void update(int pos, int value){
        update(pos, new Integer[]{value});
    }
    public void update(int from, Integer[] a){

        //Updating the array
        for(int i =0; i < a.length; i++){
            array[from + i ] = a[i];
        }

        //Invalidating all the affected ranges
        update(root, from, from + a.length - 1);

    }
    void update(Node node, int from, int to){

        /**
         * As this range is intersected by the update-range
         * Min and Sum are no longer valid so We need to invalidate them.
         * We want to keep the node anyway because some of its children may have indexed information
         **/
       node.invalidate();

        /**
         *  If the left or right arrays are contained inside the update-range We destroy them
         *  becouse all of its children will have invalid indexed data so they are a waste of memory
         */
        if(node.left != null && contains(from, to, node.leftFrom(), node.leftTo()  )) {
            node.left = null;
        }

        if(node.right != null && contains(from, to, node.rightFrom(), node.rightTo()  )) {
            node.right = null;
        }


        //If the left or right are intersected by the query-range We recurse into them
        if( node.left != null && intersects(from, to, node.leftFrom(), node.leftTo()) ){
            update(node.left(), from, to);
        }

        if( node.right != null &&  intersects(from, to, node.rightFrom(), node.rightTo()) ){
            update(node.right(), from, to);
        }


    }

    public int RMQ(int from, int to) {
        return RMQ(root, from, to);
    }

    private int RMQ(Node node, int from, int to){


        //If array is inside range We have our answer
        if( node.from() >= from && node.to() <= to ){
            return node.getMin();
        }

        int leftMin = -1;
        if( intersects(from, to, node.leftFrom(), node.leftTo()) ){
            leftMin = RMQ(node.left(), from, to);
        }

        int rightMin = -1;
        if(  intersects(from, to, node.rightFrom(), node.rightTo()) ){
            rightMin = RMQ(node.right(), from, to);
        }

        if(rightMin == -1)
            return leftMin;

        if(leftMin == -1)
            return rightMin;

        return  array[ leftMin ] <= array[rightMin] ? leftMin : rightMin;
    }

    public int RSQ(int from, int to){
        return RSQ(root, from ,to);
    }

    private int RSQ(Node node, int from, int to) {
        //If array is inside range We have our answer
        if( node.from() >= from && node.to() <= to ){
            return node.getSum();
        }


        Integer leftSum = 0;
        if( intersects(from, to, node.leftFrom(), node.leftTo()) ){
            leftSum = RSQ(node.left(), from, to);
        }

        int rightSum = 0;
        if(  intersects(from, to, node.rightFrom(), node.rightTo()) ){
            rightSum = RSQ(node.right(), from, to);
        }

        return  rightSum + leftSum;
    }

    //Inclusive intersection
    boolean intersects(int from1, int to1, int from2, int to2 ){
        return from1 <= from2 && to1 >=from2   //  (.[..)..] or (.[...]..)
                || from1 >= from2 && from1 <= to2 ; // [.(..]..) or [..(..)..
    }

    //Test if the range1 contains range2
    boolean contains(int from1, int to1, int from2, int to2 ) {
        return from2 >= from1 && to2 <= to1;
    }

    private class Node {

        Integer min = null;
        Integer sum = null;
        Node left, right;

        PartitionArray<Integer> pArray;
        Node(PartitionArray<Integer> pArray) {
            this.pArray = pArray;

        }

        void invalidate(){
            min = null;
            sum = null;
        }

        Node left(){
            if(left == null) left = new Node(pArray.left());
            return left;
        }
        Node right(){
            if(right == null ) right = new Node(pArray.right());
            return right;
        }

        int size(){ return pArray.size(); }
        int from(){ return pArray.getFrom();  }
        int leftFrom(){ return pArray.leftFrom();}
        int rightFrom() {   return pArray.rightFrom();   }
        int to(){ return pArray.getTo();  }
        int leftTo(){ return pArray.leftTo();   }
        int rightTo(){ return pArray.rightTo();  }

        //Recurse over its childs to get the min
        void update(){

            int posLeft = left().getMin();
            int posRight = right().getMin();

            min = (array[posLeft] <= array[posRight]) ? posLeft: posRight;

            sum = left().getSum() + right().getSum();

        }

        int getSum(){
            if(pArray.size() == 1) {
                return sum = pArray.get(0);
            }
            if(sum == null) update();

            return sum;
        }

        int getMin(){

            if(pArray.size() == 1) {
                return min = pArray.getFrom();
            }
            //If min is not indexed it has to get it
            if(min == null) {
                update();
            }

            return min;
        }


        @Override
        public String toString() {
            return String.format("(%d, %d) => %s | Min: %s | Sum: %s",
                    pArray.getFrom(), pArray.getTo(),
                    pArray.toString(),
                    min != null? array[min]: "null",
                    sum != null? sum: "null"
            );
        }
    }

    @Override
    public String toString() {
        Node n = root;

        Stack<Node> q = new Stack<>();

        q.push(n);

        StringBuilder out = new StringBuilder();
        //Deep First Traversing tree
        while(!q.isEmpty()) {
            Node r = q.pop();

            if(r.right != null) q.add(r.right);
            if(r.left != null) q.add(r.left);


            out.append(r.toString() + "\n");

        }

        return  out.toString();
    }
}
