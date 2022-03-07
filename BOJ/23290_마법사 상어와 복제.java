import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.lang.System.in;

class Main {
    static int m, s;
    static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] sharkDx = {-1, 0, 1, 0};
    static int[] sharkDy = {0, -1, 0, 1};
    static Shark shark;
    static Room[][] rooms;

    public static void main(String args[]) throws IOException {
        init();

        while (s-- > 0) {
            copyMagic(); //step 1

            moveFishes(); //step 2

            moveShark(); //step 3

            reduceScent(); //step 4

            completeCopyMagic(); //step 5
        }

        System.out.println(countAllFish());
    }


    public static void copyMagic() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rooms[i][j].copy();
            }
        }
    }


    public static void moveFishes() {
        ArrayList<Fish> save = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for(Fish fish : rooms[i][j].fishes) {
                    fish.move();
                    save.add(fish);
                }
                rooms[i][j].fishes = new ArrayList<>();
            }
        }
        for (Fish fish : save) {
            int x = fish.x;
            int y = fish.y;
            rooms[x][y].fishes.add(fish);
        }

    }


    public static void moveShark() {
        int[] howToMove = shark.findNextMove();

        int nx = shark.x;
        int ny = shark.y;
        for (int index = 0; index < 3; index++) {
            nx = getNx(nx, howToMove[index]);
            ny = getNy(ny, howToMove[index]);

            if(rooms[nx][ny].fishes.size() > 0) {
                rooms[nx][ny].scent = 3;
                rooms[nx][ny].fishes = new ArrayList<>();
            }
        }

        shark.x = nx;
        shark.y = ny;
    }


    public static int getNx(int nx, int dir) {
        return nx + sharkDx[dir];
    }


    public static int getNy(int ny, int dir) {
        return ny + sharkDy[dir];
    }


    public static void reduceScent() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rooms[i][j].reduceScent();
            }
        }
    }


    public static void completeCopyMagic() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rooms[i][j].completeCopy();
            }
        }
    }


    public static int countAllFish() {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                count += rooms[i][j].fishes.size();
            }
        }
        return count;
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String[] line = br.readLine().split(" ");

        m = st(line[0]);
        s = st(line[1]);
        rooms = new Room[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rooms[i][j] = new Room();
            }
        }


        for (int i = 0; i < m; i++) {
            line = br.readLine().split(" ");
            int x = st(line[0]) - 1;
            int y = st(line[1]) - 1;
            int dir = st(line[2]) - 1;
            Fish fish = new Fish(x, y, dir);
            rooms[x][y].fishes.add(fish);
        }


        line = br.readLine().split(" ");
        shark = new Shark(st(line[0])-1, st(line[1])-1);

    }


    public static boolean valid(int x, int y) {
        if(x < 0 || x >= 4 || y < 0 || y >= 4) return false;
        else return true;
    }


    public static int st(String str) {
        return Integer.parseInt(str);
    }


    static class Room {
        ArrayList<Fish> fishes = new ArrayList<>();
        ArrayList<Fish> copies = new ArrayList<>();
        int scent = 0;

        public void copy() {
            for (Fish fish : fishes) {
                copies.add(new Fish(fish));
            }
        }

        public void reduceScent() {
            if(this.scent > 0) scent--;
        }

        public void completeCopy() {
            for (Fish fish : copies) {
                fishes.add(fish);
            }
            copies = new ArrayList<>();
        }

    }


    static class Shark {
        ArrayList<int[]> orders = new ArrayList<>();
        int x, y;

        public Shark(int x, int y) {
            this.x = x;
            this.y = y;
            makeOrder();
        }

        public void makeOrder() {
            for (int a = 0; a < 4; a++) {
                for (int b = 0; b < 4; b++) {
                    for (int c = 0; c < 4; c++) {
                        orders.add(new int[]{a, b, c});
                    }
                }
            }
        }

        public int[] findNextMove() {
            int maxEat = -1;
            int[] maxOrder = new int[3];

            int nx = x, ny = y;
            for (int[] order : orders) {
                int eat = 0;
                boolean flag = true;
                boolean[][] visited = new boolean[4][4];
                nx = x; ny = y;

                for (int index = 0; index < 3; index++) {
                    nx += sharkDx[order[index]];
                    ny += sharkDy[order[index]];

                    if(valid(nx, ny)) {
                        if(!visited[nx][ny])
                            eat += rooms[nx][ny].fishes.size();

                        visited[nx][ny] = true;
                    } else {
                        flag = false;
                        break;
                    }
                }

                if (flag == true) {
                    if(maxEat < eat) {
                        maxEat = eat;
                        maxOrder = order;
                    }
                }
            }

            return maxOrder;
        }
    }

    static class Fish {
        int x, y, dir;

        public Fish(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }

        public Fish(Fish o) {
            this.x = o.x;
            this.y = o.y;
            this.dir = o.dir;
        }

        public void move() {
            for (int k = 0; k < 8; k++) {
                int nd = (dir+8-k)%8;
                int nx = x + dx[nd];
                int ny = y + dy[nd];

                if(canGo(nx, ny)) {
                    this.x = nx;
                    this.y = ny;
                    this.dir = nd;
                    return;
                }
            }
        }

        public boolean canGo(int x, int y) {
            if(valid(x, y) && noShark(x, y) && noScent(x, y)) return true;
            else return false;
        }

        public boolean noShark(int x, int y) {
            if(x == shark.x && y == shark.y) return false;
            else return true;
        }

        public boolean noScent(int x, int y) {
            if(rooms[x][y].scent == 0) return true;
            else return false;
        }

    }
}
