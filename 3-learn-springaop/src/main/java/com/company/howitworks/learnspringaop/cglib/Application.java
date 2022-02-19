package com.company.howitworks.learnspringaop.cglib;

import com.company.howitworks.learnspringaop.AspectConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AspectConfig.class)
public class Application {

  public static void main(String[] args) {
//    System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "3-learn-springaop/src/main/resources");

    final ConfigurableApplicationContext ctx = new SpringApplicationBuilder()
        .sources(Application.class)
        .web(WebApplicationType.NONE)
        .run(args);

    final CongratulationService service = ctx.getBean(CongratulationService.class);

    service.congratulate("Aleksei");
    service.congratulate("Aleksei", "car");
  }
}
