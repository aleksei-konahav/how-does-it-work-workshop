package com.company.howitworks.learnaop._2;

import static com.company.howitworks.learnaop._2.ExecutionLogger.logExecution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CongratulationService {

  public void congratulate(String person) {
    logExecution("congratulate", "person=" + person, () -> {
      System.out.println("Congratulations to " + person);
    });
  }

  public void congratulate(String person, String gift) {
    logExecution("congratulate", "person=" + person + ", gift=" + gift, () -> {
      congratulate(person);
      System.out.println("Here is your gift - " + gift);
    });
  }
}
