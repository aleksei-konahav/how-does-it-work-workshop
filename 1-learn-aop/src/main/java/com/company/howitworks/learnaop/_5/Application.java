package com.company.howitworks.learnaop._5;

import java.util.concurrent.locks.Lock;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
  public static void main(String[] args) throws InterruptedException {
    final ConfigurableApplicationContext ctx = new SpringApplicationBuilder()
        .sources(Application.class)
        .web(WebApplicationType.NONE)
        .run(args);

    final CongratulationService service = ctx.getBean(CongratulationService.class);

    final Lock lock = ctx.getBean(Lock.class);
    try {
      lock.lock();
      final Thread thread = new Thread(() -> service.sendGiftTo("Aleksei"));
      thread.start();
      thread.join();
    } finally {
      lock.unlock();
    }
  }
}
