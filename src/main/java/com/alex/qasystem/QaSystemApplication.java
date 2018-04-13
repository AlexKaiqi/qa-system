package com.alex.qasystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Alex
 */
@SpringBootApplication
@EnableScheduling
public class QaSystemApplication {

    public static void main(String[] args) { // some
        SpringApplication.run(QaSystemApplication.class, args);
    }

}
