import java.util.*;

public class Main {
    private Map<String, List<String>> adj = new TreeMap<>();
    private Map<String, Integer> entry = new LinkedHashMap<>();
    private Map<String, Integer> exit = new LinkedHashMap<>();
    private Set<String> visited = new HashSet<>();
    private int time = 1;

    public void addEdge(String u, String v) {
        adj.putIfAbsent(u, new ArrayList<>());
        adj.putIfAbsent(v, new ArrayList<>());
        adj.get(u).add(v);
    }

    public void runDFS(String start) {
        for (List<String> neighbors : adj.values()) {
            Collections.sort(neighbors);
        }
        dfs(start);
    }

    private void dfs(String node) {
        visited.add(node);
        entry.put(node, time++);
        
        List<String> neighbors = adj.get(node);
        if (neighbors != null) {
            for (String next : neighbors) {
                if (!visited.contains(next)) {
                    dfs(next);
                }
            }
        }
        exit.put(node, time++);
    }

    public void printResults() {
        System.out.println("Visited Nodes (Entry Order):");
        System.out.println(entry.keySet());
        System.out.println("\nEntry Times:");
        System.out.println(entry);
        System.out.println("\nFinish Times (Post-Order):");
        System.out.println(exit);
        System.out.println("\nDownstream Services from API:");
        System.out.println(entry.keySet());
    }

    public static void main(String[] args) {
        Main graph = new Main();
        
        graph.addEdge("api", "auth");
        graph.addEdge("api", "catalog");
        graph.addEdge("api", "cart");
        graph.addEdge("cart", "inventory");
        graph.addEdge("cart", "payment");
        graph.addEdge("payment", "notif");
        graph.addEdge("payment", "analytics");
        graph.addEdge("ship", "inventory");
        graph.addEdge("ship", "notif");
        graph.addEdge("catalog", "inventory");
        graph.addEdge("auth", "analytics");
        graph.addEdge("payment", "ship");

        graph.runDFS("api");
        graph.printResults();
    }
}
