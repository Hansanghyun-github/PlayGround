package org.example.dijkstra;

import java.util.*;

public class DijkstraWithPath {
    public static final int INF = (int) 1e9;

    public static void dijkstra(List<List<Node>> graph, int start, int[] distances, int[] previous) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));
        distances[start] = 0;

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int currentIndex = current.index;
            int currentDistance = current.distance;

            if (distances[currentIndex] < currentDistance) continue;

            for (Node neighbor : graph.get(currentIndex)) {
                int newDistance = currentDistance + neighbor.distance;

                if (newDistance < distances[neighbor.index]) {
                    distances[neighbor.index] = newDistance;
                    previous[neighbor.index] = currentIndex;
                    pq.offer(new Node(neighbor.index, newDistance));
                }
            }
        }
    }

    public static List<Integer> getPath(int[] previous, int start, int end) {
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = previous[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        if (path.get(0) == start) {
            return path;
        } else {
            return Collections.emptyList();
        }
    }

    public static void main(String[] args) {
        int n = 6;
        int start = 1;

        List<List<Node>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        graph.get(1).add(new Node(2, 2));
        graph.get(1).add(new Node(3, 5));
        graph.get(2).add(new Node(3, 1));
        graph.get(2).add(new Node(4, 2));
        graph.get(3).add(new Node(4, 1));
        graph.get(4).add(new Node(5, 3));

        int[] distances = new int[n + 1];
        int[] previous = new int[n + 1];
        Arrays.fill(distances, INF);
        Arrays.fill(previous, -1);

        dijkstra(graph, start, distances, previous);

        for (int i = 1; i <= n; i++) {
            if (distances[i] == INF) {
                System.out.println("Node " + i + " is unreachable");
            } else {
                System.out.println("Shortest distance to node " + i + " is " + distances[i]);
                List<Integer> path = getPath(previous, start, i);
                System.out.println("Path: " + path);
            }
        }

        System.out.println();
        for(int i=1; i<=n; i++) {
            var l = graph.get(i);
            System.out.println(i+": "+l);
        }
        System.out.println();
        for(int i=1; i<=n; i++) {
            System.out.println(i+": "+distances[i]);
        }
        System.out.println();
        for(int i=1; i<=n; i++) {
            System.out.println(i+": "+previous[i]);
        }
    }
}