/*
 * This file was automatically generated by EvoSuite
 * Wed May 16 13:25:00 GMT 2018
 */

import it.polimi.deepse.dagsymb.examples.UserCallDB;
import org.junit.Test;
import it.polimi.deepse.dagsymb.examples.PromoCalls;

public class PromoCalls_run_driver_PC_0_4_Test {

  //Test case number: 0
  /*
   * 1 covered goal:
   * Goal 1. com.xspark.varyingdag.examples.calls.PromoCalls.run_driver(IJJI)V: path condition EvoSuiteWrapper_0_4 (id = 0)
   */

  @Test(timeout = 4000)
  public void test0() {
      PromoCalls promoCalls0 = new PromoCalls();
      promoCalls0.addToDatasetLast24HoursLocalCalls(3502, 3502);
      promoCalls0.addToDatasetLast24HoursAbroadCalls(3502, 3502);
      promoCalls0.run(2100, 2100, 2100, 1);
  }
}
