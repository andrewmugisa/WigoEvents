package org.wigo.wigoevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        ServletWebSecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
})
public class WigoEventsApplication {
    public static void main(String[] args) {
        SpringApplication.run(WigoEventsApplication.class, args);
    }
}
