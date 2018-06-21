package it.polimi.deepse.dagsymb.examples;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class Utils
{

	public static void reset() { _I = new Utils(); }
	private static Utils _I = new Utils();
	private Utils() {}

	public static List<Call> createRandomCallForCaller(String callerId, int callLength,  int size){
		List<Call> res = new ArrayList<>();

		while(size>0){
			res.add(new Call(callerId, UUID.randomUUID().toString(), callLength, callLength * 0.2));
			size--;
		}
		return res;

	}
}