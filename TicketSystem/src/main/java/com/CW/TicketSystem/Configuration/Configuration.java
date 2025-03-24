
package com.CW.TicketSystem.Configuration;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Scanner;

@Component
public class Configuration implements Serializable {
    private int maxTicketCapacity;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    // Collecting inputs with validation
    public void collectInputs() {
        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                // Collect Total Tickets
                System.out.print("Enter total number of tickets (1-50): ");
                totalTickets = validateInput(input.nextLine(), 1, 50);

                // Collect Ticket Release Rate
                System.out.print("Enter ticket release rate (1-5 ms): ");
                ticketReleaseRate = validateInput(input.nextLine(), 1, 5);

                // Collect Customer Retrieval Rate
                System.out.print("Enter customer retrieval rate (1-5 ms): ");
                customerRetrievalRate = validateInput(input.nextLine(), 1, 5);

                // Collect Max Ticket Capacity
                System.out.print("Enter maximum ticket capacity (1-40): ");
                maxTicketCapacity = validateInput(input.nextLine(), 1, 40);

                // Validate Total Tickets Against Max Ticket Capacity
                if (totalTickets >= maxTicketCapacity) {
                    System.out.println("Total Tickets must be less than Max Ticket Capacity. Please try again.");
                    continue;
                }

                break; // Exit loop if all inputs are valid
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Helper method to validate inputs
    private int validateInput(String input, int min, int max) {
        try {
            int value = Integer.parseInt(input);
            if (value < min || value > max) {
                throw new IllegalArgumentException("Invalid input. Please enter a number between " + min + " and " + max + ".");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a valid number.");
        }
    }

    // Method to validate the final configuration values
    public void validate() {
        if (totalTickets <= 0 || maxTicketCapacity <= 0) {
            throw new IllegalArgumentException("Configuration values must be positive integers.");
        }
        if (totalTickets >= maxTicketCapacity) {
            throw new IllegalArgumentException("Total Tickets must be less than Max Ticket Capacity.");
        }
    }

    // Serialization and File handling
    public void serializeToFile(String filename) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
        }
    }

    public static Configuration loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (Configuration) in.readObject();
        }
    }

    public void saveToTextFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("--- Current Configuration ---\n");
            writer.write("Max Ticket Capacity: " + maxTicketCapacity + "\n");
            writer.write("Total Tickets: " + totalTickets + "\n");
            writer.write("Ticket Release Rate (ms): " + ticketReleaseRate + "\n");
            writer.write("Customer Retrieval Rate (ms): " + customerRetrievalRate + "\n");
            writer.write("-----------------------------\n");
        }
    }

    // Getters and Setters
    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public String toString() {
        return "Max Ticket Capacity: " + maxTicketCapacity +
                ", Total Tickets: " + totalTickets +
                ", Ticket Release Rate (ms): " + ticketReleaseRate +
                ", Customer Retrieval Rate (ms): " + customerRetrievalRate;
    }
}
