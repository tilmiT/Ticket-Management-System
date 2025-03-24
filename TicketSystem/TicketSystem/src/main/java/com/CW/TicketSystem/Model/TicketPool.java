package com.CW.TicketSystem.Model;

import com.CW.TicketSystem.LoggerUtil;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketPool {
    private final int maxCapacity;
    private final Queue<Ticket> ticketQueue;
    private static final Logger logger = LoggerUtil.getLogger(TicketPool.class.getName());

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.ticketQueue = new LinkedList<>();
    }

    public synchronized void addTicket(Ticket ticket) {
        try {
            while (ticketQueue.size() >= maxCapacity) {
                System.out.println(Thread.currentThread().getName() + " is waiting to add a ticket...");
                wait();
            }
            ticketQueue.add(ticket);
            notifyAll();
            logger.log(Level.INFO, "Ticket added by {0} - Queue size: {1} - Ticket: {2}",
                    new Object[]{Thread.currentThread().getName(), ticketQueue.size(), ticket});
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.WARNING, "Thread interrupted while adding a ticket", e);
        }
    }

    public synchronized Ticket buyTicket() {
        try {
            while (ticketQueue.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " is waiting for tickets...");
                wait();
            }
            Ticket ticket = ticketQueue.poll();
            notifyAll();
            logger.log(Level.INFO, "Ticket purchased by {0} - Queue size: {1} - Ticket: {2}",
                    new Object[]{Thread.currentThread().getName(), ticketQueue.size(), ticket});
            return ticket;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.WARNING, "Thread interrupted while buying a ticket", e);
            return null;
        }
    }
}

