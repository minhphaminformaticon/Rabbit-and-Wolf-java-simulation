package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) throws InterruptedException {


        //todo make simulation configurable
        int steps;
        int startingRabbits;
        int startingWolves;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Enter the amount of steps for the simulation (Limit: 100): ");
            steps = scanner.nextInt();
        } while (steps > 100 || steps <= 0);
        do {
            System.out.println("Enter the amount of steps for the starting rabbits (Limit: 10): ");
            startingRabbits = scanner.nextInt();
        } while (startingRabbits > 10 || startingRabbits <= 0);
        do {
            System.out.println("Enter the amount of steps for the starting wolves (Limit: 10): ");
            startingWolves = scanner.nextInt();
        } while (startingWolves > 10);
        // run simulation
        new Simulation(steps, startingRabbits, startingWolves);
    }
}
