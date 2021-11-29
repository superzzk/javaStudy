package com.zzk.study.leetcode.explore.recursion;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
 *
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 *
 * Example:
 *
 * Input: 5
 * Output:
 * [
 *      [1],
 *     [1,1],
 *    [1,2,1],
 *   [1,3,3,1],
 *  [1,4,6,4,1]
 * ]
 **/
public class PascalTriangle {
    @Test
    public void run(){
        List<List<Integer>> result = generate(5);
        System.out.println(result);
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        if(numRows<1)
            return result;
        List<Integer> upRow = new ArrayList<>();
        caclOneRowPascalNumber(numRows,upRow,result);
        return result;
    }

    private List<Integer> caclOneRowPascalNumber(int row, List<Integer> upRow, List<List<Integer>> result){
        List<Integer> line = new ArrayList<>();
        if(row==1){
            line.add(1);
            result.add(line);
            return line;
        }
        upRow = caclOneRowPascalNumber(--row, upRow, result);
        line.add(1);
        for(int i=1; i<upRow.size(); i++){
            line.add(upRow.get(i-1)+upRow.get(i));
        }
        line.add(1);

        result.add(line);
        return line;
    }

    private int caclPascalNumber(int i, int j){
        if(j==1 || j==i)
            return 1;
        return caclPascalNumber(i - 1, j-1) + caclPascalNumber(i - 1, j);
    }
}
