package com.pachecode.algorithms.test;

import com.pachecode.algorithms.ERead;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ricardodpsx@gmail.com on 2/01/15.
 */
public class EReadTest {


    static PrintWriter out = new PrintWriter(System.out, true);
    public static void main(String args[]) throws IOException {
        ERead sc = new ERead("src/tests/Eread.in");

        out.println(sc.nxInt());
        out.println(sc.nxInt());
        out.println(sc.nxLn());
        out.println(sc.nxLn() + ".");
        out.println(sc.nxInt());
        out.println(sc.nx());
        out.println(sc.nx());
    }

}
