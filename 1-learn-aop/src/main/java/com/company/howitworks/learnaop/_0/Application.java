package com.company.howitworks.learnaop._0;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    final ConfigurableApplicationContext ctx = new SpringApplicationBuilder()
        .sources(Application.class)
        .web(WebApplicationType.NONE)
        .run(args);

    final CongratulationService service = ctx.getBean(CongratulationService.class);

    service.congratulate("Aleksei");
    service.congratulate("Aleksei", "car");
  }
}
