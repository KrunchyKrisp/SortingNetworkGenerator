package com.company;

import java.util.Arrays;

public class SortingNetworkGenerator {

    public static void executePair(int[] data, int left, int right) {
        if (data[left] > data[right]) {
            int temp = data[left];
            data[left] = data[right];
            data[right] = temp;
        }
    }

    public static void executeNetwork(int[][] network, int[] data) {
        for (int[] pair : network) {
            executePair(data, pair[0], pair[1]);
        }
    }

    public static void increase(int[][] network, int pos, int n) {
        ++network[pos][1];
        if (network[pos][1] > n - 1) {
            ++network[pos][0];
            network[pos][1] = network[pos][0] + 1;
            if (network[pos][0] > n - 1 || network[pos][1] > n - 1) {
                network[pos][0] = 0;
                network[pos][1] = 1;
                if (pos > 0) {
                    increase(network, pos - 1, n);
                }
            }
        }
        if (pos > 0 && Arrays.equals(network[pos], network[pos - 1])) {
            increase(network, pos, n);
        }
    }

    public static int[][] networkGenerator(int n, int depth, boolean pairWiseStart) {
        int[][] network, end;
        long counter = 0, mCounter = 0, start = System.currentTimeMillis();
        for (; ; ++depth) {
            System.out.println("Testing Depth: " + depth);
            network = new int[depth][2];
            end = new int[depth][2];
            int k = 0, i = 0;
            for (; i < depth; ++i) {
                k = (k % 2) + 1;
                end[i][0] = 0;
                end[i][1] = k;
            }
            k = 0;
            i = 0;
            if (pairWiseStart) {
                for(int j = 1; i < depth && j < n; j *= 2) {
                    for (; i < depth && k < n - j; ++i, k += 2*j) {
                        network[i][0] = k;
                        network[i][1] = k + j;
                    }
                    k = 0;
                }
            }
            for (; i < depth; ++i) {
                k = (k % 2) + 1;
                network[i][0] = 0;
                network[i][1] = k;
            }
            System.out.println("Initial network at depth " + depth + ": " + Arrays.deepToString(network));
            do {
                if (counter == 100_000_000) {
                    ++mCounter;
                    System.out.println("Testing " + mCounter + "00Mth: " + Arrays.deepToString(network));
                    printTime(start, System.currentTimeMillis());
                    counter = 0;
                }
                ++counter;
                if (iterativeBitCombinations(n, network)) {
                    System.out.println("Network Found For n = " + n + " : " + Arrays.deepToString(network));
                    System.out.println("At Depth " + depth);
                    System.out.println("At Iteration " + mCounter + "00M + " + counter);
                    printTime(start, System.currentTimeMillis());
                    return network;
                }
                increase(network, depth - 1, n);
            } while (!Arrays.deepEquals(network, end));
        }
    }

    public static boolean iterativeBitCombinations(int n, int[][] network) {
        int[] data = new int[n];
        int[] end = new int[n];

        do {
            int[] temp = Arrays.copyOf(data, n);
            executeNetwork(network, temp);
            for (int i = 0; i < n-1; ++i) {
                if (temp[i] > temp[i+1]) {
                    return false;
                }
            }
            for (int i = n-1; i >= 0; --i) {
                if (data[i] == 0) {
                    data[i] = 1;
                    break;
                }
                data[i] = 0;
            }
        } while (!Arrays.equals(data, end));
        return true;
    }

    public static void main(String[] args) {
        int[][] network = networkGenerator(6,12, false);
    }

    public static void printTime(long start, long end) {
        long difference = end - start;
        long hours = Math.floorDiv(difference, 3600000);
        long minutes = Math.floorDiv(difference - hours * 3600000, 60000);
        double seconds = (difference - hours * 3600000 - minutes * 60000) / 1000d;
        System.out.println("Time: " + hours + "h " + minutes + "m " + seconds + "s");
    }
}
