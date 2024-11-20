package pa8;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GraphMatrixTest {

    @Test
    public void testAddEdges() {
        // Create a new graph with 5 nodes
        GraphMatrix graph = new GraphMatrix(5);

        // Add edges
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addWeightedEdge(0, 2, 5);

        // Check if the adjacency matrix reflects the edges correctly
        assertEquals(1, graph.matrix[0][1]);
        assertEquals(1, graph.matrix[0][4]);
        assertEquals(1, graph.matrix[1][2]);
        assertEquals(1, graph.matrix[2][3]);
        assertEquals(1, graph.matrix[3][4]);
        assertEquals(5, graph.matrix[0][2]); // Checking weighted edge
    }

    @Test
    public void testBFS() {
        GraphMatrix graph = new GraphMatrix(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        // BFS traversal starting from node 0
        int[] bfsResult = graph.bfs(0);

        // Assert the BFS result in the expected order
        assertArrayEquals(new int[]{0, 1, 4, 2, 3}, bfsResult);
    }

    @Test
    public void testDFS() {
        GraphMatrix graph = new GraphMatrix(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        // DFS traversal starting from node 0
        int[] dfsResult = graph.dfs(0);

        // Assert the DFS result in the expected order
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, dfsResult);
    }

    @Test
    public void testCycleDetection() {
        GraphMatrix graph = new GraphMatrix(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 0); // This creates a cycle (0 -> 1 -> 2 -> 3 -> 4 -> 0)

        // Check if the graph has a cycle
        assertTrue(graph.hasCycle());
    }

    @Test
    public void testNoCycle() {
        GraphMatrix graph = new GraphMatrix(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        // Check if the graph has a cycle (it should not)
        assertFalse(graph.hasCycle());
    }

    @Test
    public void testShortestPath() {
        GraphMatrix graph = new GraphMatrix(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        // Shortest path from node 0 to node 3
        String shortestPath = graph.shortestPath(0, 3);

        // Assert the shortest path is as expected
        assertEquals("0 1 2 3", shortestPath);
    }

    @Test
    public void testShortestPathWithWeightedEdge() {
        GraphMatrix graph = new GraphMatrix(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addWeightedEdge(0, 2, 5); // Weighted edge from 0 to 2

        // Shortest path from node 0 to node 2
        String shortestPath = graph.shortestPath(0, 2);

        // Assert the shortest path includes the weighted edge (it should return just 0 and 2)
        assertEquals("0 2", shortestPath);
    }
}
