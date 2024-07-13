package config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    @ConditionalOnProperty(name = "my.custom.bean.enabled", havingValue = "true")
    public String ThisIsMyFirstConditionalBean() {
        return "This is my first conditional bean!";
    }
}
