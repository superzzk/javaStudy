package leetcode;

/**
 * https://leetcode.com/problems/shifting-letters/
 * We have a string S of lowercase letters, and an integer array shifts.
 * <p>
 * Call the shift of a letter, the next letter in the alphabet, (wrapping around so that 'z' becomes 'a').
 * <p>
 * For example, shift('a') = 'b', shift('t') = 'u', and shift('z') = 'a'.
 * <p>
 * Now for each shifts[i] = x, we want to shift the first i+1 letters of S, x times.
 * <p>
 * Return the final string after all such shifts to S are applied.
 * <p>
 * Example 1:
 * <p>
 * Input: S = "abc", shifts = [3,5,9]
 * Output: "rpl"
 * Explanation:
 * We start with "abc".
 * After shifting the first 1 letters of S by 3, we have "dbc".
 * After shifting the first 2 letters of S by 5, we have "igc".
 * After shifting the first 3 letters of S by 9, we have "rpl", the answer.
 * <p>
 * Note:
 * <p>
 * 1 <= S.length = shifts.length <= 20000
 * 0 <= shifts[i] <= 10 ^ 9
 **/
public class ShiftingLetters {
    public static void main(String[] args) {


//        char a = 'a';
//        System.out.println(a);
//        int ai= Character.getNumericValue(a);
//        System.out.println(ai);
//        int bi = (int)a;
//        System.out.println(bi);
//        char d = (char)bi;
//        System.out.println(d);
//        char c = (char)(ai+87);
//
//        System.out.println(c);
//
//        System.out.println((int)'z');
//
//        Solution solution = new Solution();
//        //String[] strs = {"flower","flow","flight"};
//        String S = "abc";
//        int[] shifts = {3, 5, 9};
//        System.out.println(solution.shiftingLetters(S, shifts));
    }

    static class Solution {
        public String shiftingLetters(String S, int[] shifts) {
            //String rt = "";
            char[] chars = S.toCharArray();
            int n = 0;
            for (int i = 0; i < shifts.length; i++) {
                int shift = shifts[i];
                for (int j = 0; j <= i; j++) {
                    n = (int)(chars[j]) + shift;
                    while (n > 122)
                        n -= 26;
                    chars[j] = (char) (n);
                }
            }
            return String.valueOf(chars);
        }

        /*
        * Approach #1: Prefix Sum [Accepted]

        Intuition
        Let's ask how many times the ith character is shifted.

        Algorithm
        The ith character is shifted shifts[i] + shifts[i+1] + ... + shifts[shifts.length - 1] times.
        That's because only operations at the ith operation and after, affect the ith character.

        Let X be the number of times the current ith character is shifted. Then the next character i+1 is shifted X - shifts[i] times.

        For example, if S.length = 4 and S[0] is shifted X = shifts[0] + shifts[1] + shifts[2] + shifts[3] times,
        then S[1] is shifted shifts[1] + shifts[2] + shifts[3] times, S[2] is shifted shifts[2] + shifts[3] times, and so on.

        In general, we need to do X -= shifts[i] to maintain the correct value of X as we increment i.
        * */
        public String shiftingLetters2(String S, int[] shifts) {
            StringBuilder ans = new StringBuilder();
            int X = 0;
            for (int shift: shifts)
                X = (X + shift) % 26;

            for (int i = 0; i < S.length(); ++i) {
                int index = S.charAt(i) - 'a';
                ans.append((char) ((index + X) % 26 + 97));
                X = Math.floorMod(X - shifts[i], 26);
            }

            return ans.toString();
        }
    }
}
