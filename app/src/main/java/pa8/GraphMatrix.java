package pa8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class GraphMatrix implements Graph {
    

    private int [][] matrix;
    public GraphMatrix(int n_nodes){
        this.matrix = new int[n_nodes][n_nodes];
    }


    public void addEdge(int source, int dest){
        this.matrix[source][dest]=1;
    }
    
    public void addWeightedEdge(int source, int dest, int weight){
        return;
    }

 
    public int[] bfs(int start) {
        int numVertices = this.matrix.length; 
        ArrayList<Integer> visitedList = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(start); 
        visitedList.add(start);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            System.out.print(currentVertex + " "); // Print the visited vertex

            // Explore neighbors of currentVertex
            int[] neighbors = this.matrix[currentVertex];
            for (int i = 0; i < numVertices; i++) {
                if (neighbors[i] != 0 && !visitedList.contains(i)) {
                    queue.offer(i);
                    visitedList.add(i);
                }
            }
        }

        // Convert ArrayList to int[]
        int[] visitedArray = new int[visitedList.size()];
        for (int i = 0; i < visitedList.size(); i++) {
            visitedArray[i] = visitedList.get(i);
        }
        return visitedArray;
    }



    public int[] dfs(int start){
        ArrayList<Integer> visitedList = new ArrayList<>();
    dfsRecursive(start, visitedList); 

        // Convert ArrayList to int[]
        int[] visitedArray = new int[visitedList.size()];
        for (int i = 0; i < visitedList.size(); i++) {
            visitedArray[i] = visitedList.get(i);
        }
        return visitedArray;
}
private void dfsRecursive(int vertex, ArrayList<Integer> visitedList) {
    visitedList.add(vertex);
    System.out.print(vertex + " "); // Print the visited vertex

    int[] neighbors = this.matrix[vertex];
    for (int i = 0; i < neighbors.length; i++) {
        if (neighbors[i] != 0 && !visitedList.contains(i)) {
            dfsRecursive(i, visitedList);
        }
    }
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

    int[] neighbors = this.matrix[v];
    for (int i = 0; i < neighbors.length; i++) {
      if (neighbors[i] != 0) {
        if (!visited[i] && isCyclicUtil(i, visited, recursionStack)) {
          return true;
        } else if (recursionStack[i]) {
          return true;
        }
      }
    }

    recursionStack[v] = false;
    return false;
  }




    
   
    public String shortestPath(int v, int w) {
    int numVertices = this.matrix.length;
    int[] distances = new int[numVertices];
    int[] predecessors = new int[numVertices];
    Queue<Integer> queue = new LinkedList<>();

    Arrays.fill(distances, -1); // Initialize distances to -1 (unvisited)
    Arrays.fill(predecessors, -1); // Initialize predecessors to -1 (no predecessor)

    distances[v] = 0;
    queue.offer(v);

    while (!queue.isEmpty()) {
        int currentVertex = queue.poll();

        if (currentVertex == w) {
            break; // Found the destination, stop searching
        }

        int[] neighbors = this.matrix[currentVertex];
        for (int i = 0; i < numVertices; i++) {
            if (neighbors[i] != 0 && distances[i] == -1) {
                distances[i] = distances[currentVertex] + 1;
                predecessors[i] = currentVertex;
                queue.offer(i);
            }
        }
    }

    // If no path was found
    if (distances[w] == -1) {
        return null;
    }

    // Build the path string by backtracking from destination to source
    StringBuilder path = new StringBuilder();
    int current = w;
    while (current != -1) {
        path.insert(0, current + " "); // Add space after each vertex
        current = predecessors[current];
    }

    // Remove the extra space at the beginning if it exists
    if (path.length() > 0 && path.charAt(0) == ' ') {
        path.deleteCharAt(0); 
    }

    return path.toString(); // Return without trimming
}



    
    public static void main(String[] args){
        //Testing Graph Matrix
        GraphMatrix g= new GraphMatrix(5);
        g.addEdge(0, 1);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(3, 4);
        g.addEdge(2, 4);
        g.printMatrix();
    }
}

