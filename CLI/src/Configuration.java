import com.google.gson.Gson;
import java.io.*;
import java.util.Scanner;

public class Configuration implements Serializable {
    private int maxTicketCapacity;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    public void collectInputs() {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("Enter maximum ticket capacity: ");
            maxTicketCapacity = input.nextInt();

            System.out.print("Enter total number of tickets: ");
            totalTickets = input.nextInt();

            if (maxTicketCapacity >= totalTickets) {
                break; // Exit loop if valid
            } else {
                System.out.println("Error: Maximum ticket capacity must be greater than or equal to the total number of tickets. Please try again.");
            }
        }

        System.out.print("Enter ticket release rate (s): ");
        ticketReleaseRate = input.nextInt();

        System.out.print("Enter customer retrieval rate (s): ");
        customerRetrievalRate = input.nextInt();
    }

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
            writer.write("Ticket Release Rate (s): " + ticketReleaseRate + "\n");
            writer.write("Customer Retrieval Rate (s): " + customerRetrievalRate + "\n");
            writer.write("-----------------------------\n");
        }
    }

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

    @Override
    public String toString() {
        return "Max Ticket Capacity: " + maxTicketCapacity +
                ", Total Tickets: " + totalTickets +
                ", Ticket Release Rate (s): " + ticketReleaseRate +
                ", Customer Retrieval Rate (s): " + customerRetrievalRate;
    }
}
