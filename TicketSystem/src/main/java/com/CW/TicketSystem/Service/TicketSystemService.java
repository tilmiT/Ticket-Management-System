package com.CW.TicketSystem.Service;

import com.CW.TicketSystem.Configuration.Configuration;
import com.CW.TicketSystem.Model.Customer;
import com.CW.TicketSystem.Model.TicketPool;
import com.CW.TicketSystem.Model.Vendor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TicketSystemService {

    private ExecutorService executor;
    private boolean isSystemRunning = false;

    public void startSystem(Configuration config) {
        if (isSystemRunning) {
            throw new IllegalStateException("Ticket System is already running.");
        }

        executor = Executors.newFixedThreadPool(8); // Example: 5 vendors + 3 customers
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

        // Start Vendor Threads
        for (int i = 1; i <= 5; i++) {
            executor.execute(new Vendor(config.getTotalTickets(),config.getTicketReleaseRate(),ticketPool,"Vendor"));
        }

        // Start Customer Threads
        for (int i = 1; i <= 3; i++) {
            executor.execute(new Customer(ticketPool,config.getCustomerRetrievalRate(),50,"Customer"));
        }

        isSystemRunning = true;
    }

    public void stopSystem() {
        if (!isSystemRunning) {
            throw new IllegalStateException("Ticket System is not running.");
        }

        executor.shutdownNow();
        isSystemRunning = false;
    }
}
