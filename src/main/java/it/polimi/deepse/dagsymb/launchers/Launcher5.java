package it.polimi.deepse.dagsymb.launchers;/*
 * This file was automatically generated by EvoSuite
 * Wed May 16 13:22:42 GMT 2018
 */

import it.polimi.deepse.dagsymb.examples.PromoCalls;
import it.polimi.deepse.dagsymb.examples.UserCallDB;

public class Launcher5 {

  //Test case number: 0
  /*
   * 1 covered goal:
   * Goal 1. com.xspark.varyingdag.examples.calls.PromoCalls.run_driver(IJJI)V: path condition EvoSuiteWrapper_0_5 (id = 0)
   */

    public static void main(String[] args) {
	  int threshold = 1376;
      long minLocalLongCalls = 1376;
      long minAbroadLongCalls = 1381;
      int pastMonths = 1;
      int last24HLocalCallsLength = 1381;
      int last24HLocalCallsSize = 1381;
      int last24HAbroadCallsLength = 0;
      int last24HAbroadCallsSize = 0;
      int MonthCallsLength = 2990;
      int MonthCallsSize = 3000;
      int num_partitions = 500;
      PromoCalls promoCalls0 = new PromoCalls();
      //UserCallDB.addCallsToLast24HoursLocalCalls(1381, 1381);
      //promoCalls0.run(1376, 1376, 1381, 1);
      promoCalls0.run(threshold, minLocalLongCalls, minAbroadLongCalls, pastMonths, 
	  		  last24HLocalCallsLength, last24HLocalCallsSize, 
	  		  last24HAbroadCallsLength, last24HAbroadCallsSize, 
	  		  MonthCallsLength, MonthCallsSize, num_partitions);
  }
}
