package it.polimi.deepse.dagsymb.examples;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import it.polimi.deepse.dagsymb.stubs.JavaSparkContext;

public class UserCallDB
{

	private static String customer = UUID.randomUUID().toString();

	private static List<Call> currentMonthCalls = new ArrayList<>();
	private static List<Call> last24HoursLocalCalls = new ArrayList<>();
	private static List<Call> last24HoursAbroadCalls = new ArrayList<>();

	public static List<Call> getCurrentMonthCalls(){
		return currentMonthCalls;
	}

	public static List<Call> getPastMonthCalls(int pastMonth){
		return currentMonthCalls;
	}

	public static List<Call> getLast24HoursLocalCalls(){
		return last24HoursLocalCalls;
	}

	public static List<Call> getLast24HoursAbroadCalls(){
		return last24HoursAbroadCalls;
	}

	public static void addCallsToMonthCalls(int callLength, int size){
		currentMonthCalls.addAll(Utils.createRandomCallForCaller(customer, callLength, size));
	}

	public static void addCallsToLast24HoursLocalCalls(int callLength, int size){
		last24HoursLocalCalls.addAll(Utils.createRandomCallForCaller(customer, callLength, size));
	}

	public static void addCallsToLast24HoursAbroadCalls(int callLength, int size){
		last24HoursAbroadCalls.addAll(Utils.createRandomCallForCaller(customer, callLength, size));
	}


}