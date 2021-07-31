package com.ruoqishan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class TSTNew<Value> {
	private int N;       // size
    private Node root;   // root of TST

    private class Node {
        private char c;                 // character
        private Node left, mid, right;  // left, middle, and right subtries
        private int[][] val;              // value associated with string
    }

    // return number of key-value pairs
    public int size() {
        return N;
    }

   /**************************************************************
    * Is string key in the symbol table?
    **************************************************************/
    public boolean contains(String key) {
        return get(key) != null;
    }

    public int[][] get(String key) {
        if (key == null) throw new NullPointerException();
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node x = get(root, key, 0);
        if (x == null) return null;       
       return x.val;
    }

    // return subtrie corresponding to given key
    private Node get(Node x, String key, int d) {
        if (key == null) throw new NullPointerException();
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        if (x == null) return null;
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.left,  key, d);
        else if (c > x.c)              return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
        else                           return x;
    }


   /**************************************************************
    * Insert string s into the symbol table.
    **************************************************************/
    public void put(String s, int[][] arr) {
        if (!contains(s)) {
        	N++;       	
            root = put(root, s, arr, 0);
        }
        else {     	
             int[][] arrbefore = get(root, s, 0).val;
             List<int[]> list = new ArrayList<int[]>(Arrays.<int[]>asList(arrbefore));
             list.addAll(Arrays.<int[]>asList(arr));
             arr = list.toArray(arrbefore);
             root = put(root, s, arr, 0);         
        }
    }
    

    private Node put(Node x, String s, int[][] arr1, int d) {
        char c = s.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if      (c < x.c)             x.left  = put(x.left,  s, arr1, d);
        else if (c > x.c)             x.right = put(x.right, s, arr1, d);
        else if (d < s.length() - 1)  x.mid   = put(x.mid,   s, arr1, d+1);
        else                          x.val   = arr1;
        return x;
    }


   /**************************************************************
    * Find and return longest prefix of s in TST
    **************************************************************/
    public String longestPrefixOf(String s) {
        if (s == null || s.length() == 0) return null;
        int length = 0;
        Node x = root;
        int i = 0;
        while (x != null && i < s.length()) {
            char c = s.charAt(i);
            if      (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else {
                i++;
                if (x.val != null) length = i;
                x = x.mid;
            }
        }
        return s.substring(0, length);
    }

    // all keys in symbol table
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, "", queue);
        return queue;
    }

    // all keys starting with given prefix
    public Iterable<String> prefixMatch(String prefix) {
        Queue<String> queue = new Queue<String>();
        Node x = get(root, prefix, 0);
        if (x == null) return queue;
        if (x.val != null) queue.enqueue(prefix);
        collect(x.mid, prefix, queue);
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(Node x, String prefix, Queue<String> queue) {
        if (x == null) return;
        collect(x.left,  prefix,       queue);
        if (x.val != null) queue.enqueue(prefix + x.c);
        collect(x.mid,   prefix + x.c, queue);
        collect(x.right, prefix,       queue);
    }


    // return all keys matching given wildcard pattern
    public Iterable<String> wildcardMatch(String pat) {
        Queue<String> queue = new Queue<String>();
        collect(root, "", 0, pat, queue);
        return queue;
    }
 
    private void collect(Node x, String prefix, int i, String pat, Queue<String> q) {
        if (x == null) return;
        char c = pat.charAt(i);
        if (c == '.' || c < x.c) collect(x.left, prefix, i, pat, q);
        if (c == '.' || c == x.c) {
            if (i == pat.length() - 1 && x.val != null) q.enqueue(prefix + x.c);
            if (i < pat.length() - 1) collect(x.mid, prefix + x.c, i+1, pat, q);
        }
        if (c == '.' || c > x.c) collect(x.right, prefix, i, pat, q);
    }
}
