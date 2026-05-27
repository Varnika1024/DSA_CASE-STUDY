import java.util.*;

public class Main {
    static final int INF = 999999;

    static int[][] floydWarshall(int n, int[][] edges) {
        int[][] dp = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = (edges[i][j] != 0) ? edges[i][j] : INF;
                }
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dp[i][k] != INF && dp[k][j] != INF) {
                        if (dp[i][k] + dp[k][j] < dp[i][j]) {
                            dp[i][j] = dp[i][k] + dp[k][j];
                        }
                    }
                }
            }
        }
        return dp;
    }

    public static void main(String[] args) {
        int n = 6;
        int[][] edges = new int[n][n];

        edges[0][1] = 10; edges[1][0] = 10; // CHC - DDR
        edges[0][2] = 15; edges[2][0] = 15; // CHC - KRL
        edges[1][2] = 8;  edges[2][1] = 8;  // DDR - KRL
        edges[1][3] = 18; edges[3][1] = 18; // DDR - GTR
        edges[2][3] = 12; edges[3][2] = 12; // KRL - GTR
        edges[2][4] = 5;  edges[4][2] = 5;  // KRL - VKR
        edges[3][4] = 7;  edges[4][3] = 7;  // GTR - VKR
        edges[3][5] = 9;  edges[5][3] = 9;  // GTR - TNE
        edges[4][5] = 6;  edges[5][4] = 6;  // VKR - TNE

        int[][] result = floydWarshall(n, edges);

        String[] hubs = {"CHC", "DDR", "KRL", "GTR", "VKR", "TNE"};
        System.out.println("All-Pairs Shortest Travel Time Matrix (minutes):\n");
        System.out.print("     ");
        for (String h : hubs) System.out.printf("%-5s", h);
        System.out.println("\n----------------------------------");

        for (int i = 0; i < n; i++) {
            System.out.print(hubs[i] + "  | ");
            for (int j = 0; j < n; j++) {
                if (result[i][j] == INF) {
                    System.out.printf("%-5s", "INF");
                } else {
                    System.out.printf("%-5d", result[i][j]);
                }
            }
            System.out.println();
        }
    }
}
