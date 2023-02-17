package zzk.study.java.core.algorithm.greedy;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.swap;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
* Given an array of jobs where every job has a deadline and
 * associated profit if the job is finished before the deadline.
 * It is also given that every job takes the single unit of time,
 * so the minimum possible deadline for any job is 1.
 * How to maximize total profit if only one job can be scheduled at a time.
 *
 * Examples:
 *
 * Input: Four Jobs with following deadlines and profits
 * JobID  Deadline  Profit
 *   a      4        20
 *   b      1        10
 *   c      1        40
 *   d      1        30
 * Output: Following is maximum profit sequence of jobs
 *         c, a
 *
 *
 * Input:  Five Jobs with following deadlines and profits
 * JobID   Deadline  Profit
 *   a       2        100
 *   b       1        19
 *   c       2        27
 *   d       1        25
 *   e       3        15
 * Output: Following is maximum profit sequence of jobs
 *         c, a, e
 *
 * Following is the algorithm.
 *
 * 1) Sort all jobs in decreasing order of profit.
 * 2) Iterate on jobs in decreasing order of profit. For each job, do the following :
 *  a)Find a time slot i, such that slot is empty and i < deadline and i is greatest.
 *      Put the job in this slot and mark this slot filled.
 *  b)If no such i exists, then ignore the job.
 *
 * Time Complexity of the above solution is O(n2).
 * It can be optimized using Disjoint Set Data Structure.
*/
public class JobSequencingProblem {
    // Each job has a unique-id,
    // profit and deadline
    char id;
    int deadline, profit;

    @Data
    @AllArgsConstructor
    static class Job{
        char id;
        int deadline;
        int profit;
    }

    @Test
    public void test1() {
        //given
        ArrayList<Job> jobs = new ArrayList<>();

        jobs.add(new Job('a', 2, 100));
        jobs.add(new Job('b', 1, 19));
        jobs.add(new Job('c', 2, 27));
        jobs.add(new Job('d', 1, 25));
        jobs.add(new Job('e', 3, 15));

        final char[] jobSchedule = printJobScheduling(jobs, 3);// c, a ,e
        assertArrayEquals(jobSchedule, new char[]{'c', 'a', 'e'});
    }

    /**
     * @param jobs 工作项目List
     * @param noOfJobsSchedule 选取工作项目数量
     * @return
     * */
    private char[] printJobScheduling(ArrayList<Job> jobs, int noOfJobsSchedule){
        jobs.sort((a,b)->{return b.profit - a.profit;});

        // To keep track of free time slots
        boolean[] timeSlots = new boolean[noOfJobsSchedule];

        // To store result (Sequence of jobs)
        char[] jobSchedule = new char[noOfJobsSchedule];

        // Iterate through all given jobs
        for (Job job : jobs) {
            // Find a free slot for this job (Note that we start from the last possible slot)
            for (int j = Math.min(noOfJobsSchedule - 1, job.deadline - 1); j >= 0; j--) {
                // Free slot found
                if (!timeSlots[j]) {
                    timeSlots[j] = true;
                    jobSchedule[j] = job.id;
                    break;
                }
            }
        }

        // Print the sequence
        for (char jb : jobSchedule){
            System.out.print(jb + " ");
        }
        System.out.println();
        return jobSchedule;
    }
}
