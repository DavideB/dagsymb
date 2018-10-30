package it.polimi.deepse.dagsymb.examples;


import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
import java.util.UUID;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

//import it.polimi.deepse.dagsymb.stubs.JavaSparkContext;

public class UserCallDB
{
	
	private static String customer = UUID.randomUUID().toString();

	private static String currentMonthCalls = "user/Davide/currentMonthCalls.txt";
	private static String last24HoursLocalCalls = "user/Davide/last24HoursLocalCalls.txt";
	private static String last24HoursAbroadCalls = "user/Davide/last24HoursAbroadCalls.txt";

	public static String getCurrentMonthCalls(){
		return currentMonthCalls;
	}

	public static String getPastMonthCalls(int pastMonth){
		return currentMonthCalls;
	}

	public static String getLast24HoursLocalCalls(){
		return last24HoursLocalCalls;
	}

	public static String getLast24HoursAbroadCalls(){
		return last24HoursAbroadCalls;
	}

	public static void addCallsToMonthCalls(int callLength, int size){
		Utils.createRandomCallForCaller("currentMonthCalls.txt", customer, callLength, size);
	}

	public static void addCallsToLast24HoursLocalCalls(int callLength, int size){
	/*	try {
				FileSystem fs = FileSystem.get(new Configuration());
				FileStatus[] status = fs.listStatus(new Path("file:///"));
				for (FileStatus s : status) System.out.println("HDFS files: " + s.getPath());
				//status.foreach(x => println(x.getPath))
		} catch (IOException e) { System.out.println(e.getMessage());};
	*/	//URI uri = URI.create("hdfs://localhost:9000/last24HoursLocalCalls.txt");
		//try {Utils.createRandomCallForCaller(uri.toURL().toString(), customer, callLength, size);} catch (MalformedURLException e) {System.out.println(e.getMessage());};
		Utils.createRandomCallForCaller("last24HoursLocalCalls.txt", customer, callLength, size);
	}

	public static void addCallsToLast24HoursAbroadCalls(int callLength, int size){
		Utils.createRandomCallForCaller("last24HoursAbroadCalls.txt", customer, callLength, size);
	}


}