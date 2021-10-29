package com.example.dacsnm;

public class Node {
    boolean type; // true:极大层  false:极小层
    boolean update;
    int ex;
    String val;
    Node parent;
    String choose;
    Node(int[] val, boolean type, Node parent) {
        this.update = false;
        this.val = "";
        for(int i = 0; i < val.length; i++)
            this.val = this.val + val[i]+ ".";
        this.type = type;
        this.parent = parent;
    }
}