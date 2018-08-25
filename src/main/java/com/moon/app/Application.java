package com.moon.app;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.moon.app.exception.TechnicalException;
import com.moon.app.service.AccountService;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

  @Autowired
  private AccountService accountService;

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

  @PostConstruct
  public void init() {
    final List<String> initEmails = Arrays.asList("andy@example.com", "john@example.com", "lisa@example.com", "minh@example.com");
    initEmails.forEach(email -> {
      try {
        accountService.createByEmail(email);
      } catch (TechnicalException e) {
        e.printStackTrace();
      }
    });
  }

}