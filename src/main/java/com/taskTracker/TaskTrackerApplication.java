package com.taskTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.taskTracker.**"})
@EnableJpaRepositories(basePackages = {"com.taskTracker.repository"})
@EntityScan(basePackages = {"com.taskTracker.domain"})
public class TaskTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerApplication.class, args);
    }
}
