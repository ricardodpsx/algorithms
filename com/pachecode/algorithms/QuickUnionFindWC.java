package com.pachecode.algorithms;


import java.io.PrintWriter;

/****************************************************************************
 *  Compilation:  javac WCQUF.java
 *  Execution:  java WeightedQuickUnionUF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  quick-union-find Weighted and WITH path compression
 *
 ****************************************************************************/

public class QuickUnionFindWC {
    private int[] id;    // id[i] = parent of i
    private int[] sz;    // sz[i] = number of objects in subtree rooted at i
    private int count;   // number of components

    // Create an empty union find data structure with N isolated sets.
    public QuickUnionFindWC(int N) {
        count = N;
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    // Return the number of disjoint sets.
    public int count() {
        return count;
    }

    // Return component identifier for component containing p
    public int find(int p) {
        while (p != id[p]) {
            id[p] = id[id[p]]; //adding half compression
            p = id[p];
        }
        return p;
    }

   // Are objects p and q in the same set?
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    //Size of the set that contains p
    //TODO: Test
    public int sizeOfSet(int p){
        return sz[find(p)];
    }

  
   // Replace sets containing p and q with their union.
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;

        // make smaller root point to larger one
        if   (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
        else                 { id[j] = i; sz[i] += sz[j]; }
        count--;
    }

    //Usage
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String args[]) {
        QuickUnionFindWC sets = new QuickUnionFindWC(5);

        sets.union(1,2);
        sets.union(1,3);

        out.println(sets.sizeOfSet(1));
        out.println(sets.sizeOfSet(3));

        out.close();
    }

}

