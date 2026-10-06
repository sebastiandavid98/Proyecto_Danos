package com.universidad.reportedanos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Escaneamos todos los paquetes del proyecto
@SpringBootApplication(scanBasePackages = {
    "com.universidad.reportedanos",
    "controller",
    "service",
    "repository"
})
public class ReporteDanosApp {
    public static void main(String[] args) {
        SpringApplication.run(ReporteDanosApp.class, args);
    }
}
