package com.company;

import java.util.Stack;

public class StackBinary {

    public static void main(String[] args) {
        int number = 6;
        String str = "kachow";

       //toBinary(number);

        //toStack(str);





    }
    //Part one
    public static void toBinary(int decimal){
        Stack<Integer> stk= new Stack<Integer>();
        int[] binary = new int[40];
        int index = 0;
        while(decimal > 0){
            binary[index++] = decimal%2;
            decimal = decimal/2;
        }
        for(int i = index-1;i >= 0;i--){
            stk.push(binary[i]);
        }
        int j = stk.size();
        for(int i = 0;i < j;i++){
            System.out.print(stk.pop());
        }
    }

    //part two
    public static void toStack(String str) {
        Stack<Character> stk= new Stack<Character>();
        for(int i=0; i<str.length();i++) {
            stk.push(str.charAt(i));
        }
        System.out.println(stk);
        for(int i=stk.size(); i>0;i--) {
            char k = stk.pop();
            System.out.print(k);
        }

    }

    }


