package it.polimi.deepse.dagsymb.launchers;/*
 * This file was automatically generated by EvoSuite
 * Wed May 16 13:21:27 GMT 2018
 */

import it.polimi.deepse.dagsymb.examples.PromoCalls;
import it.polimi.deepse.dagsymb.examples.UserCallDB;

public class Launcher {

  //Test case number: depends on arguments passed
  /*
   * 1 covered goal: depends on arguments passed
   * Goal 1. com.xspark.varyingdag.examples.calls.PromoCalls.run_driver(IJJI)V: path condition EvoSuiteWrapper_0_2 (id = 0)
   */

    public static void main(String[] args) {
      PromoCalls promoCalls0 = new PromoCalls(); //args[0] is the IGuardEvaluator implementation Class FQ Classname
      boolean genData = false;
      String appName = "";
      if (args[12] != null && args[12].startsWith("-g")) genData = true;
      if (args[13] != null && !args[13].startsWith("-")) appName = args[12];
      //UserCallDB.addCallsToLast24HoursLocalCalls(Integer.parseInt(args[5]), Integer.parseInt(args[6]));
      //UserCallDB.addCallsToLast24HoursAbroadCalls(Integer.parseInt(args[7]), Integer.parseInt(args[8]));
      //UserCallDB.addCallsToMonthCalls(Integer.parseInt(args[9]), Integer.parseInt(args[10]));
      //promoCalls0.run(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
      
      promoCalls0.run(Integer.parseInt(args[1]), Long.parseLong(args[2]), Long.parseLong(args[3]), Integer.parseInt(args[4]),
    		  		  Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]), Integer.parseInt(args[8]),
    				  Integer.parseInt(args[9]), Integer.parseInt(args[10]), Integer.parseInt(args[11]), genData, appName);
  }
}
