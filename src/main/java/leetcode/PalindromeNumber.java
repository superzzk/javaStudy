package leetcode;

/**
 * Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.
 * <p>
 * Example 1:
 * <p>
 * Input: 121
 * Output: true
 * <p>
 * Example 2:
 * <p>
 * Input: -121
 * Output: false
 * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
 * <p>
 * Example 3:
 * <p>
 * Input: 10
 * Output: false
 * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
 * <p>
 * Follow up:
 * Coud you solve it without converting the integer to a string?
 **/
public class PalindromeNumber {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int a = 121;
        System.out.println("Is " + a + " a palindrome number? " + solution.isPalindrome2(a));
    }


    static class Solution {
        public boolean isPalindrome(int x) {
            String s = String.valueOf(x);
            String s_rev = reverseString(s);
            if (s.equals(s_rev))
                return true;
            return false;
        }

        private String reverseString(String str) {
            String revString = "";
            for (int i = str.length() - 1; i >= 0; i--) {
                char c = str.charAt(i);
                revString += c;
            }
            return revString;
        }

        public boolean isPalindrome2(int x) {
            if(x<0)
                return false;
            int a = reverseInt(x);

            if(a==x)
                return true;
            return false;
        }

        private int reverseInt(int x) {
            int rt=0;
            x = x > 0 ? x : x * (-1);
            int remainder;
            while(x>0){
                remainder=x%10;
                x=x/10;
                rt=rt*10+remainder;
            }
            return rt;
        }

        /*
        * Approach 1: Revert half of the number

        Intuition

            The first idea that comes to mind is to convert the number into string, and check if the string is a palindrome,
            but this would require extra non-constant space for creating the string which is not allowed by the problem description.

            Second idea would be reverting the number itself, and then compare the number with original number,
            if they are the same, then the number is a palindrome. However, if the reversed number is larger
            than int.MAX\text{int.MAX}int.MAX, we will hit integer overflow problem.

            Following the thoughts based on the second idea, to avoid the overflow issue of the reverted number,
            what if we only revert half of the int number? After all, the reverse of the last half of the palindrome
            should be the same as the first half of the number, if the number is a palindrome.

            For example, if the input is 1221, if we can revert the last part of the number "1221" from "21" to "12",
            and compare it with the first half of the number "12", since 12 is the same as 12, we know that the number is a palindrome.

            Let's see how we could translate this idea into an algorithm.

        Algorithm

            First of all we should take care of some edge cases. All negative numbers are not palindrome,
            for example: -123 is not a palindrome since the '-' does not equal to '3'. So we can return false for all negative numbers.

            Now let's think about how to revert the last half of the number. For number 1221, if we do 1221 % 10,
            we get the last digit 1, to get the second to the last digit, we need to remove the last digit from 1221,
            we could do so by dividing it by 10, 1221 / 10 = 122. Then we can get the last digit again by doing a modulus by 10,
            122 % 10 = 2, and if we multiply the last digit by 10 and add the second last digit, 1 * 10 + 2 = 12,
            it gives us the reverted number we want. Continuing this process would give us the reverted number with more digits.

            Now the question is, how do we know that we've reached the half of the number?

            Since we divided the number by 10, and multiplied the reversed number by 10,
            when the original number is less than the reversed number, it means we've processed half of the number digits.
        * */
        public boolean IsPalindrome3(int x) {
            // Special cases:
            // As discussed above, when x < 0, x is not a palindrome.
            // Also if the last digit of the number is 0, in order to be a palindrome,
            // the first digit of the number also needs to be 0.
            // Only 0 satisfy this property.
            if(x < 0 || (x % 10 == 0 && x != 0)) {
                return false;
            }

            int revertedNumber = 0;
            while(x > revertedNumber) {
                revertedNumber = revertedNumber * 10 + x % 10;
                x /= 10;
            }

            // When the length is an odd number, we can get rid of the middle digit by revertedNumber/10
            // For example when the input is 12321, at the end of the while loop we get x = 12, revertedNumber = 123,
            // since the middle digit doesn't matter in palidrome(it will always equal to itself), we can simply get rid of it.
            return x == revertedNumber || x == revertedNumber/10;
        }
    }


}

