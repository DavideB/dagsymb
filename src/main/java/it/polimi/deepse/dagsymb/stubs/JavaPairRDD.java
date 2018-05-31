package it.polimi.deepse.dagsymb.stubs;

import java.util.List;

public class JavaPairRDD<T1, T2> extends JavaRDD<Object> {

	public JavaPairRDD(List<Object> data) {
		super(data);
	}

	public JavaPairRDD<T1, T2> distinct() {
		return this;	
	}

	public JavaPairRDD<T1, T2> groupByKey() {
		return this;	
	}

	public JavaPairRDD<T1, T2> cache() {
		return this;
	}

	public JavaPairRDD<T1, T2> mapValues(Lambda1P lambda) {
		return this;
	}

	public JavaPairRDD<T1, T2> join(JavaPairRDD pairs) {
		return this;
	}

	public JavaPairRDD<T1, T2> values() {
		return this;
	}

	public JavaPairRDD<T1, T2> flatMapToPair(Lambda1P lambda) {
		return this;
	}

	public JavaPairRDD<T1, T2> reduceByKey(Lambda2P lambda) {
		return this;
	}

}
