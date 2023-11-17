package dev.jlkeesh.springbootstartercustom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.jlkeesh.springbootstartercustom.security.CorsProperty;

@SpringBootApplication
public class SpringBootStarterCustomApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootStarterCustomApplication.class, args);
  }
}
