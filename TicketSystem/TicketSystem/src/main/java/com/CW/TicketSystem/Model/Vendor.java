package com.CW.TicketSystem.Model;

import java.math.BigDecimal;

public class Vendor implements Runnable {
    private int totalTickets;
    private int releaseRate;
    private TicketPool ticketPool;

    public Vendor(int totalTickets, int releaseRate, TicketPool ticketPool, String vendor) {
        this.totalTickets = totalTickets;
        this.releaseRate = releaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started.");
        for (int i = 1; i <= totalTickets; i++) {
            Ticket ticket = new Ticket(i, "Event-" + i, new BigDecimal("100.00"));
            ticketPool.addTicket(ticket);
            System.out.println("Ticket added: " + ticket + " by " + Thread.currentThread().getName());
            try {
                Thread.sleep(releaseRate * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Vendor interrupted.");
                break;
            }
        }
        System.out.println(Thread.currentThread().getName() + " finished.");
    }
}
