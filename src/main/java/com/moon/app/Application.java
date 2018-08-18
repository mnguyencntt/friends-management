package com.moon.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

  static {
    // TODO Set proxy here.
    // System.setProperty("http.proxySet", "true");
    // System.setProperty("http.proxyHost", "");
    // System.setProperty("http.proxyPort", "8080");
  }

  public static void main(String[] args) throws Throwable {
    SpringApplication.run(Application.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

}