package leetcode;

import scala.Int;

import java.util.HashSet;
import java.util.Set;

/**
 * 202
 * Write an algorithm to determine if a number is "happy".
 *
 * A happy number is a number defined by the following process:
 * Starting with any positive integer, replace the number by the sum of the squares of its digits,
 * and repeat the process until the number equals 1 (where it will stay),
 * or it loops endlessly in a cycle which does not include 1.
 * Those numbers for which this process ends in 1 are happy numbers.
 *
 * Example:
 *
 * Input: 19
 * Output: true
 * Explanation:
 * 1^2 + 9^2 = 82
 * 8^2 + 2^2 = 68
 * 6^2 + 8^2 = 100
 * 1^2 + 0^2 + 0^2 = 1
 **/
public class HappyNumber {

    HappyNumber.Solution s = new HappyNumber.Solution();

    public static void main(String[] args) {
        HappyNumber ooo = new HappyNumber();

        int num = 19;
        boolean rt = ooo.s.isHappy(num);
        System.out.println(rt);
    }

    class Solution {
        public boolean isHappy(int n) {
            Set<Integer> used = new HashSet<>();

            while(true) {
                int total = 0;
                for (char c : Integer.toString(n).toCharArray()) {
                    total += Math.pow( (int) c-'0',2);
                }
                if(total ==1)
                    return true;
                if (used.contains(total)) {
                    return false;
                }
                else {
                    n=total;
                    used.add(total);
                    continue;
                }
            }
        }
    }

    class Solution2 {
        Set<Long> set = new HashSet<>();
        public boolean isHappy(int n) {
            long sum = 0;
            while (n != 0) {
                int digit = n % 10;
                sum += digit * digit;
                n /= 10;
            }
            if (sum == 1) return true;
            if (sum == 0 || !set.add(sum)) return false;
            return isHappy((int)sum);
        }
    }

}
