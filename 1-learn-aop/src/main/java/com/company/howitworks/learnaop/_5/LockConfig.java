package com.company.howitworks.learnaop._5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LockConfig {

  @Bean
  public Lock lock() {
    return new ReentrantLock();
  }
}
