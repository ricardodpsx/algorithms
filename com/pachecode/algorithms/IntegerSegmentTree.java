package com.pachecode.algorithms;

/**
 * Created by ricardodpsx@gmail.com on 8/01/15.
 */
public class IntegerSegmentTree extends SegmentTree{

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