/*
문제
연인 코니와 브라운은 광활한 들판에서 ‘나 잡아 봐라’ 게임을 한다. 이 게임은 브라운이 코니를 잡거나, 코니가 너무 멀리 달아나면 끝난다. 게임이 끝나는데 걸리는 최소 시간을 구하시오.

조건
코니는 처음 위치 C에서 1초 후 1만큼 움직이고, 이후에는 가속이 붙어 매 초마다 이전 이동 거리 + 1만큼 움직인다. 즉 시간에 따른 코니의 위치는 C, C + 1, C + 3, C + 6, …이다.
브라운은 현재 위치 B에서 다음 순간 B – 1, B + 1, 2 * B 중 하나로 움직일 수 있다.
코니와 브라운의 위치 p는 조건 0 <= x <= 200,000을 만족한다.
브라운은 범위를 벗어나는 위치로는 이동할 수 없고, 코니가 범위를 벗어나면 게임이 끝난다.
입력 형식
표준 입력의 첫 줄에 코니의 위치 C와 브라운의 위치 B를 공백으로 구분하여 순서대로 읽는다.

출력 형식
브라운이 코니를 잡을 수 있는 최소시간 N초를 표준 출력한다. 단 브라운이 코니를 잡지 못한 경우에는 -1을 출력한다.

예제
입력: 11 2

출력: 5

코니의 위치: 11 → 12 → 14 → 17 → 21 → 26

브라운의 위치: 2 → 3 → 6 → 12 → 13 → 26

브라운은 코니를 5초 만에 잡을 수 있다.
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    static boolean[][] visited = new boolean[200_001][2];
    static final int even = 0, odd = 1;
    static Queue<Integer> q = new LinkedList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int conyPosition = sc.nextInt();
        int brown = sc.nextInt();

        q.add(brown);
        visited[brown][even] = true;
        visited[brown][odd] = true;

        int time = 0;
        while(inRange(conyPosition)) {
            int timeRemainder = time%2;

            if(visited[conyPosition][timeRemainder]) {//끝
                System.out.println(time);
                return;
            }

            time++;
            conyPosition += time;
            updateNextBrown(q.size(), time);
        }

        System.out.println(-1);
    }

    public static void updateNextBrown(int size, int time) {
        for(int i = 0; i < size; i++) {
            int index = q.poll();

            int[] nextIndex = {index-1, index+1, index*2};
            int timeRemainder = time%2;

            for(int j = 0; j < 3; j++) {
                int now = nextIndex[j];
                if(inRange(now) && !visited[now][timeRemainder]) {
                    visited[now][timeRemainder] = true;
                    q.add(now);
                }
            }
        }
    }

    public static boolean inRange(int num) {
        if(0 <= num && num <= 200_000) {
            return true;
        } else {
            return false;
        }
    }
}
