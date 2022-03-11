import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.*;
import static java.lang.System.in;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(in));
    static int n;
    static int[] A, B, C;

    public static void main(String[] args) throws IOException {
        n = st(br.readLine());

        String[] line;
        int a, b, c;
        A = new int[n];
        B = new int[n];
        C = new int[n];

        for (int i = 0; i < n; i++) {
            line = br.readLine().split(" ");

            a = st(line[0]);
            c = st(line[1]);
            b = st(line[2]);

            A[i] = a;
            B[i] = b;
            C[i] = c;
        }

        long left = 0L;
        long right = Integer.MAX_VALUE + 1L;
        long mid;

        while(left < right) {
            mid = (left+right)/2;

            int belowMid = countBelowMid(mid);

            if(belowMid % 2 == 0) {
                left = mid+1;
            } else {
                right = mid;
            }

        }

        System.out.println(left == Integer.MAX_VALUE+1L ? "NOTHING" : String.format("%d %d", left, count((int)left)));

    }

    public static int countBelowMid(long mid) {
        int belowMid = 0;
        for (int i = 0; i < n; i++) {
            int start = A[i];
            int end = (int)Math.min(mid, C[i]);

            if(start > end) {
                continue;
            }

            int count = (end-start)/B[i] + 1;
            belowMid += count;
        }

        return belowMid;
    }

    public static int count(int num) {
        int numCount = 0;
        for (int i = 0; i < n; i++) {
            if(C[i] < num || A[i] > num) {
                continue;
            }

            if((num-A[i]) % B[i] == 0) {
                numCount ++;
            }
        }

        return numCount;
    }

    public static int st(String str) {
        return parseInt(str);
    }

}
