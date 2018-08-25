/*
 * 
 */
package com.moon.app;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


  @Bean
  public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.moon.app.controller"))
          .paths(PathSelectors.ant("/*"))
          .build()
          .apiInfo(apiInfo())
          .useDefaultResponseMessages(false)
          .globalResponseMessage(
               RequestMethod.GET, 
               Arrays.asList(
                   new ResponseMessageBuilder()
                     .code(500).message("Internal server error")
                     .responseModel(new ModelRef("Error"))
                     .build(), 
                   new ResponseMessageBuilder()
                     .code(403)
                     .message("Forbidden!!!!!")
                     .build()));
  }

  private ApiInfo apiInfo() {
      ApiInfo apiInfo = new ApiInfo("My REST API", "Some custom description of API.", "API TOS", "Terms of service",
          new Contact("Minh Nguyen", "m.nguyencntt@gmail.com", "m.nguyencntt@gmail.com"), "License of API", "API license URL",
          Collections.emptyList());
      return apiInfo;
  }

}