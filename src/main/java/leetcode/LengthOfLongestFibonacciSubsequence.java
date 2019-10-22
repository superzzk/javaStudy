package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 873
 * A sequence X_1, X_2, ..., X_n is fibonacci-like if:
 *
 *     n >= 3
 *     X_i + X_{i+1} = X_{i+2} for all i + 2 <= n
 *
 * Given a strictly increasing array A of positive integers forming a sequence,
 * find the length of the longest fibonacci-like subsequence of A.  If one does not exist, return 0.
 *
 * (Recall that a subsequence is derived from another sequence A by deleting any number of elements
 * (including none) from A, without changing the order of the remaining elements.
 * For example, [3, 5, 8] is a subsequence of [3, 4, 5, 6, 7, 8].)
 *
 *
 *
 * Example 1:
 *
 * Input: [1,2,3,4,5,6,7,8]
 * Output: 5
 * Explanation:
 * The longest subsequence that is fibonacci-like: [1,2,3,5,8].
 *
 * Example 2:
 *
 * Input: [1,3,7,11,12,14,18]
 * Output: 3
 * Explanation:
 * The longest subsequence that is fibonacci-like:
 * [1,11,12], [3,11,14] or [7,11,18].
 *
 * Note:
 *
 *     3 <= A.length <= 1000
 *     1 <= A[0] < A[1] < ... < A[A.length - 1] <= 10^9
 *     (The time limit has been reduced by 50% for submissions in Java, C, and C++.)
 **/
public class LengthOfLongestFibonacciSubsequence {

    LengthOfLongestFibonacciSubsequence.Solution s = new LengthOfLongestFibonacciSubsequence.Solution();

    public static void main(String[] args) {
        LengthOfLongestFibonacciSubsequence ooo = new LengthOfLongestFibonacciSubsequence();

        int[] nums = {1,3,7,11,12,14,18};
        int rt = ooo.s.lenLongestFibSubseq(nums);
        System.out.println(rt);
    }

    class Solution {
        public int lenLongestFibSubseq(int[] A) {
            int count=0;
            if(A.length <3)
                return 0;
            for (int i = 0; i < A.length-count; i++) {
                for (int j = i + 1; j < A.length; j++) {
                    int c = lenLongestFibSubseq(A,i,j);
                    count = c > count ? c : count;
                }
            }
            return count > 0 ? count + 2 : 0;
        }

        //选定两个初始点，计算已该两点为初始的最大Fibonacci Number
        private int lenLongestFibSubseq(int nums[], int index1, int index2){
            int count=0;
            while(true){
                int nextFib = nums[index1] + nums[index2];
                int nextPos = Arrays.binarySearch(nums, nextFib, index2+1,nums.length-1);
                if(nextPos>0){
                    count++;
                    index1=index2;
                    index2=nextPos;
                }else{
                    break;
                }
            }

            return  count;
        }
    }

    class Solution2 {
        public int lenLongestFibSubseq(int[] A) {
            int N = A.length;
            Set<Integer> S = new HashSet();
            for (int x: A) S.add(x);

            int ans = 0;
            for (int i = 0; i < N; ++i)
                for (int j = i+1; j < N; ++j) {
                    /* With the starting pair (A[i], A[j]),
                     * y represents the future expected value in
                     * the fibonacci subsequence, and x represents
                     * the most current value found. */
                    int x = A[j], y = A[i] + A[j];
                    int length = 2;
                    while (S.contains(y)) {
                        // x, y -> y, x+y
                        int tmp = y;
                        y += x;
                        x = tmp;
                        ans = Math.max(ans, ++length);
                    }
                }

            return ans >= 3 ? ans : 0;
        }
    }

    /*
    * Approach 2: Dynamic Programming
    Intuition
    Think of two consecutive terms A[i], A[j] in a fibonacci-like subsequence as a single node (i, j),
    and the entire subsequence is a path between these consecutive nodes. For example, with the fibonacci-like
    subsequence (A[1] = 2, A[2] = 3, A[4] = 5, A[7] = 8, A[10] = 13),
    we have the path between nodes (1, 2) <-> (2, 4) <-> (4, 7) <-> (7, 10).

    The motivation for this is that two nodes (i, j) and (j, k) are connected if and only if A[i] + A[j] == A[k],
    and we needed this amount of information to know about this connection.
    Now we have a problem similar to Longest Increasing Subsequence.

    Algorithm

    Let longest[i, j] be the longest path ending in [i, j]. Then longest[j, k] = longest[i, j] + 1 if (i, j) and (j, k) are connected.
    Since i is uniquely determined as A.index(A[k] - A[j]), this is efficient: we check for each j < k what i is potentially,
    and update longest[j, k] accordingly.
    * */
    class Solution3 {
        public int lenLongestFibSubseq(int[] A) {
            int N = A.length;
            Map<Integer, Integer> index = new HashMap();
            for (int i = 0; i < N; ++i)
                index.put(A[i], i);

            Map<Integer, Integer> longest = new HashMap();
            int ans = 0;

            for (int k = 0; k < N; ++k)
                for (int j = 0; j < k; ++j) {
                    int i = index.getOrDefault(A[k] - A[j], -1);
                    if (i >= 0 && i < j) {
                        // Encoding tuple (i, j) as integer (i * N + j)
                        int cand = longest.getOrDefault(i * N + j, 2) + 1;
                        longest.put(j * N + k, cand);
                        ans = Math.max(ans, cand);
                    }
                }

            return ans >= 3 ? ans : 0;
        }
    }
}
