package pa8;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GraphListWeightedTest {

    @Test
    public void testAddWeightedEdge() {
        GraphListWeighted graph = new GraphListWeighted(5);

        // Add weighted edges
        graph.addWeightedEdge(0, 1, 5);
        graph.addWeightedEdge(0, 2, 10);
        graph.addWeightedEdge(1, 3, 2);
        graph.addWeightedEdge(2, 3, 3);

        // Check if the edge weights are correctly assigned
        assertEquals(5, graph.weights[0][1]);
        assertEquals(10, graph.weights[0][2]);
        assertEquals(2, graph.weights[1][3]);
        assertEquals(3, graph.weights[2][3]);
    }

    @Test
    public void testBFS() {
        GraphListWeighted graph = new GraphListWeighted(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        // Perform BFS starting from vertex 0
        int[] bfsResult = graph.bfs(0);

        // Expected BFS distances from vertex 0
        assertArrayEquals(new int[]{0, 1, 1, 2, -1}, bfsResult);  // No connection to vertex 4
    }

    @Test
    public void testDFS() {
        GraphListWeighted graph = new GraphListWeighted(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        // Perform DFS starting from vertex 0
        int[] dfsResult = graph.dfs(0);

        // Expected DFS distances from vertex 0
        assertArrayEquals(new int[]{0, 1, 1, 2, -1}, dfsResult);  // No connection to vertex 4
    }

    @Test
    public void testCycleDetection() {
        GraphListWeighted graph = new GraphListWeighted(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);  // Creates a cycle (0 -> 1 -> 2 -> 0)

        // Check if the graph has a cycle
        assertTrue(graph.hasCycle());
    }

    @Test
    public void testNoCycle() {
        GraphListWeighted graph = new GraphListWeighted(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);  // No cycle here

        // Check if the graph has a cycle
        assertFalse(graph.hasCycle());
    }

    @Test
    public void testDijkstra() {
        GraphListWeighted graph = new GraphListWeighted(5);
        graph.addWeightedEdge(0, 1, 5);
        graph.addWeightedEdge(0, 2, 10);
        graph.addWeightedEdge(1, 2, 3);
        graph.addWeightedEdge(1, 3, 2);
        graph.addWeightedEdge(2, 3, 1);

        // Perform Dijkstra's algorithm starting from vertex 0
        int[] dijkstraResult = graph.dijkstra(0);

        // Assert the shortest distances from vertex 0
        assertArrayEquals(new int[]{0, 5, 8, 7, Integer.MAX_VALUE}, dijkstraResult);
        // Expected distances: 0 -> 0, 1 -> 5, 2 -> 8, 3 -> 7, 4 -> ∞ (no path)
    }

    @Test
    public void testDisconnectedGraph() {
        GraphListWeighted graph = new GraphListWeighted(5);
        graph.addEdge(0, 1);
        graph.addEdge(2, 3);

        // Perform BFS starting from node 0 (should visit only nodes 0 and 1)
        int[] bfsResult = graph.bfs(0);
        assertArrayEquals(new int[]{0, 1, -1, -1, -1}, bfsResult);

        // Perform DFS starting from node 0 (should visit only nodes 0 and 1)
        int[] dfsResult = graph.dfs(0);
        assertArrayEquals(new int[]{0, 1, -1, -1, -1}, dfsResult);
    }

    @Test
    public void testShortestPath() {
        GraphListWeighted graph = new GraphListWeighted(5);
        graph.addWeightedEdge(0, 1, 5);
        graph.addWeightedEdge(1, 2, 2);
        graph.addWeightedEdge(2, 3, 3);
        graph.addWeightedEdge(3, 4, 1);

        // Shortest path from vertex 0 to vertex 3
        String shortestPath = graph.shortestPath(0, 3);

        // Expected result: Path is 0 -> 1 -> 2 -> 3
        assertEquals("0 1 2 3", shortestPath);
    }

    @Test
    public void testNoPath() {
        GraphListWeighted graph = new GraphListWeighted(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        // No path from node 0 to node 4
        String shortestPath = graph.shortestPath(0, 4);

        // Assert that no path is found
        assertNull(shortestPath);
    }
}
