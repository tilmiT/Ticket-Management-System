package com.CW.TicketSystem.Model;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;
    private final int quantity;
    private final String customerName;

    public Customer(TicketPool ticketPool, int retrievalRate, int quantity, String customerName) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.quantity = quantity;
        this.customerName = customerName;
    }

    @Override
    public void run() {
        System.out.println(customerName + " started.");
        for (int i = 0; i < quantity; i++) {
            Ticket ticket = ticketPool.buyTicket();
            System.out.println("Ticket purchased: " + ticket + " by " + customerName);
            try {
                Thread.sleep(retrievalRate * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Customer interrupted.");
                break;
            }
        }
        System.out.println(customerName + " finished.");
    }
}

