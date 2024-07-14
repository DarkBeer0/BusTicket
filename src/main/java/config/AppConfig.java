package config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    @ConditionalOnProperty(name = "user.ticket.update.enabled", havingValue = "true")
    public String thisIsMyFirstConditionalBean() {
        return "This bean is only available when user.ticket.update.enabled is true";
    }
}
