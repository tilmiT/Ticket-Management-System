package com.CW.TicketSystem.Service;
import com.CW.TicketSystem.Configuration.Configuration;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private final Configuration configuration;

    public ConfigurationService(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void updateConfiguration(Configuration updatedConfig) {
        configuration.setTotalTickets(updatedConfig.getTotalTickets());
        configuration.setMaxTicketCapacity(updatedConfig.getMaxTicketCapacity());

        configuration.setTicketReleaseRate(updatedConfig.getTicketReleaseRate());

        configuration.setCustomerRetrievalRate(updatedConfig.getCustomerRetrievalRate());
    }
}
