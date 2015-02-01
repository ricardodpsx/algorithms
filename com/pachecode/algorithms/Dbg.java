package com.pachecode.algorithms;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Created by ricardo on 1/01/15.
 */
public class Dbg {

    public static void print(Collection c) {
        System.out.print(Arrays.toString(c.toArray()));
    }

    public static<T> Dbg pr(T[] t) { return pr(t, t.length); }

    public static <T> Dbg pr(T[] ch, int size){
        for (int i = 0; i < size; i++) {
            System.out.print(ch[i] +", ");
        }

        System.out.println();
        return new Dbg();
    }



    public static<T> Dbg pr(T s){
        System.out.print(s + " ");
        return new Dbg();
    }

    public static <T> Dbg pr(int[] ch, int size){
        for (int i = 0; i < size; i++) {
            System.out.print(ch[i] +", ");
        }

        System.out.println();
        return new Dbg();
    }

    public static <T> Dbg pr(int[] ch){
        pr(ch, ch.length);
        return new Dbg();
    }
    public static <T> Dbg pr(char[] ch){
        for(char c: ch)
            System.out.print(c +", ");
        System.out.println();
        return new Dbg();
    }
    public static<T> Dbg pr(Collection<T> c) {
        for(T i : c) {
            System.out.print(i + ", ");
        }
        System.out.println();
        return new Dbg();
    }

    public  static<K, T> Dbg pr(Map<K, T> map) {
        return pr("", map);
    }
    public  static<K, T> Dbg pr(String name, Map<K, T> map) {

        for(K k : map.keySet() ) {

            System.out.print(k + ": " + map.get(k) + ", ");


        }
        System.out.println();
        return new Dbg();
    }



    public  static<T> Dbg pr(T[][] mat) {

        for(T[] ar : mat ) {
            for(T it : ar)
                System.out.print(it + ", ");

            System.out.println();

        }
        return new Dbg();
    }

    public static Dbg ln() {
        System.out.println();
        return new Dbg();
    }
}
