package algorithm.backtrace;

/**
 * Problem Statement:
 * Given a N*N board with the Knight placed on the first block of an empty board.
 * Moving according to the rules of chess knight must visit each square exactly once.
 * Print the order of each the cell in which they are visited.
 *
 * Example:
 *
 * Input :
 * N = 8
 * Output:
 * 0  59  38  33  30  17   8  63
 * 37  34  31  60   9  62  29  16
 * 58   1  36  39  32  27  18   7
 * 35  48  41  26  61  10  15  28
 * 42  57   2  49  40  23   6  19
 * 47  50  45  54  25  20  11  14
 * 56  43  52   3  22  13  24   5
 * 51  46  55  44  53   4  21  12
 *
 * Backtracking works in an incremental way to attack problems.
 * Typically, we start from an empty solution vector and one by one add items
 * (Meaning of item varies from problem to problem. In the context of Knight’s tour problem, an item is a Knight’s move).
 * When we add an item, we check if adding the current item violates the problem constraint,
 * if it does then we remove the item and try other alternatives.
 * If none of the alternatives works out then we go to the previous stage and remove the item added in the previous stage.
 * If we reach the initial stage back then we say that no solution exists.
 * If adding an item doesn't violate constraints then we recursively add items one by one.
 * If the solution vector becomes complete then we print the solution.
 *
 * Backtracking Algorithm for Knight’s tour
 *
 * Following is the Backtracking algorithm for Knight’s tour problem.
 *
 * If all squares are visited
 *     print the solution
 * Else
 *    a) Add one of the next moves to solution vector and recursively
 *    check if this move leads to a solution. (A Knight can make maximum
 *    eight moves. We choose one of the 8 moves in this step).
 *    b) If the move chosen in the above step doesn't lead to a solution
 *    then remove this move from the solution vector and try other
 *    alternative moves.
 *    c) If none of the alternatives work then return false (Returning false
 *    will remove the previously added item in recursion and if false is
 *    returned by the initial call of recursion then "no solution exists" )
 *
 * Time Complexity :
 * There are N2 Cells and for each, we have a maximum of 8 possible moves to choose from, so the worst running time is O(8N^2).
 *
 * Important Note:
 * No order of the xMove, yMove is wrong, but they will affect the running time of the algorithm drastically.
 * For example, think of the case where 8th choice of the move is the correct one and before that our code ran 7 different wrong paths.
 * It’s always a good idea a have a heuristic than to try backtracking randomly.
 * Like, in this case, we know the next step would probably be in south or east direction,
 * then checking the paths which leads their first is a better strategy.
 *
 * Note that Backtracking is not the best solution for the Knight’s tour problem.
 * See below article for other better solutions. The purpose of this post is to explain Backtracking with an example.
 * https://www.geeksforgeeks.org/warnsdorffs-algorithm-knights-tour-problem/
 * Warnsdorff’s algorithm for Knight’s tour problem
 * */
public class KnightTourProblem {
	static int N = 8;

	/* A utility function to print solution
	   matrix sol[N][N] */
	static void printSolution(int sol[][])
	{
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++)
				System.out.print(sol[x][y] + " ");
			System.out.println();
		}
	}

	/* A utility function to check if i,j are valid indexes for N*N chessboard */
	static boolean isSafe(int x, int y, int sol[][])
	{
		return (x >= 0 && x < N && y >= 0 && y < N
				&& sol[x][y] == -1);
	}

	/* A recursive utility function to solve Knight Tour problem */
	static boolean solveKTUtil(int x, int y, int moved,
	                           int sol[][], int xMove[],
	                           int yMove[])
	{
		int k, next_x, next_y;
		if (moved == N * N)
			return true;

		/* Try all next moves from the current coordinate x, y */
		for (k = 0; k < 8; k++) {
			next_x = x + xMove[k];
			next_y = y + yMove[k];
			if (isSafe(next_x, next_y, sol)) {
				sol[next_x][next_y] = moved;
				if (solveKTUtil(next_x, next_y, moved + 1, sol, xMove, yMove))
					return true;
				else
					sol[next_x][next_y] = -1; // backtracking
			}
		}

		return false;
	}

	/* This function solves the Knight Tour problem using Backtracking.  This  function mainly
	   uses solveKTUtil() to solve the problem. It returns false if no complete tour is possible,
	   otherwise return true and prints the tour.
	   Please note that there may be more than one solutions, this function prints one of the feasible solutions.  */
	static boolean solveKT()
	{
		int sol[][] = new int[8][8];

		/* Initialization of solution matrix */
		for (int x = 0; x < N; x++)
			for (int y = 0; y < N; y++)
				sol[x][y] = -1;

        /* xMove[] and yMove[] define next move of Knight.
           xMove[] is for next value of x coordinate
           yMove[] is for next value of y coordinate */
		int xMove[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
		int yMove[] = { 1, 2, 2, 1, -1, -2, -2, -1 };

		// Since the Knight is initially at the first block
		sol[0][0] = 0;

        /* Start from 0,0 and explore all tours using solveKTUtil() */
		if (!solveKTUtil(0, 0, 1, sol, xMove, yMove)) {
			System.out.println("Solution does not exist");
			return false;
		}
		else
			printSolution(sol);

		return true;
	}

	/* Driver Code */
	public static void main(String args[])
	{
		// Function Call
		solveKT();
	}
}
