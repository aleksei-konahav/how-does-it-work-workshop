package com.company.howitworks.learnaspectj;

public class CongratulationService {

  public void congratulate(String person) {
    System.out.println("Congratulations to " + person);
  }

  public void congratulate(String person, String gift) {
    congratulate(person);
    System.out.println("Here is your gift - " + gift);
  }
}
