package com.company.howitworks.learnaop._3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CongratulationService {

  public void congratulate(String person) {
    System.out.println("Congratulations to " + person);
  }

  public void congratulate(String person, String gift) {
    congratulate(person);
    System.out.println("Here is your gift - " + gift);
  }
}
