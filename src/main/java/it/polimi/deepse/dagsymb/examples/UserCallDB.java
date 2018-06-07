package it.polimi.deepse.dagsymb.examples;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import it.polimi.deepse.dagsymb.stubs.JavaSparkContext;

public class UserCallDB
{
	
	private static String customer = UUID.randomUUID().toString();
	
	public static List<Call> getCurrentMonthCalls(){
		return getCallsOfPastMonth(0);
	}
	
	public static List<Call> getCallsOfPastMonth(int monthInThePast){
		List<Call> calls = new ArrayList<Call>();
		/*GIO*/if (!JavaSparkContext.isJbseExecution()) {
			return ((PromoCalls)JavaSparkContext.getCaller()).getDataset(2);
		} //calls.addAll(Utils.createRandomCallForCaller(customer, 3500));
		return calls;
	}
	
	public static List<Call> getLast24HoursLocalCalls(){
		List<Call> calls = new ArrayList<Call>();
		/*GIO*/if (!JavaSparkContext.isJbseExecution()) {
			return ((PromoCalls)JavaSparkContext.getCaller()).getDataset(0);
		} //	calls.addAll(Utils.createRandomCallForCaller(customer, 100));
		return calls;
	}
	
	public static List<Call> getLast24HoursAbroadCalls(){
		List<Call> calls = new ArrayList<Call>();
		/*GIO*/if (!JavaSparkContext.isJbseExecution()) {
			return ((PromoCalls)JavaSparkContext.getCaller()).getDataset(1);
		} //calls.addAll(Utils.createRandomCallForCaller(customer, 30));
		return calls;
	}

}