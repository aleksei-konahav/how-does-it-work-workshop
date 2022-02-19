package com.company.howitworks.learnaspectj;

public class Application {

  public static void main(String[] args) {
    final CongratulationService service = new CongratulationService();

    service.congratulate("Aleksei");
    service.congratulate("Aleksei", "car");
  }
}
