import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.lang.System.in;

public class Main {
    static int[] heights;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        int n = st(br.readLine());

        heights = Stream.of(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();


        Stack<Tower> towerStack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int h = heights[i];
            while(!towerStack.isEmpty() && towerStack.peek().height < h) {
                towerStack.pop();
            }

            if (towerStack.isEmpty()) {
                sb.append("0 ");
            } else {
                sb.append(towerStack.peek().index + " ");
            }
            towerStack.add(new Tower(h, i + 1));
        }

        System.out.println(sb.toString());
    }

    public static int st(String str) {
        return parseInt(str);
    }

    static class Tower {
        int height;
        int index;

        public Tower(int height, int index) {
            this.height = height;
            this.index = index;
        }
    }
}
