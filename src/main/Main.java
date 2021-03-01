package main;

import network.SortingNetworkGenerator;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        int n, depth;
        boolean pairWise;
        while (true) {
            System.out.println("Generation: (P)airWise / (B)ruteForce: ");
            input = scanner.next();
            switch(input.toLowerCase()) {
                case "p":
                    System.out.println("N: ");
                    n = scanner.nextInt();
                    System.out.println(Arrays.toString(SortingNetworkGenerator.pairWiseNetworkGenerator(n)));
                    break;
                case "b":
                    System.out.println("N: ");
                    n = scanner.nextInt();
                    System.out.println("Starting Depth: ");
                    depth = scanner.nextInt();
                    System.out.println("PairWise start (true/false): ");
                    pairWise = scanner.nextBoolean();
                    SortingNetworkGenerator.networkGenerator(n, depth, pairWise);
                    break;
                default:
                    System.out.println("Incorrect Input");
            }
        }
    }
}
