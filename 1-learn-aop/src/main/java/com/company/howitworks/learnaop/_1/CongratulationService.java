package com.company.howitworks.learnaop._1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CongratulationService {

  public void congratulate(String person) {
    log.info("Start execution of [congratulate] with arguments: person={}", person);
    System.out.println("Congratulations to " + person);
    log.info("Finish execution of [congratulate]");
  }

  public void congratulate(String person, String gift) {
    log.info("Start execution of [congratulate] with arguments: person={}, gift={}", person, gift);
    congratulate(person);
    System.out.println("Here is your gift - " + gift);
    log.info("Finish execution of [congratulate]");
  }
}
