package com.company.howitworks.learnspringaop.jdkproxy;

import com.company.howitworks.learnspringaop.LogMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CongratulationServiceImpl implements CongratulationService {

  @LogMethod
  @Override
  public void congratulate(String person) {
    System.out.println("Congratulations to " + person);
  }

  @LogMethod
  @Override
  public void congratulate(String person, String gift) {
    congratulate(person);
    System.out.println("Here is your gift - " + gift);
  }
}
