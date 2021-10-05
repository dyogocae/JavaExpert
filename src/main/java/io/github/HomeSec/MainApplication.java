package io.github.HomeSec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MainApplication extends SpringBootServletInitializer {

    // psvm
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
