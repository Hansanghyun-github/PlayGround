package org.example.dijkstra;

import java.util.*;

public class BfsWithPath {
    public static final int[] dr = {-1, 1, 0, 0};
    public static final int[] dc = {0, 0, -1, 1};

    int[][] board = {
            {0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0}
    };

    public static void main(String[] args) {
        new BfsWithPath().solution();
    }

    private void solution() {
        int n = 5;
        int m = 5;
        int[][] distances = new int[n][m];
        Pair[][] previous = new Pair[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                distances[i][j] = Integer.MAX_VALUE;
                previous[i][j] = new Pair(-1, -1);
            }
        }

        int sr = 0;
        int sc = 0;
        int er = 4;
        int ec = 4;
        bfs(sr, sc, distances, previous);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("%2d ",((distances[i][j]==Integer.MAX_VALUE)?-1:distances[i][j]));
            }
            System.out.println();
        }

        List<Pair> path = getPath(previous, sr, sc, er, ec);
        for (Pair pair : path) {
            System.out.print(pair.r + "," + pair.c + " -> ");
        }
        System.out.println();
    }

    private List<Pair> getPath(Pair[][] previous, int sr, int sc, int er, int ec) {
        int cr = er, cc = ec;
        List<Pair> result = new ArrayList<>();
        while(true){
            if(cr == sr && cc == sc) break;
            result.add(new Pair(cr,cc));
            int tr = previous[cr][cc].r;
            int tc = previous[cr][cc].c;
            cr = tr; cc = tc;
        }
        result.add(new Pair(sr,sc));
        Collections.reverse(result);

        return result;
    }

    private void bfs(int sr, int sc, int[][] distances, Pair[][] previous) {
        Queue<Infor> q = new LinkedList<>();
        q.add(new Infor(new Pair(sr,sc), 0));
        boolean[][] visited = new boolean[5][5];
        distances[sr][sc] = 0;
        visited[sr][sc] = true;

        while(!q.isEmpty()){
            Infor cur = q.poll();
            for(int k=0;k<4;k++) {
                int nr = cur.p.r+dr[k], nc = cur.p.c+dc[k];
                if(nr < 0 || nr >= 5 || nc < 0 || nc >= 5) continue;
                if(board[nr][nc] == 1) continue;
                if(visited[nr][nc]) continue;

                visited[nr][nc]=true;
                q.add(new Infor(new Pair(nr,nc), cur.n + 1));
                distances[nr][nc] = cur.n + 1;
                previous[nr][nc] = new Pair(cur.p.r, cur.p.c);
            }
        }
    }

    class Infor{
        Pair p;
        int n;

        public Infor(Pair p, int n) {
            this.p = p;
            this.n = n;
        }
    }

    class Pair{
        int r;
        int c;
        public Pair(int r, int c){
            this.r = r;
            this.c = c;
        }
    }
}
