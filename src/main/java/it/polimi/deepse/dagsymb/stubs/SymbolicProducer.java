package it.polimi.deepse.dagsymb.stubs;

import java.util.ArrayList;

import jbse.meta.Analysis;

public class SymbolicProducer {
	public static void reset() { _I = new SymbolicProducer(); }
	private static SymbolicProducer _I = new SymbolicProducer();
	private SymbolicProducer() {}
	public static SymbolicProducer _I() {
		Analysis.assume(_I != null);
		return _I;
	}
	
	private int[] valsInt = new int[100];
	private int nextInt = 0;
	public int getInt() {
		if (!Analysis.isResolved(this, "valsInt")) {
			nextInt = 0;
		}
		Analysis.assume(valsInt != null);
		Analysis.assume(valsInt.length > nextInt);
		return valsInt[nextInt++];
	}
	public void setInt(int concrete) {
		valsInt[nextInt++] = concrete;
	}

	private long[] valsLong = new long[100];
	private int nextLong = 0;
	public long getLong() {
		if (!Analysis.isResolved(this, "valsLong")) {
			nextLong = 0;
		}
		Analysis.assume(valsLong != null);
		Analysis.assume(valsLong.length > nextLong);
		return valsLong[nextLong++];
	}
	public void setLong(long concrete) {
		valsLong[nextLong++] = concrete;
	}

	private ArrayList[] valsArrayList = new ArrayList[100];
	private int nextArrayList = 0;
	public ArrayList getArrayList() {
		if (!Analysis.isResolved(this, "valsArrayList")) {
			nextArrayList = 0;
		}
		Analysis.assume(valsArrayList != null);
		Analysis.assume(valsArrayList.length > nextArrayList);
		ArrayList ret = valsArrayList[nextArrayList++];
		Analysis.assume(ret != null);
		for (int i = 0; i < nextArrayList - 1; i++) {
			Analysis.assume(ret != valsArrayList[i]);
		}			
			
		return ret;
	}
	public void setArrayList(ArrayList concrete) {
		valsArrayList[nextArrayList++] = concrete;
	}
}
