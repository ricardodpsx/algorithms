package com.pachecode.algorithms;

import java.util.HashMap;

/**
 * Created by ricardodpsx@gmail.com on 5/01/15.
 *
 */
public class SparseMatrix {
    //nxm rowsXcols
    private HashMap<Integer, Long > rows[];
    private int rowsNum;
    private int colsNum;
    public SparseMatrix(int rowsNum, int colsNum){
        this.rowsNum = rowsNum;
        this.colsNum = colsNum;
        rows = new HashMap[rowsNum];
    }

    public void set(int i, int j, Long val) {
        if(val != 0) {
            if(rows[i] == null)
                rows[i] = new HashMap<>();

            rows[i].put(j, val);
        }
    }

    public Long get(int i, int j) {

        if(rows[i] == null) return 0l;

        Long res = rows[i].get(j);
        if(res == null) return 0l;

        return res;
    }

    @Override
    public String toString(){
        String out = "";
        for (int i = 0; i < rowsNum; i++) {
            for (int j = 0; j < colsNum; j++) {
                out += get(i, j) + ", ";
            }
            out += "\n";
        }
        return out;
    }


}