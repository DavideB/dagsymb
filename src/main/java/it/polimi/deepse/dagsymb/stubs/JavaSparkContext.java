package it.polimi.deepse.dagsymb.stubs;

import java.util.List;

import jbse.meta.Analysis;

public class JavaSparkContext {
	private static Object caller;
	
	public static Object getCaller() {
		return caller;
	}

	public static boolean isJbseExecution(){
		return !Analysis.isResolved(caller, "jbseExecutionFlag");
	}

	public JavaSparkContext(SparkConf conf, Object caller) {
		JavaSparkContext.caller = caller;
		if (!isJbseExecution()) {
			SymbolicProducer.reset();
		}
	}

	public JavaRDD<String> textFile(String textFile) {
		return new JavaRDD<>(null);
	}

	public void stop() {
	}

	public JavaRDD<?> parallelize(List<?> l, int slices) {
		return parallelize(l);
	}

	public JavaRDD<?> parallelize(List<?> l) {
		return new JavaRDD<>(l);
	}

}
