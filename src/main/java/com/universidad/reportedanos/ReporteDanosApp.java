package com.universidad.reportedanos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

 feature/frontend-thymeleaf
// Indicamos donde estan las entidades y los repositorios
@SpringBootApplication(scanBasePackages = {
    "com.universidad.reportedanos",
    "controller",
    "service"
})
@EntityScan(basePackages = "com.universidad.reportedanos.modelo")
@EnableJpaRepositories(basePackages = "repository")
=======
// Escaneamos todos los paquetes del proyecto
@SpringBootApplication(scanBasePackages = {
    "com.universidad.reportedanos",
    "controller",
    "service",
    "repository"
})
 main
public class ReporteDanosApp {
    public static void main(String[] args) {
        SpringApplication.run(ReporteDanosApp.class, args);
    }
}
