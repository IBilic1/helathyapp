package hr.algebra.healthyapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProps {

    private List<String> systemUsers;

    public List<String> getSystemUsers() {
        return systemUsers;
    }

    public void setSystemUsers(List<String> systemUsers) {
        this.systemUsers = systemUsers;
    }
}
