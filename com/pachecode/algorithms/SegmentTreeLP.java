package com.pachecode.algorithms;

import java.util.*;

/**
 * Created by ricardodpsx@gmail.com on 31/01/15.
 *
 * How it works:
 * 1) You can perform range Updates with an Operation
 * 2) The operations update the tree from top to bottom when needed
 * 2) If you are visiting a Node and you try to visit one of it's children (right or left)
 *      the Node will "propate" the operation
 *
 *
 *
 * Segmentree with Lazy propagation
 */
public class SegmentTreeLP {
    Node root;
    Integer[] array;

    public SegmentTreeLP(Integer[] array) {
        root = new Node(new PartitionArray<Integer>(array));
        this.array = array;
    }



    public int RMinQ(int from, int to) {
        return RMQ(root, from, to)[1];
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

        int[] leftMin = null;
        if( intersects(from, to, node.leftFrom(), node.leftTo()) ){
            leftMin = RMQ(node.left(), from, to);
        }

        int[] rightMin = null;
        if(  intersects(from, to, node.rightFrom(), node.rightTo()) ){
            rightMin = RMQ(node.right(), from, to);
        }

        int[] min;

        if(rightMin == null) min = leftMin;
        else if(leftMin == null) min = rightMin;
        else min =  leftMin[1] <= rightMin[1] ? leftMin : rightMin;


        return min;

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

    public void update(int from, int value ) {
        updateRange(from, from, value);
    }

    public void updateRange(int from, int to, int value){
        updateRange(root, from, to, new SetOperation(value));
    }

    public void updateRange(int from, int to, Operation op) {
        updateRange(root, from, to, op);
    }

    void updateRange(Node node, int from, int to, Operation op){


        //If node is inside update-range We set to it the inferred values
        if( node.from() >= from && node.to() <= to ){

            //We execute the Operation so it does what it needs to
            //new SetOperation(value).doIt(node);
            op.doIt(node);

            return;
        }else {
            //If the node intersects the update-range its values are no longer valid so it needs to propagate its pending operations
            //and lost is inferred values
            node.min = null;
            node.sum = null;
        }

        //If update-range intersects with either the left or right part of the Node We go down into the recursion to update the right parts
        if( intersects(from, to, node.leftFrom(), node.leftTo()) ){
            updateRange(node.left(), from, to, op);
        }

        if(  intersects(from, to, node.rightFrom(), node.rightTo()) ){
            updateRange(node.right(), from, to, op);
        }

        if(node.left != null && node.left().sum != null
                && node.right!= null && node.right.sum != null)
            node.sum = node.left.sum + node.right.sum;
    }

    //Inclusive intersection
    boolean intersects(int from1, int to1, int from2, int to2 ){
        return from1 <= from2 && to1 >=from2   //  (.[..)..] or (.[...]..)
                || from1 >= from2 && from1 <= to2 ; // [.(..]..) or [..(..)..
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

        int[] min = null;
        Integer sum = null;
        Node left, right;

        PartitionArray<Integer> pArray;



        Node(PartitionArray<Integer> pArray) {
            this.pArray = pArray;

            if(pArray.size() == 1) {
                //Aplying pending OPS
                min = new int[2];
                min[0] = pArray.getFrom();
                min[1] = pArray.get(0);
                sum = pArray.get(0);
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

    public int[] toArray(){
        int[] ar = new int[array.length];
        for(int i =0; i < array.length; i++){
            ar[i] = RMinQ(i,i);
        }
        return ar;
    }
    @Override
    public String toString() {
        Node n = root;

        Stack<Node> q = new Stack<>();

        q.push(n);

        StringBuilder out = new StringBuilder();

        //out.append("Inf Array: " + Arrays.toString( toArray() ) + "\n");
        //Deep First Traversing tree
        while(!q.isEmpty()) {
            Node r = q.pop();

            if(r.right != null) q.add(r.right);
            if(r.left != null) q.add(r.left);


            out.append(r.toString() + "\n");

        }

        return  out.toString();
    }


    /**** Set Range Operations *********/

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


    /*************** Toggle from 0 to 1 and viceversa operation ****************/

    //To toggle between 0 and 1
    public static class Toggle extends Operation{

        @Override
        public void doIt(Node n) {


            if(n.pendingOps.peek() instanceof SetOperation) {

                SetOperation op = ((SetOperation) (n.pendingOps.peek()));
                //Just update the operation value and do nothing else

                new SetOperation(op.value == 0 ? 1 : 0).doIt(n);

                return;
            }

            //Invert the sum
            if(n.sum != null)
                n.sum = n.size() - n.sum;

            if(n.sum != null && n.min != null) {
                //If there are no ones, all of them will be 1
                if(n.sum == 0) n.min = new int[]{ 0 , 1};
                //else there is at least a 1 it will become 0
                //Todo: Warning: This will fail! if you are using positions!!
                else n.min = new int[]{ 0 , 0 };
            }

            if(n.size() > 1) {
                if( n.pendingOps.peek() instanceof Toggle)
                    n.pendingOps.poll();
                else
                    n.pendingOps.add(this);
            }else{
                n.pArray.set(0, n.pArray.get(0) == 0 ? 1 : 0);
                n.min = new int[]{n.from(), n.pArray.get(0)};

            }
        }



        @Override
        public String toString(){
            return "Toggle";
        }
    }


    /********************* Set Pattern Operation **************************/
    static public class SetPattern extends Operation{
        Integer[] pattern;
        int sum = 0;
        int min, minPos;
        public SetPattern(Integer[] pattern) {
            this.pattern = pattern;

            min = pattern[0];
            int i = 0;
            for(int it : pattern) {
                sum += it;
                if( it < min) {
                    min = it;
                    minPos = i;
                }
                i++;
            }
        }
        @Override
        void doIt(Node n) {

            if(n.size() == 1){
                n.pArray.set( 0,  pattern[ n.from()%pattern.length   ]    );
            }else{
                n.pendingOps.add(this);
                n.sum = sum;

            }
        }

        @Override
        public boolean canInfer() {
            return true;
        }

        @Override
        public int inferSum(int from, int to, Node n) {
            return 3;
        }

    }

}
