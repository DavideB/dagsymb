package it.polimi.deepse.dagsymb.examples;

import it.polimi.deepse.dagsymb.stubs.SparkConf; /*GIO*///import org.apache.spark.SparkConf;

import jbse.meta.Analysis;

import java.util.ArrayList;
import java.util.List;

import it.polimi.deepse.dagsymb.stubs.JavaSparkContext; /*GIO*///import org.apache.spark.api.java.JavaSparkContext;
import it.polimi.deepse.dagsymb.stubs.Lambda1P;


/*
 * The program simulates a daily routine of a telecommunication company. This company offers a promotion according
 * to how many -long calls- (e.g., greater than the 'threshold' parameter) a customer makes during a day.
 * It considers both local calls and calls outside the country.
 * - if the user makes more than 'minLocalLongCalls' local long calls or more than 'minAbroadLongCalls'
 * abroad long calls it will receive a 50% discount on all the calls made during the day
 * - if the user makes more than 'minLocalLongCalls' local long calls it will receive an additional discount of 5%
 * on all the calls of the current month and of 'pastMonths' in the past.
 * - if the users makes more 'minAbroadLongCalls' abroad long calls it will receive an additional discount of 10% on
 * all the calls of the current month
 *
 * Action 'collect' is used instead of action 'saveAsTextFile' for the sake of simplicity
 *
 */

/*GIO*///NB: Le parti annotate con il commento /*GIO*/ indicano le mie instrumentazioni del codice originale

@SuppressWarnings("resource")
public class PromoCalls {
	public Object jbseExecutionFlag;
	private List<Call>[] theInputDatasets = new List[]{
			new ArrayList<Call>(), new ArrayList<Call>(), new ArrayList<Call>(), new ArrayList<Call>(), new ArrayList<Call>(),
			new ArrayList<Call>(), new ArrayList<Call>(), new ArrayList<Call>(), new ArrayList<Call>(), new ArrayList<Call>()
	};


	public void run(final int threshold, long minLocalLongCalls, long minAbroadLongCalls, int pastMonths){
		/*GIO*/Analysis.assume(threshold > 1000);
		/*GIO*/Analysis.assume(minLocalLongCalls > 1000);
		/*GIO*/Analysis.assume(minAbroadLongCalls > 1000);
		/*GIO*/Analysis.assume(pastMonths >= 0);
		/*GIO*/Analysis.assume(pastMonths <= 2);

		SparkConf conf = new SparkConf().setAppName("CallsExample").setMaster("local[4]");
		JavaSparkContext sc = new JavaSparkContext(conf, this);

		long localLongCalls = sc.parallelize(UserCallDB.getLast24HoursLocalCalls())
				.filter(new Lambda1P() {
					@Override
					public Object process(Object o) {
						return ((Call) o).getLength() > threshold;	/*GIO*///call -> call.getLength() > threshold
					}
				}).count();

		long abroadLongCalls = sc.parallelize(UserCallDB.getLast24HoursAbroadCalls())
				.filter(new Lambda1P() {
					@Override
					public Object process(Object o) {
						return ((Call) o).getLength() > threshold;	/*GIO*///call -> call.getLength() > threshold
					}
				}).count();

		if (localLongCalls > minLocalLongCalls || abroadLongCalls > minAbroadLongCalls){
			sc.parallelize(UserCallDB.getLast24HoursLocalCalls()).map(new Lambda1P() {
				@Override
				public Object process(Object o) {
					return ((Call) o).setCost(((Call) o).getCost() * 0.5); /*GIO*/// call -> call.setCost(call.getCost()*0.5)
				}
			}).collect();

			sc.parallelize(UserCallDB.getLast24HoursAbroadCalls()).map(new Lambda1P() {
				@Override
				public Object process(Object o) {
					return ((Call) o).setCost(((Call) o).getCost() * 0.5); /*GIO*/// call -> call.setCost(call.getCost()*0.5)
				}
			}).collect();
		}

		if (localLongCalls > minLocalLongCalls) {
			sc.parallelize(UserCallDB.getCurrentMonthCalls()).map(new Lambda1P() {
				@Override
				public Object process(Object o) {
					return ((Call) o).setCost(((Call) o).getCost() * 0.95); /*GIO*/// call -> call.setCost(call.getCost()*0.95)
				}
			}).collect();

			for (int i = 1; i <= pastMonths; i++)
				sc.parallelize(UserCallDB.getPastMonthCalls(i)).map(new Lambda1P() {
					@Override
					public Object process(Object o) {
						return ((Call) o).setCost(((Call) o).getCost() * 0.95); /*GIO*/// call -> call.setCost(call.getCost()*0.95)
					}
				}).collect();
		}

		if (abroadLongCalls > minAbroadLongCalls){
			sc.parallelize(UserCallDB.getCurrentMonthCalls()).map(new Lambda1P() {
				@Override
				public Object process(Object o) {
					return ((Call) o).setCost(((Call) o).getCost() * 0.9); /*GIO*/// call -> call.setCost(call.getCost()*0.9)
				}
			}).collect();
		}

		/*GIO*//*try {
			Thread.sleep(1000*60*60);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */

		/*GIO*/run(threshold, minLocalLongCalls, minAbroadLongCalls, pastMonths);//Questa chiamata e un hack per la generazione di test: il metodo chiamato è vuoto
		sc.stop();

	}


}