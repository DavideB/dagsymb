package it.polimi.deepse.dagsymb.examples;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class GuardEvaluatorTest {

	@Test
	public void test1() {
		Map<String, Object> values = new HashMap<>();
		
		IGuardEvaluator evaluator = new GuardEvaluatorPromoCallsFile();
		List<Integer> result = evaluator.evaluateGuards(values);
		
		System.out.println("test1: " + result);
	}

	@Test
	public void test2() {
		Map<String, Object> values = new HashMap<>();
		values.put("arg0", "110");
		
		IGuardEvaluator evaluator = new GuardEvaluatorPromoCallsFile();
		List<Integer> result = evaluator.evaluateGuards(values);
		
		System.out.println("test2: " + result);
	}

	@Test
	public void test3() {
		Map<String, Object> values = new HashMap<>();
		values.put("arg0", "110");
		values.put("arg1", "2000");
		values.put("count_PromoCalls.java:42_0", 2000L);
		values.put("arg2", "2000");
		values.put("count_PromoCalls.java:45_0", 3000L);
		
		
		IGuardEvaluator evaluator = new GuardEvaluatorPromoCallsFile();
		List<Integer> result = evaluator.evaluateGuards(values);
		
		System.out.println("test3: " + result);
	}

}
