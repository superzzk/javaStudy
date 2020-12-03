package com.zzk.study.io;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

public class ScannerDemo {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args){

        String str = input.next();
        System.out.println(str);

        Integer i = input.nextInt();
        System.out.println(i);

        ArrayList<Integer> l = new ArrayList<>();
        l.toArray(new Integer[0]);
    }

}
