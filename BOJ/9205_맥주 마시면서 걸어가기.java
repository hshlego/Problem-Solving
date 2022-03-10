import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.*;
import static java.lang.System.in;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(in));


    public static void main(String[] args) throws IOException {
        int T = st(br.readLine());
        for (int t = 0; t < T; t++) {
            int n = st(br.readLine());
            ArrayList<Node> nodes = new ArrayList<>();

            for (int i = 0; i < n+2; i++) {
                String[] line = br.readLine().split(" ");
                nodes.add(new Node(i, st(line[0]), st(line[1])));
            }

            ArrayList[] adj = new ArrayList[n + 2];
            for (int i = 0; i < n + 2; i++) {
                adj[i] = new ArrayList<Integer>();
            }

            for (int i = 0; i < n + 2; i++) {
                for (int j = i + 1; j < n + 2; j++) {
                    if(calcDist(nodes.get(i), nodes.get(j)) <= 1000) {
                        adj[i].add(j);
                        adj[j].add(i);
                    }
                }
            }

            boolean[] visited = new boolean[n + 2];
            Queue<Node> q = new LinkedList<>();
            q.add(nodes.get(0));
            visited[0] = true;

            boolean arrived = false;
            while (!q.isEmpty()) {
                Node now = q.poll();
                if(now == nodes.get(n+1)) {
                    arrived = true;
                    break;
                }

                for(int e : (ArrayList<Integer>) adj[now.num]) {
                    if(!visited[e]) {
                        visited[e] = true;
                        q.add(nodes.get(e));
                    }
                }
            }

            System.out.println(arrived? "happy" : "sad");
        }
    }

    public static int calcDist(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public static int st(String str) {
        return parseInt(str);
    }

    static class Node {
        int num, x, y;

        public Node(int num, int x, int y) {
            this.num = num;
            this.x = x;
            this.y = y;
        }
    }
}
