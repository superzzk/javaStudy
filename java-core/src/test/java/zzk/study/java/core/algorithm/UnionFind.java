package zzk.study.java.core.algorithm;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.*;

//https://www.geeksforgeeks.org/union-find/
// Java Program for union-find algorithm to detect cycle in a graph
public class UnionFind {
    @Test
    public void test(){
		/* Let us create the following graph
		0
		| \
		|  \
		1---2 */
        Graph graph = new Graph(3, 3);

        // add edge 0-1
        graph.edges[0].src = 0;
        graph.edges[0].dest = 1;

        // add edge 1-2
        graph.edges[1].src = 1;
        graph.edges[1].dest = 2;

        // add edge 0-2
        graph.edges[2].src = 0;
        graph.edges[2].dest = 2;

        Assertions.assertTrue(isCycle(graph));
    }
    class Graph {
        int verticesNo, edgesNo; // V-> no. of vertices & E->no.of edges
        Edge edges[]; // /collection of all edges

        class Edge {
            int src, dest;
        }

        // Creates a graph with V vertices and E edges
        Graph(int verticesNo, int edgesNo) {
            this.verticesNo = verticesNo;
            this.edgesNo = edgesNo;
            edges = new Edge[this.edgesNo];
            for (int i = 0; i < edgesNo; ++i)
                edges[i] = new Edge();
        }

        // A utility function to find the subset of an element i
        int find(int parent[], int i) {
            if (parent[i] == -1)
                return i;
            return find(parent, parent[i]);
        }

        // A utility function to do union of two subsets
        void Union(int parent[], int x, int y) {
            parent[x] = y;
        }
    }

    // The main function to check whether a given graph contains cycle or not
    private boolean isCycle(Graph graph) {
        // Allocate memory for creating V subsets
        int parent[] = new int[graph.verticesNo];

        // Initialize all subsets as single element sets
        for (int i = 0; i < graph.verticesNo; ++i)
            parent[i] = -1;

        // Iterate through all edges of graph, find subset of both
        // vertices of every edge, if both subsets are same, then
        // there is cycle in graph.
        for (int i = 0; i < graph.edgesNo; ++i) {
            int x = graph.find(parent, graph.edges[i].src);
            int y = graph.find(parent, graph.edges[i].dest);

            if (x == y)
                return true;

            graph.Union(parent, x, y);
        }
        return false;
    }

}
