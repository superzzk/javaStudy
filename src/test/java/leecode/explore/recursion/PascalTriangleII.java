package leecode.explore.recursion;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a non-negative index k where k ≤ 33, return the kth index row of the Pascal's triangle.
 *
 * Note that the row index starts from 0.
 *
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 *
 * Example:
 *
 * Input: 3
 * Output: [1,3,3,1]
 *
 * Follow up:
 *
 * Could you optimize your algorithm to use only O(k) extra space?
 **/
public class PascalTriangleII {
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
        caclOneRowPascalNumber(numRows,upRow);
        return result;
    }

    public List<Integer> getRow(int rowIndex) {
        List<Integer> result = new ArrayList<>();
        rowIndex++;
        if(rowIndex<1)
            return result;
        List<Integer> upRow = new ArrayList<>();
        return caclOneRowPascalNumber(rowIndex,upRow);
    }

    private List<Integer> caclOneRowPascalNumber(int row, List<Integer> upRow){
        List<Integer> line = new ArrayList<>();
        if(row==1){
            line.add(1);
            return line;
        }
        upRow = caclOneRowPascalNumber(--row, upRow);
        line.add(1);
        for(int i=1; i<upRow.size(); i++){
            line.add(upRow.get(i-1)+upRow.get(i));
        }
        line.add(1);

        return line;
    }
}
