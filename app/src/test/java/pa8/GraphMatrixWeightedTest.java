package pa8;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GraphMatrixWeightedTest {

    @Test
    public void testAddWeightedEdges() {
        GraphMatrixWeighted graph = new GraphMatrixWeighted(5);

        // Add weighted edges
        graph.addWeightedEdge(0, 1, 3);
        graph.addWeightedEdge(1, 2, 4);
        graph.addWeightedEdge(2, 3, 5);
        graph.addWeightedEdge(3, 4, 6);

        // Check if the adjacency matrix reflects the weighted edges correctly
        assertEquals(3, graph.matrix[0][1]);
        assertEquals(4, graph.matrix[1][2]);
        assertEquals(5, graph.matrix[2][3]);
        assertEquals(6, graph.matrix[3][4]);
    }

    @Test
    public void testBFS() {
        GraphMatrixWeighted graph = new GraphMatrixWeighted(5);
        graph.addWeightedEdge(0, 1, 1);
        graph.addWeightedEdge(1, 2, 1);
        graph.addWeightedEdge(2, 3, 1);
        graph.addWeightedEdge(3, 4, 1);

        // BFS traversal starting from node 0
        int[] bfsResult = graph.bfs(0);

        // Assert the BFS result in the expected order
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, bfsResult);
    }

    @Test
    public void testDFS() {
        GraphMatrixWeighted graph = new GraphMatrixWeighted(5);
        graph.addWeightedEdge(0, 1, 1);
        graph.addWeightedEdge(1, 2, 1);
        graph.addWeightedEdge(2, 3, 1);
        graph.addWeightedEdge(3, 4, 1);

        // DFS traversal starting from node 0
        int[] dfsResult = graph.dfs(0);

        // Assert the DFS result in the expected order
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, dfsResult);
    }

    @Test
    public void testCycleDetection() {
        GraphMatrixWeighted graph = new GraphMatrixWeighted(5);
        graph.addWeightedEdge(0, 1, 1);
        graph.addWeightedEdge(1, 2, 1);
        graph.addWeightedEdge(2, 3, 1);
        graph.addWeightedEdge(3, 4, 1);
        graph.addWeightedEdge(4, 0, 1); // This creates a cycle (0 -> 1 -> 2 -> 3 -> 4 -> 0)

        // Check if the graph has a cycle
        assertTrue(graph.hasCycle());
    }

    @Test
    public void testNoCycle() {
        GraphMatrixWeighted graph = new GraphMatrixWeighted(5);
        graph.addWeightedEdge(0, 1, 1);
        graph.addWeightedEdge(1, 2, 1);
        graph.addWeightedEdge(2, 3, 1);
        graph.addWeightedEdge(3, 4, 1);

        // Check if the graph has a cycle (it should not)
        assertFalse(graph.hasCycle());
    }

    @Test
    public void testShortestPath() {
        GraphMatrixWeighted graph = new GraphMatrixWeighted(5);
        graph.addWeightedEdge(0, 1, 1);
        graph.addWeightedEdge(1, 2, 1);
        graph.addWeightedEdge(2, 3, 1);
        graph.addWeightedEdge(3, 4, 1);

        // Shortest path from node 0 to node 3
        String shortestPath = graph.shortestPath(0, 3);

        // Assert the shortest path is as expected (0 -> 1 -> 2 -> 3)
        assertEquals("0 1 2 3", shortestPath);
    }

    @Test
    public void testShortestPathWithWeights() {
        GraphMatrixWeighted graph = new GraphMatrixWeighted(5);
        graph.addWeightedEdge(0, 1, 3);
        graph.addWeightedEdge(1, 2, 4);
        graph.addWeightedEdge(2, 3, 5);
        graph.addWeightedEdge(3, 4, 6);

        // Shortest path from node 0 to node 4
        String shortestPath = graph.shortestPath(0, 4);

        // Assert the shortest path is as expected (0 -> 1 -> 2 -> 3 -> 4)
        assertEquals("0 1 2 3 4", shortestPath);
    }

    @Test
    public void testNoPath() {
        GraphMatrixWeighted graph = new GraphMatrixWeighted(5);
        graph.addWeightedEdge(0, 1, 1);
        graph.addWeightedEdge(1, 2, 1);
        graph.addWeightedEdge(2, 3, 1);

        // Shortest path from node 0 to node 4 (no path exists)
        String shortestPath = graph.shortestPath(0, 4);

        // Assert no path is found
        assertNull(shortestPath);
    }
}
