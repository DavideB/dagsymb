package it.polimi.deepse.dagsymb.launchers;/*
 * This file was automatically generated by EvoSuite
 * Wed May 16 13:27:55 GMT 2018
 */

import it.polimi.deepse.dagsymb.examples.PromoCalls;
import it.polimi.deepse.dagsymb.examples.UserCallDB;

public class Launcher7 {

  //Test case number: 0
  /*
   * 1 covered goal:
   * Goal 1. com.xspark.varyingdag.examples.calls.PromoCalls.run_driver(IJJI)V: path condition EvoSuiteWrapper_0_7 (id = 0)
   */

  public static void main(String[] args) {
      PromoCalls promoCalls0 = new PromoCalls();
      UserCallDB.addCallsToLast24HoursLocalCalls(2350, 2350);
      promoCalls0.run(1942, 1942, 1942, 2);
  }
}