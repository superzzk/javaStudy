package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Count the number of prime numbers less than a non-negative number, n.
 *
 * Example:
 *
 * Input: 10
 * Output: 4
 * Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
 **/
public class CountPrimes {
    CountPrimes.Solution s = new CountPrimes.Solution();

    public static void main(String[] args) {
        CountPrimes ooo = new CountPrimes();

        int num = 3;
        num = 499979;
        num = 10;
        int rt = ooo.s.countPrimes2(num);
        System.out.println(rt);
    }

    class Solution {
        Set<Integer> primes = new HashSet<>();

        //暴力计算每个数值，非常慢，无法实用
        public int countPrimes(int n) {
            int count = 0;
            for (int i = 2; i < n; i++) {
                if (isPrime(i))
                    count++;
            }
            return count;
        }

        private boolean isPrime(int num) {
            for (int i = 2; i < num; i++) {
                if (num % i == 0)
                    return false;
            }
            primes.add(num);
            return true;
        }

        public int countPrimes2(int n) {
            if(n==0 || n==1) return 0;
            int count = 0;
            //此处测试发现用char比用int快很多
            char[] primes = new char[n];//0--prime, 1--not prime

            for(int i=2;i<n;i++){
                if(primes[i]==0){
                    count++;
                    for(int j=2; i * j < n; j++){
                        primes[i * j] = 1;
                    }
                }
            }

            return count;
        }
    }

    /**
     * 先计算所有的非Prime
     */
    public int countPrimes(int n) {
        if(n==0 || n==1) return 0;
        int[] primes = new int[n];
        int res = n-2;
        //1 => not prime
        //0 => prime
        //initially all the elements are prime
        //marking 0 and 1 as non-prime
        primes[0] = 1;
        primes[1] = 1;
        for(int i=2;i<n; i++) {
            //if the current element is prime
            if(primes[i] == 0) {
                for(int j=2; i*j<n; j++) {
                    //mark all the fators of current prime as non-prime
                    if(primes[i*j] != 1){
                        primes[i*j] = 1;
                        res--;
                    }
                }
            }
        }
        return res;
    }

    /**
    * This solution is optimized by checking only the odd numbers.
     *
     * I started by thinking:
     * If n is less than or equal to 2, there is only one prime element.
     * If n is greater than 2, there is at least one prime element (2).
     *
     * Assume all odd numbers greater than or equal to 3 are prime numbers.
     * Iterate through all the odd numbers from 3 to n. During each iteration,
     * set all the odd product of the prime number to be non-prime number.
     * (e.g. when iterating through 3, we know 9, 15, 18, ... cannot be prime number.)
     * Note that we don't have to check the even number products.
    */
    class Solution4 {
        public int countPrimes(int n) {
            if (n <= 2){
                return 0;
            }

            boolean[] notPrime = new boolean[n];
            int ct = 1;

            for (int i = 3; i < n; i+=2){
                if (!notPrime[i]) {
                    ct++;
                    for (int j = 3; i*j < n; j+=2){
                        notPrime[i*j] = true;
                    }
                }
            }
            return ct;
        }
    }
}
