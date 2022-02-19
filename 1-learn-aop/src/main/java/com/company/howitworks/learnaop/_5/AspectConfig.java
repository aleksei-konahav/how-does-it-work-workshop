package com.company.howitworks.learnaop._5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.company.howitworks.learnaop._4.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class AspectConfig {

  @Bean
  public LoggingAspect loggingAspect() {
    return new LoggingAspect();
  }

  @Bean
  public ExclusiveLockAspect exclusiveLockAspect(Lock lock) {
    return new ExclusiveLockAspect(lock);
  }
}
