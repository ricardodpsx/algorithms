package com.pachecode.algorithms;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by ricardodpsx@gmail.com on 2/01/15.
 */
public class ERead {
    BufferedReader bf;
    StringTokenizer tk;

    public ERead(Reader r) {
        bf = new BufferedReader(r);
    }

    public ERead() {
        this(new InputStreamReader(System.in));
    }

    public ERead(String str) throws IOException {
        this(new FileReader(str));
    }

    boolean hasNext() throws IOException {
        return (tk != null && tk.hasMoreTokens()) || initToken();
    }

    boolean initToken() throws IOException {


        if (tk == null || !tk.hasMoreTokens()) {
            String line = bf.readLine();

            if(line == null) return false;

            tk = new StringTokenizer(line);
        }
        return true;
    }

    public Integer nxInt() throws IOException {
        initToken();
        return Integer.valueOf(tk.nextToken());
    }

    public Long nxLng() throws IOException {
        initToken();
        return Long.valueOf(tk.nextToken());
    }

    public Double nxDbl() throws IOException {
        initToken();
        return Double.valueOf(tk.nextToken());
    }

    public String nx() throws IOException {
        initToken();
        return tk.nextToken();
    }

    //This will jump whatever is in the current line
    public String nxLn() throws IOException {
        tk = null;
        return bf.readLine();
    }

    //the rest of the line
    public String rest() throws IOException {
        tk = null;
        StringBuilder bl = new StringBuilder();
        while (tk.hasMoreTokens()) bl.append(tk.nextToken());
        return bl.toString();
    }
}
