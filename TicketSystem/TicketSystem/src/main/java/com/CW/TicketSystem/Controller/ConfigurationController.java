package com.CW.TicketSystem.Controller;
import com.CW.TicketSystem.Configuration.Configuration;
import com.CW.TicketSystem.Service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class ConfigurationController {
    private final ConfigurationService ConfigurationService;

    @Autowired
    public ConfigurationController(ConfigurationService configurationService) {
        this.ConfigurationService = configurationService;
    }

    @GetMapping("/getConfig")
    public Configuration getConfiguration() {
        return ConfigurationService.getConfiguration();
    }

    @PostMapping("/posting")
    public String updateConfiguration(@RequestBody Configuration updatedConfig) {
        ConfigurationService.updateConfiguration(updatedConfig);
        return "Configuration updated successfully!";
    }

}