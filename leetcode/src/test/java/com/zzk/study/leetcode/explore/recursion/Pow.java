package com.zzk.study.leetcode.explore.recursion;

import org.junit.Test;

/**
 * 50
 * Implement pow(x, n), which calculates x raised to the power n (xn).
 *
 * Example 1:
 *
 * Input: 2.00000, 10
 * Output: 1024.00000
 *
 * Example 2:
 *
 * Input: 2.10000, 3
 * Output: 9.26100
 *
 * Example 3:
 *
 * Input: 2.00000, -2
 * Output: 0.25000
 * Explanation: 2-2 = 1/22 = 1/4 = 0.25
 *
 * Note:
 *
 *     -100.0 < x < 100.0
 *     n is a 32-bit signed integer, within the range [−231, 231 − 1]
 **/
public class Pow {
    @Test
    public void test(){
        System.out.println(myPow2(2,10));
    }


    public double myPow(double x, int n) {
        if (x==0)
            return 0;
        int power = n;
        if(n<0)
            power = n * (-1);
        double result = pow_iter(x, power);
        if (n < 0)
            return 1/result;
        return result;
    }

    private double pow(double x, int n){
        if(n==0)
            return 1;

        return x*pow(x, n - 1) ;
    }

    private double pow_iter(double x, int n){
        double result=1;
        for(int i=0; i<n; i++)
            result*=x;
        return result;
    }


    public double myPow2(double x, int n) {
        if(n >= Integer.MAX_VALUE || n <= Integer.MIN_VALUE){
            if(x == 1){
                return 1;
            }
            if(x == -1){
                return n % 2 == 0 ? 1 : -1;
            }
            return 0;
        }

        if(n < 0){
            return 1 / helper(x,Math.abs(n));
        }

        return helper(x, n);
    }

    double helper(double x, int n){
        if(n == 0){
            return 1;
        }
        if(n == 1){
            return x;
        }
        double res = myPow(x, n/2);
        return res * res * (n % 2 == 0 ? 1 : x);
    }

}
