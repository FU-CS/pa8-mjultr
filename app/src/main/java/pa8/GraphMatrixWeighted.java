package pa8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class GraphMatrixWeighted implements Graph {
    private int [][] matrix;
    public GraphMatrixWeighted(int n_nodes){
        this.matrix = new int[n_nodes][n_nodes];
    }

    public void addEdge(int source, int dest){
        return;
    }


    
    public void addWeightedEdge(int source, int dest, int weight) {
        this.matrix[source][dest] = weight;
    }


    public int[] bfs(int start) {
    int numVertices = this.matrix.length;
    int[] visitedOrder = new int[numVertices]; // Array to store visited vertices in order
    int visitedCount = 0; // Counter for visited vertices
    boolean[] visited = new boolean[numVertices]; // To track visited vertices
    Queue<Integer> queue = new LinkedList<>();

    visited[start] = true;
    queue.offer(start);
    visitedOrder[visitedCount++] = start; // Add start vertex to visitedOrder

    while (!queue.isEmpty()) {
        int currentVertex = queue.poll();

        int[] neighbors = neighbors(currentVertex); // Get neighbors of currentVertex
        for (int neighbor : neighbors) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.offer(neighbor);
                visitedOrder[visitedCount++] = neighbor; // Add neighbor to visitedOrder
            }
        }
    }

    // Create a new array with only the visited vertices
    int[] result = new int[visitedCount];
    System.arraycopy(visitedOrder, 0, result, 0, visitedCount);

    return result;
}


    public int[] dfs(int start) {
        int numVertices = this.matrix.length;
        ArrayList<Integer> visitedList = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
    
        dfsRecursive(start, visited, visitedList);
    
        // Convert ArrayList to int[]
        int[] visitedArray = new int[visitedList.size()];
        for (int i = 0; i < visitedList.size(); i++) {
            visitedArray[i] = visitedList.get(i);
        }
        return visitedArray;
    }
    
    private void dfsRecursive(int vertex, boolean[] visited, ArrayList<Integer> visitedList) {
        visited[vertex] = true;
        visitedList.add(vertex);
    
        int[] neighbors = neighbors(vertex); // Get neighbors of vertex
        for (int neighbor : neighbors) {
            if (!visited[neighbor]) {
                dfsRecursive(neighbor, visited, visitedList);
            }
        }
    }

    public int[] neighbors(int vertex) {
        List<Integer> neighborsList = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[vertex][i] != 0 && matrix[vertex][i] != Integer.MAX_VALUE) {
                neighborsList.add(i);
            }
        }
        int[] neighborsArray = new int[neighborsList.size()];
        for (int i = 0; i < neighborsList.size(); i++) {
            neighborsArray[i] = neighborsList.get(i);
        }
        return neighborsArray;
    }

    public boolean hasCycle() {
        int numVertices = this.matrix.length;
        boolean[] visited = new boolean[numVertices];
        boolean[] recursionStack = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && isCyclicUtil(i, visited, recursionStack)) {
                return true;
            }
        }
        return false;
    }
    private boolean isCyclicUtil(int v, boolean[] visited, boolean[] recursionStack) {
        visited[v] = true;
        recursionStack[v] = true;
    
        int[] neighbors = neighbors(v); // Get neighbors of v
    
        for (int neighbor : neighbors) {
            if (!visited[neighbor] && isCyclicUtil(neighbor, visited, recursionStack)) {
                return true; // Cycle detected in neighbor's subtree
            } else if (recursionStack[neighbor]) {
                return true; // Cycle detected (neighbor is already in recursion stack)
            }
        }
    
        recursionStack[v] = false; // Backtrack: Remove v from recursion stack
        return false; // No cycle found from this vertex
    }

    public String shortestPath(int start, int end) {
    int numVertices = this.matrix.length;
    int[] distances = new int[numVertices];
    int[] predecessors = new int[numVertices];
    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(v -> distances[v]));

    Arrays.fill(distances, Integer.MAX_VALUE);
    Arrays.fill(predecessors, -1);

    distances[start] = 0;
    priorityQueue.offer(start);

    while (!priorityQueue.isEmpty()) {
        int currentVertex = priorityQueue.poll();

        if (currentVertex == end) {
            break; // Found the destination
        }

        int[] neighbors = neighbors(currentVertex); // Get neighbors of currentVertex
        for (int neighbor : neighbors) {
            int weight = matrix[currentVertex][neighbor]; // Get edge weight
            int newDistance = distances[currentVertex] + weight;

            if (newDistance < distances[neighbor]) {
                distances[neighbor] = newDistance;
                predecessors[neighbor] = currentVertex;
                priorityQueue.offer(neighbor); // Update priority queue
            }
        }
    }

    // If no path was found
    if (distances[end] == Integer.MAX_VALUE) {
        return null;
    }

    // Build the path string by backtracking from destination to source
    StringBuilder path = new StringBuilder();
    int current = end;
    while (current != -1) {
        path.insert(0, current + " ");
        current = predecessors[current];
    }

    // Remove the extra space at the beginning
    if (path.length() > 0 && path.charAt(0) == ' ') {
        path.deleteCharAt(0);
    }

    return path.toString();
}

}
