package com.pachecode.algorithms;

import java.util.*;

/**
 * Created by ricardodpsx@gmail.com on 31/01/15.
 */
public class SegmentTree2 {
    Node root;
    Integer[] array;


    public static void main(String args[]){
       /* SegmentTree2 st = new SegmentTree2(new Integer[]{1,2,4,8,-1,3});
        System.out.println(st.RMQ(0, 3));
        System.out.println(st.RSQ(0, 3));
        System.out.println(st);

        st.updateRange(0,3, 0);

        System.out.println(st);



        System.out.println(st.RMQ(0,3));
        System.out.println(st.RMQ(1,2));
        System.out.println(st.RSQ(0, 4));

        System.out.println(st);*/

        SegmentTree2 st2 = new SegmentTree2(new Integer[]{0,0,0,0,0,0,0});

        st2.updateRange(2, 5, 1);
        //st2.root.forceExpand();
        System.out.println(st2);

        st2.updateRange(0, 3, new Toggle());

        st2.root.forceExpand();
        System.out.println(st2);



    }

    public SegmentTree2(Integer[] array) {
        root = new Node(new PartitionArray<Integer>(array));
        this.array = array;
    }



    public int RMQ(int from, int to) {
        return RMQ(root, from, to)[0];
    }

    private int[] RMQ(Node node, int from, int to){

        //If the range falls inside a the node maybe a lazy operation can infer the sum or min
        if( !node.pendingOps.isEmpty()
                && node.pendingOps.peek().canInfer()
                && from >= node.from() && to <= node.to() ){
            return node.pendingOps.peek().inferMin(from, to, node);
        }

        //If array is inside range We have our answer
        if( node.from() >= from && node.to() <= to ){
            return node.getMin();
        }

        //If range intersects with either the left or right part of the array get the MINPQ where applies
        /* Explanation of the if:
            if two ranges  [a,b] and [c,d] intercepts
                distance(a,d) < distance(a,b) + distance(c,d)
         */

        int[] leftMin = null;
        if( intersects(from, to, node.leftFrom(), node.leftTo()) ){
            leftMin = RMQ(node.left(), from, to);
        }

        int[] rightMin = null;
        if(  intersects(from, to, node.rightFrom(), node.rightTo()) ){
            rightMin = RMQ(node.right(), from, to);
        }

        if(rightMin == null)
            return leftMin;

        if(leftMin == null)
            return rightMin;

        return  leftMin[1] <= rightMin[1] ? leftMin : rightMin;
    }

    public int RSQ(int from, int to){
        return RSQ(root, from ,to);
    }

    private int RSQ(Node node, int from, int to) {

        //If the range falls inside a bigger node maybe a lazy operation can infer the sum or min
        if( !node.pendingOps.isEmpty()
                && node.pendingOps.peek().canInfer()
                && from >= node.from() && to <= node.to() ){
            return node.pendingOps.peek().inferSum(from, to, node);
        }

        //If array is inside range We have our answer
        if( node.from() >= from && node.to() <= to ){
            return node.getSum();
        }


        Integer leftSum = 0;
        if( intersects(from, to, node.leftFrom(), node.leftTo()) ){
            leftSum = RSQ(node.left(), from, to);
        }

        int rightSum = 0;
        if(  intersects(from, to, node.rightFrom(), node.rightTo()) ) {
            rightSum = RSQ(node.right(), from, to);
        }

        return  rightSum + leftSum;
    }

    void updateRange(int from, int to, int value){
        updateRange(root, from, to, new SetOperation(value));
    }

    void updateRange(int from, int to, Operation op) {
        updateRange(root, from, to, op);
    }

    void updateRange(Node node, int from, int to, Operation op){


        //If array is inside range We set to it the inferred values
        if( node.from() >= from && node.to() <= to ){
            //We remove its children because they are no longer valid
            node.left = null;
            node.right = null;

            //We execute the Operation so it does what it needs to
            //new SetOperation(value).doIt(node);
            op.doIt(node);

            return;
        }

        //If range intersects with either the left or right part of the array We go down into the recursion to update the right parts
       // System.out.printf("query: (%s, %s) - node: (%s, %s)%n", from, to, node.leftFrom(), node.leftTo());
        if( intersects(from, to, node.leftFrom(), node.leftTo()) ){
            updateRange(node.left(), from, to, op);
        }

        if(  intersects(from, to, node.rightFrom(), node.rightTo()) ){
            updateRange(node.right(), from, to, op);
        }
    }

    //Inclusive intersection
    boolean intersects(int from1, int to1, int from2, int to2 ){
        return from1 <= from2 && to1 >=from2   //  (.[..)..] or (.[...]..)
                || from1 >= from2 && from1 <= to2 ; // [.(..]..) or [..(..)..
    }

    static class SetOperation extends Operation{
        int value;
        public SetOperation(int value){
            this.value = value;
        }
        @Override
        public void doIt(Node n) {
            n.min = new int[] {n.from(),value};
            n.sum = n.size() * value;

            //This operation replaces all
            n.pendingOps.clear();

            if(n.size() != 1) {
                n.pendingOps.add(this);
            }else{
                n.pArray.set(0, value);
            }
        }

        @Override
        public int[] inferMin(int from, int to, Node n){
            //Updates the node at that position
            //n.pArray.setReal(from, value);
            return new int[]{from, value};
        }

        @Override
        public boolean canInfer() {
            return true;
        }

        @Override
        public int inferSum(int from, int to, Node n) {
            return (to - from + 1)*value;
        }


        @Override
        public String toString(){
            return "Set: " + value;
        }
    }

    //To toggle between 0 and 1
    static class Toggle extends Operation{

        @Override
        public void doIt(Node n) {
            //Invert the sum
            if(n.sum != null)
                n.sum = n.size() - n.sum;



            if(n.size() > 1) {
                n.pendingOps.add(this);
            }else{
                n.pArray.set(0, n.pArray.get(0) == 0 ? 1 : 0);
            }
        }



        @Override
        public String toString(){
            return "Toggle";
        }
    }

    static abstract  class  Operation {
        abstract void doIt(Node n);

        public boolean canInfer() {
            return false;
        }

        public int inferSum(int from, int to, Node n) {
            return 0;
        }


        public int[] inferMin(int from, int to, Node n) {
            return null;
        }

    }

    private class Node {

        //Pending for lazy propagation
        Queue<Operation> pendingOps = new LinkedList<>();

        int[] min = new int[2];
        Integer sum = null;
        Node left, right;

        PartitionArray<Integer> pArray;



        Node(PartitionArray<Integer> pArray) {
            this.pArray = pArray;

            if(pArray.size() == 1) {
                //Aplying pending OPS

                //min[0] = pArray.getFrom();
                //sum = pArray.get(0);
            }


        }

        void forceExpand(){
            if(size() == 1) return;
            propagate();
            left().forceExpand();
            right().forceExpand();
        }

        //Propagate the operations to its children before someone access them
        void propagate(){

            if(size() == 1) return;

            //Create the childs pass the operations to them and clear.
            if( left == null || right == null) {
                left = new Node(pArray.left());
                right = new Node(pArray.right());
            }

            //Excecuting the pendign operations on its childs
            while(!pendingOps.isEmpty()) {
                Operation op = pendingOps.poll();
                op.doIt(left);
                op.doIt(right);
            }
        }

        //The simple access to left or right causes propagation of operations
        Node left(){
            propagate();
            return left;
        }
        Node right(){
            propagate();
            return right;
        }

        int size(){
            return pArray.size();
        }

        int from(){
            return pArray.getFrom();
        }
        int leftFrom(){
            return pArray.leftFrom();
        }
        int rightFrom() {
            return pArray.rightFrom();
        }


        int to(){
            return pArray.getTo();
        }
        int leftTo(){
            return pArray.leftTo();
        }
        int rightTo(){
            return pArray.rightTo();
        }

        //Recurse over its childs to get the min
        void update(){

            int[] posLeft = left().getMin();
            int[] posRight = right().getMin();

            min = ( posLeft[1] <= posRight[1]) ? posLeft: posRight;

            sum = left().getSum() + right().getSum();

        }

        int getSum(){
            if(sum == null) update();

            return sum;
        }

        int[] getMin(){

            //If min is not indexed it has to get it
            if(min == null) {
                update();
            }

            return min;
        }


        @Override
        public String toString() {
            return String.format("(%d, %d) => %s | Min[%s]: %s | Sum: %s | PendingOps: %s",
                    pArray.getFrom(), pArray.getTo(),
                    pArray.toString(),
                    min!=null?min[0]:"?",
                    min != null? min[1]: "null",
                    sum != null? sum: "null",
                    Arrays.toString( pendingOps.toArray() )
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
