package io.vodzinskiy;

import java.util.Arrays;

public class Data {
    public final static int N = 4;
    public final static int P = 2;
    public final static int H = N/P;

    public int a;
    public int b;

    public int[] A = new int[N];
    public int[] C = new int[N];
    public int[] D = new int[N];
    public int[] Z = new int[N];

    public int[][] MX = new int[N][N];
    public int[][] MR = new int[N][N];

    //заповнення вектора одиницями
    public void inputVector(int[] X) {
        Arrays.fill(X, 1);
    }

    //заповнення матриці одиницями
    public void inputMatrix(int[][] MX) {
        for (int[] row : MX)
            Arrays.fill(row, 1);
    }

    //виведення результату
    public void printResult() {
        System.out.println(Arrays.toString(A));
    }

    //мінімальний елемент часткового вектора
    public int minSubVectorElement(int[] X, int id) {
        int start = (id - 1) * H;
        int end = id * H;
        int min = Integer.MAX_VALUE;
        for (int i = start; i < end; i++) {
            if (X[i] < min) {
                min = X[i];
            }
        }
        return min;
    }

    //максимальний елемент часткового вектора
    public int maxSubVectorElement(int[] X, int id) {
        int start = (id - 1) * H;
        int end = id * H;
        int min = Integer.MIN_VALUE;
        for (int i = start; i < end; i++) {
            if (X[i] > min) {
                min = X[i];
            }
        }
        return min;
    }

    // обрахунок A_H = a * Z_H + D * (MX * MR_H) * b
    public void calculationA(int id) {
        int start = (id - 1) * H;
        int end = id * H;

        for (int i = start; i < end; i++) {
            int sum = 0;
            for (int j = 0; j < N; j++) {
                int mr_times_mx = 0;
                for (int k = start; k < end; k++) {
                    mr_times_mx += MR[i][k] * MX[k][j];
                }
                sum += D[j] * mr_times_mx;
            }
            A[i] = a * Z[i] + sum * b;
        }

    }
}
