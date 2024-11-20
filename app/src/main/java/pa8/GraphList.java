package pa8;
import java.util.Map;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class GraphList implements Graph{
    Map<Integer, ArrayList<Integer>> map;

    public GraphList(){
        this.map = new HashMap<>();
    }
    

    public void addEdge(int v, int w){
        if (map.containsKey(v)){
            map.put(v, new ArrayList<Integer>());
            map.get(v).add(w);
        }
        else{
            map.get(v).add(w);
        }
    }

    public void addWeightedEdge(int source, int dest, int weight){
        return;
    }
    public int[] bfs(int start) {
        int numVertices = map.size(); // Get the number of vertices in the graph
        int[] visitedOrder = new int[numVertices]; // Array to store visited vertices in order
        int visitedCount = 0; // Counter for visited vertices
        boolean[] visited = new boolean[numVertices]; // To track visited vertices
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true; // Mark the start vertex as visited
        queue.offer(start); // Add the start vertex to the queue
        visitedOrder[visitedCount++] = start; // Add start vertex to visitedOrder

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll(); // Get the next vertex from the queue

            // Iterate through neighbors of the current vertex
            for (int neighbor : map.getOrDefault(currentVertex, new ArrayList<>())) { 
                if (!visited[neighbor]) { // If the neighbor hasn't been visited
                    visited[neighbor] = true; // Mark the neighbor as visited
                    queue.offer(neighbor); // Add the neighbor to the queue
                    visitedOrder[visitedCount++] = neighbor; // Add neighbor to visitedOrder
                }
            }
        }

        // Create a new array with only the visited vertices
        int[] result = new int[visitedCount];
        System.arraycopy(visitedOrder, 0, result, 0, visitedCount);

        return result; // Return the array of visited vertices in BFS order
        }
    public int[] dfs(int start) {
        int numVertices = map.size(); // Get the number of vertices in the graph
        ArrayList<Integer> visitedList = new ArrayList<>(); // List to store visited vertices in order
        boolean[] visited = new boolean[numVertices]; // To track visited vertices

        dfsRecursive(start, visited, visitedList); // Call the recursive helper function

        // Convert ArrayList to int[]
        int[] visitedArray = new int[visitedList.size()];
        for (int i = 0; i < visitedList.size(); i++) {
            visitedArray[i] = visitedList.get(i);
        }
        return visitedArray; // Return the array of visited vertices in DFS order
}

private void dfsRecursive(int vertex, boolean[] visited, ArrayList<Integer> visitedList) {
    visited[vertex] = true; // Mark the current vertex as visited
    visitedList.add(vertex); // Add the current vertex to the visited list

    // Iterate through neighbors of the current vertex
    for (int neighbor : map.getOrDefault(vertex, new ArrayList<>())) {
        if (!visited[neighbor]) { // If the neighbor hasn't been visited
            dfsRecursive(neighbor, visited, visitedList); // Recursively call dfs for the neighbor
            }
        }
    }


    public boolean hasCycle() {
        int numVertices = map.size(); // Get the number of vertices in the graph
        boolean[] visited = new boolean[numVertices]; // To track visited vertices
        boolean[] recursionStack = new boolean[numVertices]; // To track vertices in the current recursion stack
    
        // Iterate through all vertices
        for (int vertex = 0; vertex < numVertices; vertex++) {
            if (!visited[vertex] && isCyclicUtil(vertex, visited, recursionStack)) {
                return true; // Cycle detected
            }
        }
    
        return false; // No cycle found
    }
    
    private boolean isCyclicUtil(int vertex, boolean[] visited, boolean[] recursionStack) {
        visited[vertex] = true; // Mark the current vertex as visited
        recursionStack[vertex] = true; // Add the current vertex to the recursion stack
    
        // Iterate through neighbors of the current vertex
        for (int neighbor : map.getOrDefault(vertex, new ArrayList<>())) {
            if (!visited[neighbor] && isCyclicUtil(neighbor, visited, recursionStack)) {
                return true; // Cycle detected in neighbor's subtree
            } else if (recursionStack[neighbor]) {
                return true; // Cycle detected (neighbor is already in recursion stack)
            }
        }
    
        recursionStack[vertex] = false; // Remove the current vertex from the recursion stack (backtracking)
        return false; // No cycle found from this vertex
    }

    public String shortestPath(int start, int end) {
        int numVertices = map.size(); // Get the number of vertices in the graph
        int[] distances = new int[numVertices]; // To store distances from the start vertex
        int[] predecessors = new int[numVertices]; // To store predecessors in the shortest path
        Queue<Integer> queue = new LinkedList<>();
    
        Arrays.fill(distances, -1); // Initialize distances to -1 (unvisited)
        Arrays.fill(predecessors, -1); // Initialize predecessors to -1 (no predecessor)
    
        distances[start] = 0; // Distance from start to itself is 0
        queue.offer(start); // Add the start vertex to the queue
    
        while (!queue.isEmpty()) {
            int currentVertex = queue.poll(); // Get the next vertex from the queue
    
            // If we've reached the end vertex, break the loop
            if (currentVertex == end) {
                break;
            }
    
            // Iterate through neighbors of the current vertex
            for (int neighbor : map.getOrDefault(currentVertex, new ArrayList<>())) {
                if (distances[neighbor] == -1) { // If the neighbor hasn't been visited
                    distances[neighbor] = distances[currentVertex] + 1; // Update distance
                    predecessors[neighbor] = currentVertex; // Set the predecessor
                    queue.offer(neighbor); // Add the neighbor to the queue
                }
            }
        }
    
        // If no path was found
        if (distances[end] == -1) {
            return null;
        }
    
        // Build the path string by backtracking from destination to source
        StringBuilder path = new StringBuilder();
        int current = end;
        while (current != -1) {
            path.insert(0, current + " "); // Add vertex to the path
            current = predecessors[current]; // Move to the predecessor
        }
    
        // Remove the extra space at the beginning
        if (path.length() > 0 && path.charAt(0) == ' ') {
            path.deleteCharAt(0);
        }
    
        return path.toString(); // Return the shortest path string
    }
}
