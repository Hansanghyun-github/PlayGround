package org.example.dijkstra;

import lombok.ToString;

@ToString
class Node implements Comparable<Node> {
    int index;
    int distance;

    public Node(int index, int distance) {
        this.index = index;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other) {
        // 거리 먼저 비교, 거리가 같으면 노드 번호 비교
        if (this.distance == other.distance) {
            return Integer.compare(this.index, other.index);
        }
        return Integer.compare(this.distance, other.distance);
    }
}
