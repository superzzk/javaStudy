package zzk.study.java.core.algorithm.greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 基本的贪婪算法可以看 {@link JobSequencingProblem}
 * The costly operation in the Greedy solution is to assign a free slot for a job.
 * We were traversing each and every slot for a job and assigning
 * the greatest possible time slot(<deadline) which was available.
 * <p>
 * What does greatest time slot means?
 * Suppose that a job J1 has a deadline of time t = 5.
 * We assign the greatest time slot which is free and less than the deadline i.e 4-5 for this job.
 * Now another job J2 with deadline of 5 comes in,
 * so the time slot allotted will be 3-4 since 4-5 has already been allotted to job J1.
 * <p>
 * Why to assign greatest time slot(free) to a job?
 * Now we assign the greatest possible time slot since if we assign a time slot
 * even lesser than the available one than there might be some other job which will miss its deadline.
 * Example:
 * J1 with deadline d1 = 5, profit 40
 * J2 with deadline d2 = 1, profit 20
 * Suppose that for job J1 we assigned time slot of 0-1.
 * Now job J2 cannot be performed since we will perform Job J1 during that time slot.
 * <p>
 * Using Disjoint Set for Job Sequencing
 * All time slots are individual sets initially. We first find the maximum deadline of all jobs.
 * Let the max deadline be m. We create m+1 individual sets.
 * If a job is assigned a time slot of t where t => 0, then the job is scheduled during [t-1, t].
 * So a set with value X represents the time slot [X-1, X].
 * We need to keep track of the greatest time slot available
 * which can be allotted to a given job having deadline.
 * We use the parent array of Disjoint Set Data structures for this purpose.
 * The root of the tree is always the latest available slot.
 * If for a deadline d, there is no slot available, then root would be 0. Below are detailed steps.
 * <p>
 * Initialize Disjoint Set: Creates initial disjoint sets.
 * <p>
 * // m is maximum deadline of a job
 * parent = new int[m + 1];
 * <p>
 * // Every node is a parent of itself
 * for (int i = 0; i ≤ m; i++)
 * parent[i] = i;
 * <p>
 * Find : Finds the latest time slot available.
 * // Returns the maximum available time slot
 * find(s)
 * {
 * // Base case
 * if (s == parent[s])
 * return s;
 * <p>
 * // Recursive call with path compression
 * return parent[s] = find(parent[s]);
 * }
 * <p>
 * Union :
 * Merges two sets.
 * // Makes u as parent of v.
 * union(u, v)
 * {
 * // update the greatest available
 * // free slot to u
 * parent[v] = u;
 * }
 * <p>
 * How come find returns the latest available time slot?
 * Initially all time slots are individual slots. So the time slot returned is always maximum.
 * When we assign a time slot ‘t’ to a job, we do union of ‘t’ with ‘t-1’ in a way that ‘t-1’
 * becomes parent of ‘t’. To do this we call union(t-1, t).
 * This means that all future queries for time slot t would now return the latest
 * time slot available for set represented by t-1.
 */
public class JobSequencingUsingDisjointSet {
    // A Simple Disjoint Set Data Structure
    static class DisjointSet {
        int parent[];

        // Constructor
        DisjointSet(int n) {
            parent = new int[n + 1];
            // Every node is a parent of itself
            for (int i = 0; i <= n; i++)
                parent[i] = i;
        }

        // Path Compression
        int find(int s) {
        /* Make the parent of the nodes in the path
           from u--> parent[u] point to parent[u] */
            if (s == parent[s])
                return s;
            return parent[s] = find(parent[s]);
        }

        // Makes u as parent of v.
        void merge(int u, int v) {
            //update the greatest available free slot to u
            parent[v] = u;
        }
    }

    static class Job implements Comparator<Job> {
        // Each job has a unique-id, profit and deadline
        char id;
        int deadline, profit;

        // Constructors
        public Job() {}

        public Job(char id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }

        // Returns the maximum deadline from the set of jobs
        public static int findMaxDeadline(ArrayList<Job> arr) {
            int ans = Integer.MIN_VALUE;
            for (Job temp : arr)
                ans = Math.max(temp.deadline, ans);
            return ans;
        }

        // Prints optimal job sequence
        public static void printJobScheduling(ArrayList<Job> arr) {
            // Sort Jobs in descending order on the basis
            // of their profit
            Collections.sort(arr, new Job());

            // Find the maximum deadline among all jobs and
            // create a disjoint set data structure with
            // maxDeadline disjoint sets initially.
            int maxDeadline = findMaxDeadline(arr);
            DisjointSet dsu = new DisjointSet(maxDeadline);

            // Traverse through all the jobs
            for (Job temp : arr) {
                // Find the maximum available free slot for
                // this job (corresponding to its deadline)
                int availableSlot = dsu.find(temp.deadline);


                // If maximum available free slot is greater
                // than 0, then free slot available
                if (availableSlot > 0) {
                    // This slot is taken by this job 'i'
                    // so we need to update the greatest free
                    // slot. Note that, in merge, we make
                    // first parameter as parent of second
                    // parameter.  So future queries for
                    // availableSlot will return maximum slot
                    // from set of "availableSlot - 1"
                    dsu.merge(dsu.find(availableSlot - 1),
                            availableSlot);
                    System.out.print(temp.id + " ");
                }
            }
            System.out.println();
        }

        // Used to sort in descending order on the basis
        // of profit for each job
        public int compare(Job j1, Job j2) {
            return j1.profit > j2.profit ? -1 : 1;
        }
    }

    // Driver code

    public static void main(String args[]) {
        ArrayList<Job> arr = new ArrayList<Job>();
        arr.add(new Job('a', 2, 100));
        arr.add(new Job('b', 1, 19));
        arr.add(new Job('c', 2, 27));
        arr.add(new Job('d', 1, 25));
        arr.add(new Job('e', 3, 15));
        System.out.println("Following jobs need to be " +
                "executed for maximum profit");
        Job.printJobScheduling(arr);
    }

}
