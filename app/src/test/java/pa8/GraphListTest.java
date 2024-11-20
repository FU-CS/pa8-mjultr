package pa8;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GraphListTest {

    @Test
    public void testAddEdges() {
        GraphList graph = new GraphList();

        // Add edges to the graph
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        // Check the adjacency list representation
        assertTrue(graph.map.containsKey(0));
        assertTrue(graph.map.get(0).contains(1));
        assertTrue(graph.map.get(0).contains(2));
        assertTrue(graph.map.containsKey(1));
        assertTrue(graph.map.get(1).contains(3));
        assertTrue(graph.map.containsKey(2));
        assertTrue(graph.map.get(2).contains(3));
    }

    @Test
    public void testBFS() {
        GraphList graph = new GraphList();
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        // Perform BFS starting from node 0
        int[] bfsResult = graph.bfs(0);

        // Assert the BFS traversal order
        assertArrayEquals(new int[]{0, 1, 2, 3}, bfsResult);
    }

    @Test
    public void testDFS() {
        GraphList graph = new GraphList();
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        // Perform DFS starting from node 0
        int[] dfsResult = graph.dfs(0);

        // Assert the DFS traversal order
        assertArrayEquals(new int[]{0, 1, 3, 2}, dfsResult);  // DFS might explore 1 before 2
    }

    @Test
    public void testCycleDetection() {
        GraphList graph = new GraphList();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0); // Creates a cycle (0 -> 1 -> 2 -> 0)

        // Check if the graph has a cycle
        assertTrue(graph.hasCycle());
    }

    @Test
    public void testNoCycle() {
        GraphList graph = new GraphList();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);  // No cycle here

        // Check if the graph has a cycle
        assertFalse(graph.hasCycle());
    }

    @Test
    public void testShortestPath() {
        GraphList graph = new GraphList();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        // Shortest path from node 0 to node 3
        String shortestPath = graph.shortestPath(0, 3);

        // Assert the shortest path is as expected (0 -> 1 -> 2 -> 3)
        assertEquals("0 1 2 3", shortestPath);
    }

    @Test
    public void testNoPath() {
        GraphList graph = new GraphList();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        // Shortest path from node 0 to node 4 (no path exists)
        String shortestPath = graph.shortestPath(0, 4);

        // Assert that no path is found
        assertNull(shortestPath);
    }

    @Test
    public void testDisconnectedGraph() {
        GraphList graph = new GraphList();
        graph.addEdge(0, 1);
        graph.addEdge(2, 3);

        // Perform BFS starting from node 0 (should visit 0 and 1 only)
        int[] bfsResult = graph.bfs(0);
        assertArrayEquals(new int[]{0, 1}, bfsResult);

        // Perform DFS starting from node 0 (should visit 0 and 1 only)
        int[] dfsResult = graph.dfs(0);
        assertArrayEquals(new int[]{0, 1}, dfsResult);
    }
}
