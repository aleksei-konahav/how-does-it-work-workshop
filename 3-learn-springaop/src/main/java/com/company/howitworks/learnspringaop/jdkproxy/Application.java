package com.company.howitworks.learnspringaop.jdkproxy;

import com.company.howitworks.learnspringaop.AspectConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AspectConfig.class)
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
