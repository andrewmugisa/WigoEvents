package org.wigo.wigoevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(excludeName = {
        "org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration",
        "org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration",
        "org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration"
})
public class WigoEventsApplication {
    public static void main(String[] args) {
        SpringApplication.run(WigoEventsApplication.class, args);
    }
}
