import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        Scanner input = new Scanner(System.in);

        System.out.println("System Initialization and Configuration:");
        System.out.println("1. Save configuration");
        System.out.println("2. Load configuration");
        System.out.print("Choice (press 1 or 2): ");
        int choice = input.nextInt();

        if (choice == 2) {
            try {
                config = Configuration.loadFromFile("config.ser");
                System.out.println("Configuration loaded successfully:");
                System.out.println(config);
            } catch (Exception e) {
                System.out.println("Failed to load configuration: " + e.getMessage());
                System.out.println("Proceeding with new configuration setup...");
                config.collectInputs();
            }
        } else {
            config.collectInputs();
            try {
                config.saveToTextFile("config.txt");
                System.out.println("\n--- Current Configuration ---");
                System.out.println("Max Ticket Capacity: " + config.getMaxTicketCapacity());
                System.out.println("Total Tickets: " + config.getTotalTickets());
                System.out.println("Ticket Release Rate (ms): " + config.getTicketReleaseRate());
                System.out.println("Customer Retrieval Rate (ms): " + config.getCustomerRetrievalRate());
                System.out.println("-----------------------------");
                System.out.println("Configuration saved to text file: config.txt");
                config.serializeToFile("config.ser");
                System.out.println("Configuration saved successfully.");
            } catch (Exception e) {
                System.out.println("Error while saving configuration: " + e.getMessage());
            }
        }

        input.nextLine(); // Consume leftover newline
        System.out.print("\nEnter 'start', 'stop', or 'exit': ");
        String command = input.nextLine().toLowerCase();

        if (command.equals("start")) {
            System.out.println("Simulation started!");
            runSimulation(config);
        } else if (command.equals("stop")) {
            System.out.println("Simulation stopped.");
            System.exit(0);
        } else {
            System.out.println("Exiting program.");
            System.exit(0);
        }
    }

    private static void runSimulation(Configuration config) {
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

        Vendor vendor = new Vendor(config.getTotalTickets(), config.getTicketReleaseRate(), ticketPool);
        Thread vendorThread = new Thread(vendor, "Vendor");

        Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate(), config.getTotalTickets(), "Customer");
        Thread customerThread = new Thread(customer, "Customer");

        vendorThread.start();
        customerThread.start();
    }
}

