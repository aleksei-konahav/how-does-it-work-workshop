package com.company.howitworks.learnaop._4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class AspectConfig {

  @Bean
  public LoggingAspect loggingAspect() {
    return new LoggingAspect();
  }
}
