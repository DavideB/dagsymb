package it.polimi.deepse.dagsymb.stubs;

import java.util.ArrayList;
import java.util.List;

public class JavaRDD<T> {

	private final List<T> data;
	public JavaRDD(List<T> data) {
		this.data = data;
	}

	public JavaPairRDD mapToPair(Object lambda) {
		return new JavaPairRDD(data);
	}

	public JavaRDD<T> distinct() {
		return this;	
	}

	public JavaRDD<T> groupByKey() {
		return this;
	}

	public JavaRDD<T> cache() {
		return this;
	}

	public JavaRDD<T> mapValues(Lambda1P lambda) {
		return this;
	}
	
	public JavaRDD<T> values() {
		return this;
	}

	public JavaRDD<T> map(Lambda1P lambda) {
		if (data != null) {
			List<T> newData = new ArrayList<>();
			for (T d : data) {
				newData.add((T) lambda.process(d));
			}
			return new JavaRDD<>(newData);
		}
		return this;
	}

	public JavaRDD<T> filter(Lambda1P lambda) {
		if (data != null) {
			List<T> newData = new ArrayList<>();
			for (T d : data) {
				if((boolean) lambda.process(d)) {
					newData.add(d);
				}
			}
			return new JavaRDD<>(newData);
		}
		return this;
	}

	public List<?> collect() {
		if (JavaSparkContext.isJbseExecution()) {
			return SymbolicProducer._I().getArrayList();	
		}
		SymbolicProducer._I().setArrayList((ArrayList) data);
		return data;
	}

	public long count () {
		if (JavaSparkContext.isJbseExecution()) {
			return SymbolicProducer._I().getLong();			
		}
		SymbolicProducer._I().setLong(data.size());
		return data.size();
	}

	public int reduce(Lambda2P lambda) {
		if (JavaSparkContext.isJbseExecution()) {
			return SymbolicProducer._I().getInt(); 
		}
		int res = data.size(); //TODO: do the actual computation of reduce
		SymbolicProducer._I().setInt(res);
		return res; 
	}

}
