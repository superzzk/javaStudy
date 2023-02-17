package zzk.study.java.core.algorithm.greedy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Activity Selection problem
 * https://en.wikipedia.org/wiki/Activity_selection_problem
 * <p>
 * You are given n activities with their start and finish times.
 * Select the maximum number of activities that can be performed by a single person,
 * assuming that a person can only work on a single activity at a time.
 * <p>
 * Example:
 * <p>
 * Example 1 : Consider the following 3 activities sorted by finish time.
 * start[]  =  {10, 12, 20};
 * finish[] =  {20, 25, 30};
 * A person can perform at most two activities. The
 * maximum set of activities that can be executed
 * is {0, 2} [ These are indexes in start[] and finish[] ]
 * <p>
 * Example 2 : Consider the following 6 activities sorted by finish time.
 * start[]  =  {1, 3, 0, 5, 8, 5};
 * finish[] =  {2, 4, 6, 7, 9, 9};
 * A person can perform at most four activities. The maximum set of activities that can be executed
 * is {0, 1, 3, 4} [ These are indexes in start[] and finish[] ]
 * <p>
 * The greedy choice is to always pick the next activity whose finish time is least among the remaining activities
 * and the start time is more than or equal to the finish time of previously selected activity.
 * <p>
 * 1) Sort the activities according to their finishing time
 * 2) Select the first activity from the sorted array and print it.
 * 3) Do following for remaining activities in the sorted array.
 * a) If the start time of this activity is greater than or equal to the finish time of previously selected activity
 * then select this activity and print it.
 */
public class ActivitySelection {

    // driver program to test above function
    @Test
    public void test1() {
        int s[] = {1, 3, 0, 5, 8, 5};
        int f[] = {2, 4, 6, 7, 9, 9};
        int n = s.length;

        printMaxActivities(s, f, n);
    }

    // Prints a maximum set of activities that can be done by a single person, one at a time.
    public static void printMaxActivities(int start[], int finish[], int numOfActivities) {
        int i, j;
        System.out.print("Following activities are selected : ");

        // The first activity always gets selected
        i = 0;
        System.out.print(i + " ");

        // Consider rest of the activities
        for (j = 1; j < numOfActivities; j++) {
            // If this activity has start time greater than or
            // equal to the finish time of previously selected activity, then select it
            if (start[j] >= finish[i]) {
                System.out.print(j + " ");
                i = j;
            }
        }
    }


    // 如果输入是未排序的
    static class GFG {

        // Pair class
        static class Pair {

            int first;
            int second;

            Pair(int first, int second) {
                this.first = first;
                this.second = second;
            }
        }

        static void SelectActivities(int s[], int f[]) {
            ArrayList<Pair> ans = new ArrayList<>();

            // Minimum Priority Queue to sort activities in
            // ascending order of finishing time (f[i]).
            PriorityQueue<Pair> queue = new PriorityQueue<>(
                    (p1, p2) -> p1.first - p2.first);

            for (int i = 0; i < s.length; i++) {
                // Pushing elements in priority queue where the key is f[i]
                queue.add(new Pair(f[i], s[i]));
            }

            Pair it = queue.poll();
            int start = it.second;
            int end = it.first;
            ans.add(new Pair(start, end));

            while (!queue.isEmpty()) {
                Pair task = queue.poll();
                if (task.second >= end) {
                    start = task.second;
                    end = task.first;
                    ans.add(new Pair(start, end));
                }
            }
            System.out.println(
                    "Following Activities should be selected. \n");

            for (Pair itr : ans) {
                System.out.println(
                        "Activity started at: " + itr.first
                                + " and ends at  " + itr.second);
            }
        }

        // Driver Code
        public static void main(String[] args) {

            int s[] = {1, 3, 0, 5, 8, 5};
            int f[] = {2, 4, 6, 7, 9, 9};

            // Function call
            SelectActivities(s, f);
        }
    }
}
