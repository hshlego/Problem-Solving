import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Integer.parseInt;
import static java.lang.System.in;

public class Main {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int n, m;
    static char[][] board;
    static boolean[][] visited;
    static Queue<Node> waterQueue, swanQueue;
    static boolean swanFound;
    static int destX, destY;

    public static void main(String[] args) throws IOException {
        init();

        int days = 0;
        while(swanFound == false) {
            days++;

            meltIce();
            swanTraverse();
        }

        System.out.println(days);
    }

    static void meltIce() {
        int rep = waterQueue.size();

        while (rep-- > 0) {
            Node now = waterQueue.poll();

            for (int dir = 0; dir < 4; dir++) {
                int nx = now.x + dx[dir];
                int ny = now.y + dy[dir];

                if(valid(nx, ny) && board[nx][ny] == 'X') {
                    board[nx][ny] = '.';
                    waterQueue.add(new Node(nx, ny));
                }
            }
        }
    }

    static void swanTraverse() {
        Queue<Node> blockedQueue = new LinkedList<>();

        while(!swanQueue.isEmpty()) {
            Node now = swanQueue.poll();

            for (int dir = 0; dir < 4; dir++) {
                int nx = now.x + dx[dir];
                int ny = now.y + dy[dir];

                if(valid(nx, ny) && !visited[nx][ny]) {
                    visited[nx][ny] = true;

                    if(board[nx][ny] == 'X') {
                        blockedQueue.add(new Node(nx, ny));
                    } else {
                        if(isDestination(nx, ny)) {
                            swanFound = true;
                            return;
                        }
                        swanQueue.add(new Node(nx, ny));
                    }
                }
            }
        }
        swanQueue = blockedQueue;
    }

    static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String[] line = br.readLine().split(" ");

        n = st(line[0]);
        m = st(line[1]);

        board = new char[n][m];
        visited = new boolean[n][m];
        swanQueue = new LinkedList<>();
        waterQueue = new LinkedList<>();

        ArrayList<Node> swanPositions = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < m; j++) {
                board[i][j] = str.charAt(j);
                if(board[i][j] == 'L') {
                    swanPositions.add(new Node(i, j));
                }
                if(board[i][j] == 'L' || board[i][j] =='.') {
                    waterQueue.add(new Node(i, j));
                }
            }
        }

        int swanX = swanPositions.get(0).x;
        int swanY = swanPositions.get(0).y;
        destX = swanPositions.get(1).x;
        destY = swanPositions.get(1).y;

        visited = new boolean[n][m];
        swanQueue.add(new Node(swanX, swanY));
        visited[swanX][swanY] = true;
        swanTraverse();

    }
    
    static boolean isDestination(int x, int y) {
        if (x == destX && y == destY) {
            return true;
        } else {
            return false;
        }
    }

    static boolean valid(int x, int y) {
        if (x < 0 || x >= n || y < 0 || y >= m) {
            return false;
        } else {
            return true;
        }
    }

    static int st(String str) {
        return parseInt(str);
    }

    static class Node{
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
