package pa8;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;



public class GraphListWeighted implements Graph {
    Map<Integer, ArrayList<Integer>> map;
    int[][] weights; // 2D array to store weights

    public GraphListWeighted(int numVertices) {
        this.map = new HashMap<>();
        this.weights = new int[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            Arrays.fill(weights[i], Integer.MAX_VALUE);
        }
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
    public void addWeightedEdge(int v, int w, int weight) {
        if (!map.containsKey(v)) {
            map.put(v, new ArrayList<Integer>());
        }
        map.get(v).add(w);
        weights[v][w] = weight;
    }


    public int[] bfs(int startVertex) {
        int numVertices = weights.length; 
        int[] distances = new int[numVertices];
        Arrays.fill(distances, -1); // Initialize distances to -1 (unvisited)
        distances[startVertex] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startVertex);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            ArrayList<Integer> neighbors = map.getOrDefault(currentVertex, new ArrayList<>());
            for (int neighbor : neighbors) {
                if (distances[neighbor] == -1) { // If neighbor is unvisited
                    distances[neighbor] = distances[currentVertex] + 1;
                    queue.offer(neighbor);
                }
            }
        }

        return distances;
    }

    public int[] dfs(int startVertex) {
        int numVertices = weights.length;
        int[] distances = new int[numVertices];
        Arrays.fill(distances, -1); // Initialize distances to -1 (unvisited)
        distances[startVertex] = 0;

        dfsRecursive(startVertex, distances); // Call the recursive helper function

        return distances;
    }

    private void dfsRecursive(int currentVertex, int[] distances) {
        ArrayList<Integer> neighbors = map.getOrDefault(currentVertex, new ArrayList<>());
        for (int neighbor : neighbors) {
            if (distances[neighbor] == -1) { // If neighbor is unvisited
                distances[neighbor] = distances[currentVertex] + 1;
                dfsRecursive(neighbor, distances); // Recursively visit neighbor
            }
        }
    }

    public boolean hasCycle() {
        int numVertices = weights.length;
        boolean[] visited = new boolean[numVertices];
        boolean[] inRecursion = new boolean[numVertices]; // Track vertices in current path

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                if (hasCycleUtil(i, visited, inRecursion)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasCycleUtil(int v, boolean[] visited, boolean[] inRecursion) {
        visited[v] = true;
        inRecursion[v] = true; // Mark as currently in the recursion path

        ArrayList<Integer> neighbors = map.getOrDefault(v, new ArrayList<>());
        for (int neighbor : neighbors) {
            if (!visited[neighbor]) { // If neighbor is unvisited
                if (hasCycleUtil(neighbor, visited, inRecursion)) {
                    return true;
                }
            } else if (inRecursion[neighbor]) { // If neighbor is in current path
                return true; // Cycle detected
            }
        }

        inRecursion[v] = false; // Remove from current path
        return false;
    }


    public String shortestPath(int x, int y){
        String S = new String("s");
        return S;
    }

    public int[] dijkstra(int startVertex) {
        int numVertices = weights.length;
        int[] distances = new int[numVertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startVertex] = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>((v1, v2) -> Integer.compare(distances[v1], distances[v2]));
        pq.offer(startVertex);

        while (!pq.isEmpty()) {
            int currentVertex = pq.poll();

            if (distances[currentVertex] == Integer.MAX_VALUE) {
                break; // All reachable vertices have been visited
            }

            ArrayList<Integer> neighbors = map.getOrDefault(currentVertex, new ArrayList<>());
            for (int neighbor : neighbors) {
                int weight = weights[currentVertex][neighbor];
                if (weight != Integer.MAX_VALUE) { // If there is an edge
                    int newDist = distances[currentVertex] + weight;
                    if (newDist < distances[neighbor]) {
                        distances[neighbor] = newDist;
                        pq.offer(neighbor); // Re-add neighbor with updated distance
                    }
                }
            }
        }

        return distances;
    }
}