package com.pachecode.algorithms.old;

import com.pachecode.algorithms.PartitionArray;

import java.util.Comparator;
import java.util.Stack;

/**
 * Created by ricardodpsx@gmail.com on 3/01/15.
 *
 * Data Structure to perform fast dynamic range queries
 *
 */
public class SegmentTree {

    Node root;



    //Default searchComparator
    Comparator searchComparator = null;
    Aggregator aggregator = null;
    PartitionArray array;


    public SegmentTree(PartitionArray array, Comparator searchComparator,  Aggregator aggregator){
        this.searchComparator = searchComparator;
        this.aggregator = aggregator;
        this.array = array;
        root = new Node(array); //Build the segment tree
    }

    protected SegmentTree() {

    }

    public SegmentTree(Comparable[] array, Comparator searchComparator,  Aggregator aggregator){
        this(new PartitionArray(array), searchComparator, aggregator);
    }

    public SegmentTree(Comparable[] array){
        this(new PartitionArray(array), naturalOrder, sum());
    }
    public SegmentTree(PartitionArray array){
        this( array , naturalOrder, sum());
    }



    public int search(int from, int to) {
        assert from <= to;
        return search(root, from, to);
    }

    protected int search(Node n, int from, int to) {

        /*
            if the node interval is contained on our interval, it is a candidate so
            return its index
         */
        if(from <= n.from() && to >= n.to()) {
            return n.getSelection();
        }

        //Do not search getRight/getLeft if its only one node and is not inside the interval
        if(n.size() == 1)
            return -1;

        int indLeft = -1, indRight = -1;
        /**
         * If the getLeft interval intercepts and starts before from,
         * searchComparator to the getLeft
         */
        if( from <= n.leftTo() )
            indLeft = search(n.getLeft(), from, to);

        /**
         * if the getRight interval intersects and finishes after to
         * searchComparator to the getRight
         */
        if( to >= n.rightFrom() )
            indRight = search(n.getRight(), from, to);


        if(indLeft == - 1) return indRight;
        else if(indRight == - 1) return  indLeft;


        return select(indLeft, indRight);
    }

    public Object aggregation(int from, int to) {
        assert from <= to;
        return aggregation(root, from, to);
    }

    protected Object aggregation(Node n, int from, int to) {

        /*
            if the node interval is  inside [from, to] return its index
         */
        if(from <= n.from() && to >= n.to()) {
            return n.getAggregation();
        }
        //else:
        //Do not search getRight/getLeft if its only one node and is not inside the interval
        if(n.size() == 1)
            return null;


        Object leftAgg = null, rightAgg = null;

        // If the search-interval intersects getLeft-subArray get the aggregation of it
        if( from <= n.leftTo() )
            leftAgg = aggregation(n.getLeft(), from, to);

        // If the search-interval intersects getRight-subArray get the aggregation of it
        if( to >= n.rightFrom() )
            rightAgg = aggregation(n.getRight(), from, to);


        if(leftAgg == null) return rightAgg;
        else if(rightAgg == null) return  leftAgg;
        //else
        return aggregator.run( rightAgg, leftAgg );
    }



    //Updating with Different values
    public void update(int from, Object[] values) {
        for(int i = 0; i < values.length; i++) {
            array.set( from + i, values[i]);
        }
        update(root, from , values);
    }

    //Update index i with value val and perform indexing of that line and indexes
    public void update(int i, Object val) {
        update(i, new Object[]{val});
    }


    public void update(Node n, int from, Object[] values) {
        //n.ind =  searchComparator.compare(n.subArray.getReal(n.ind), val) <= 0 ? root.subArray.getReal(n.ind): val ;

        int to = from + values.length - 1;

        /** If the node range is inside the Updating range
         * We do not want to recurse many more and We don't want to use such useless memory
         */
        if(n.from() >= from && n.to() <= to ) {
            n.invalidate();

            //This lines automatically frees the reference, not sure how good idea it is but keep the structure at is minimun space
            //Can be removed without problems
            n.unsetRight();
            n.unsetLeft();

            return;
        }

        //1) We need to change the initial value first from bot to top, see 2
        //Here we are in bot
        if(n.size() == 1) {
            n.update();
            return;
        }



        //If [from, to] affects  the getRight or getLeft interval, update them
        if( n.rightExists() && ( from <= n.rightFrom() && to >= n.rightFrom()   //  (.[..)..] or (.[...]..)
                || from >= n.rightFrom() && from <= n.rightTo() ) ) // [.(..]..) or [..(..)..]
        {
            update(n.getRight(), from, values);
        }

        if( n.leftExists() && ( from <= n.leftFrom() && to >= n.leftFrom()   //  (.[..)..] or (.[...]..)
                || from >= n.leftFrom() && from <= n.leftTo() ) ) // [.(..]..) or [..(..)..]
        {
            update(n.getLeft(), from, values);
        }
        //2) Invalidating aggregation and selection to force further lazy update
        if(n.size() > 1) {
            n.invalidate();
        }

    }


    int select(int leftIndex, int rightIndex){
        Object leftVal = array.get(leftIndex);
        Object rightVal = array.get(rightIndex);

        return searchComparator.compare(leftVal, rightVal) <= 0 ?  leftIndex : rightIndex;
    }

    class IntegerSegmentTree extends SegmentTree{

        public IntegerSegmentTree(PartitionArray<Integer> array) {
            this.searchComparator = naturalOrder;
            this.aggregator = sum();
            this.array = array;
            root = new IntegerNode(array); //Build the segment tree
        }

        public IntegerSegmentTree(Integer[] array) {
            this(new PartitionArray<Integer>(array));
        }

        @Override
        protected Object aggregation(Node n, int from, int to) {
            IntegerNode ni = (IntegerNode) n;

            //If search is inside one node with pending value you can infer
            if(ni.hasPending() && from >= n.from() && to <= n.to()  ) {
                return  ni.pending* (to - from + 1);
            }

            return super.aggregation(n, from, to);
        }

        @Override
        protected int search(Node n, int from, int to) {
            IntegerNode ni = (IntegerNode) n;

            //If search is inside one node with pending value you can infer
            if(ni.hasPending() && from >= n.from() && to <= n.to()  ) {
                return n.getSelection();
            }

            return super.search(n, from, to);
        }


        /**
         * Update Function with Lazy Top-Bottom propagation
         * @param from
         * @param to
         * @param value
         */
        public void update(int from,int to, int value) {
            update((IntegerNode) root, from, to, value);
        }

        public void update(Node n, int from, Object[] values) {

        /*The Main update has priority over the
        Top-Bottomm propagation that may happen above so We update it*/

            if(n.size() == 1) {

                array.set( n.from() , values[ n.from()  - from ] );
                n.update();
                return;
            }

            //n.ind =  searchComparator.compare(n.subArray.getReal(n.ind), val) <= 0 ? root.subArray.getReal(n.ind): val ;
            if(n.size() > 1) { //Propagates before it loses the information when invalidated
                IntegerNode ni = (IntegerNode) n;
                ni.propagate();
            }

            super.update(n, from, values);
        }

        //We can take advantage of Lazy Top-Bottom Propagation when Working with Numbers
        private void update(IntegerNode n, int from, int to, int value) {
            //n.ind =  searchComparator.compare(n.subArray.getReal(n.ind), val) <= 0 ? root.subArray.getReal(n.ind): val ;

            //If the node range is inside the Updating range
            if(n.from() >= from && n.to() <= to ) {
                n.setPending(value);

                return;
            }

            if(n.size() > 1) { //Propagates before it loses the information when invalidated
                n.propagate();
            }

            //You dont need to update if you are not changing the value
            if(n.hasPending() && n.pending == value) {

                return;
            }

            //If [from, to] affects  the getRight or getLeft interval, update them
            if(  from <= n.rightFrom() && to >= n.rightFrom()   //  (.[..)..] or (.[...]..)
                    || from >= n.rightFrom() && from <= n.rightTo()  ) // [.(..]..) or [..(..)..]
            {
                update( (IntegerNode) n.getRight(), from, to, value);
            }

            if( from <= n.leftFrom() && to >= n.leftFrom()   //  (.[..)..] or (.[...]..)
                    || from >= n.leftFrom() && from <= n.leftTo()  ) // [.(..]..) or [..(..)..]
            {
                update((IntegerNode) n.getLeft(), from, to, value);
            }

            //2) Invalidating aggregation and selection to force further lazy update
            if(n.size() > 1) {
                n.invalidate();
            }

        }

        class IntegerNode extends Node{

            //Last Value pending for propagation
            Integer pending = null;

            private boolean propagatedRight = true;
            private boolean propagatedLeft= true;


            public boolean hasPending(){
                return !propagatedRight && !propagatedLeft  ;
            }

            void setPending(Integer value) {

                //all the range is changing so all the values are the same
                setAggregation(size() * value);
                setSelection(from());

                subArray.set(0, value);

                //Now We know the changes needs to be propagated when required
                pending = value;
                propagatedRight = false;
                propagatedLeft = false;

                unsetLeft();
                unsetRight();
            }

            void propagate(){
                propagateLeft();
                propagateRight();
            }


            //Do propagations only when and array inside you changes
            void propagateLeft(){
                if(size() == 1) return;

                if(!propagatedLeft)
                    ((IntegerNode) super.getLeft()).setPending(pending);

                propagatedLeft = true;
            }


            void propagateRight(){

                if(size() == 1) return;

                if(!propagatedRight)
                    ((IntegerNode) super.getRight()).setPending(pending);

                propagatedRight = true;
            }

            @Override
            void invalidate() {

                //If the range is invalidated it also means that it's not capable of infer anything
                //So We mark it as if there is nothing to propagate
                propagatedRight = true;
                propagatedLeft = true;


                super.invalidate();
            }


            @Override
            Node getRight() {
                IntegerNode n = (IntegerNode) super.getRight();

                //If has pending changes propagate them getRight
                propagateRight();

                return n;
            }



            @Override
            Node getLeft() {
                IntegerNode n = (IntegerNode) super.getLeft();

                propagateLeft();

                return n;
            }

            @Override
            Node create(PartitionArray part){
                return new IntegerNode(part);
            }



            IntegerNode(PartitionArray subArray) {
                super(subArray);
            }

            @Override
            public String toString(){
                return super.toString() + "| pending: " + hasPending();
            }

        }
    }


    class Node{

        PartitionArray subArray;
        Node left = null, right = null;




        private Object aggregation = null; //Aggregation, The result of the aggregation

        //Index, Here We store the subArray indexed element
        private int selection = -1;



        Node(PartitionArray subArray){
            this.subArray = subArray;
        }


        boolean leftExists() {
            return left != null;
        }
        Node getLeft() {

            if(left == null && subArray.size() > 1) left = create(subArray.left());
            return left;
        }
        int leftFrom(){
            return subArray.leftFrom();
        }

        int leftTo(){

            return leftFrom() + subArray.leftSize() -1;
        }

        void unsetLeft() {
            this.left = null;
        }


        public boolean rightExists() {
            return right != null;
        }
        Node getRight() {
            if(right == null && subArray.size() > 1) right = create(subArray.right());
            return right;
        }

        void unsetRight() {
            this.right = null;
        }

        Node create(PartitionArray part){
            return new Node(part);
        }


        int rightFrom(){
            return subArray.rightFrom();
        }
        int rightTo(){
            return rightFrom() + subArray.rightSize() -1;
        }

        void invalidate() {
            selection = -1;
            aggregation = null;
        }


        void setSelection(int sel){
            this.selection = sel;
        }

        int getSelection() {
            if( selection == -1 )
                this.update();

            return selection;
        }

        void setAggregation(Object ag){
            this.aggregation = ag;
        }

        Object getAggregation(){
            if( aggregation == null )
                this.update();

            return aggregation;
        }

        void update() {
            if(subArray.size() > 1) {

                selection =  select(getLeft().getSelection(), getRight().getSelection());

                aggregation = aggregator.run(getLeft().getAggregation(), getRight().getAggregation());

            } else{
                //For indexing operations We start with the global index of the unit
                selection = subArray.getFrom();

                // For aggregation operations we start with the value of the unit
                aggregation = aggregator.init(subArray.get(0));
            }

        }


        int size(){
           return subArray.size();
        }


        /**
         * @return The value of the index in this interval.
         */
        Object val() {
            return selection!= -1 ? subArray.getReal(selection) : null;
        }

        int from(){
            return subArray.getFrom();
        }

        int to(){
            return subArray.getTo();
        }


        @Override
        public String toString() {
            return String.format("i: [%d, %d] => %s | index = %s, val = %s | aggregation: %s ",
                    subArray.getFrom(), subArray.getTo(),
                    subArray.toString(),
                    selection,
                    val(),
                    aggregation
            );
        }


    }

    public static final Comparator<Comparable> naturalOrder = new Comparator<Comparable>() {
        @Override
        public int compare(Comparable o1, Comparable o2) {
            return o1.compareTo(o2);
        }
    };





    public static abstract  class Aggregator{

        public Object init(Object a ){
            return a;
        }

        public  abstract Object run(Object a, Object b);
    }



    /**
     * For RSQ (Range Sum Query) Fenwick Tree is better
     * @return
     */
    public static Aggregator sum() {
        return new Aggregator() {
            @Override
            public Integer run(Object a, Object b) {
                int x = (int) a;
                int y = (int) b;
                return x + y;
            }
        };
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
