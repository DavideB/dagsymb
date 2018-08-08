package it.polimi.deepse.dagsymb.examples;
import java.util.List;
import java.util.Map;

public interface IGuardEvaluator {
	
	public List<Integer> evaluateGuards(Map<String, Object> knownValues);

}
