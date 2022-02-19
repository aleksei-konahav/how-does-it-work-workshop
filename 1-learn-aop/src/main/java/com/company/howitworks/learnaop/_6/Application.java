package com.company.howitworks.learnaop._6;

import java.util.concurrent.locks.Lock;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {

  @EventListener(GiftDto.class)
  public void onEvent(GiftDto dto) {
    System.out.println(dto);
  }

  public static void main(String[] args) throws InterruptedException {
    final ConfigurableApplicationContext ctx = new SpringApplicationBuilder()
        .sources(Application.class)
        .web(WebApplicationType.NONE)
        .run(args);

    final CongratulationService service = ctx.getBean(CongratulationService.class);

    service.createGiftFor("Aleksei");
  }
}
