package it.polimi.deepse.dagsymb.examples;

/*GIO*///NB: Le parti annotate con il commento /*GIO*/ indicano le mie instrumentazioni del codice originale

import it.polimi.deepse.dagsymb.stubs.SparkConf; /*GIO*///import org.apache.spark.SparkConf;
import it.polimi.deepse.dagsymb.stubs.JavaSparkContext; /*GIO*///import org.apache.spark.api.java.JavaSparkContext;
import it.polimi.deepse.dagsymb.stubs.Lambda1P;

import jbse.meta.Analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



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


@SuppressWarnings("resource")
public class PromoCallsMock {
  public void run(final int threshold, long minLocalLongCalls, long minAbroadLongCalls, int pastMonths){
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

	  System.out.println("0");

	  if (localLongCalls > minLocalLongCalls || abroadLongCalls > minAbroadLongCalls){
	  		System.out.println("1");
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
		  System.out.println("2");

		  sc.parallelize(UserCallDB.getCurrentMonthCalls()).map(new Lambda1P() {
			  @Override
			  public Object process(Object o) {
				  return ((Call) o).setCost(((Call) o).getCost() * 0.95); /*GIO*/// call -> call.setCost(call.getCost()*0.95)
			  }
		  }).collect();
		  
		  for (int i = 1; i <= pastMonths; i++) {
			  System.out.println("3");

			  sc.parallelize(UserCallDB.getPastMonthCalls(i)).map(new Lambda1P() {
				  @Override
				  public Object process(Object o) {
					  return ((Call) o).setCost(((Call) o).getCost() * 0.95); /*GIO*/// call -> call.setCost(call.getCost()*0.95)
				  }
			  }).collect();
		  }
	  }
	  
	  if (abroadLongCalls > minAbroadLongCalls){
		  System.out.println("4");
		  sc.parallelize(UserCallDB.getCurrentMonthCalls()).map(new Lambda1P() {
			  @Override
			  public Object process(Object o) {
				  return ((Call) o).setCost(((Call) o).getCost() * 0.9); /*GIO*/// call -> call.setCost(call.getCost()*0.9)
			  }
		  }).collect();
	  }

	  System.out.println("5");


	  /*GIO*//*try {
			Thread.sleep(1000*60*60);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	    
	  /*GIO*/run_driver(threshold, minLocalLongCalls, minAbroadLongCalls, pastMonths);//Questa chiamata e un hack per la generazione di test: il metodo chiamato è vuoto 
	  sc.stop();
	  
  }
	 

    /*GIO*: ALL THE FOLLOWING CODE IS INSTRUMENTATION */
	public Object jbseExecutionFlag;
	private List<Call>[] theInputDatasets = new List[] {
			new ArrayList<Call>(), new ArrayList<Call>(), new ArrayList<Call>(), new ArrayList<Call>(), new ArrayList<Call>(), 
			new ArrayList<Call>(), new ArrayList<Call>(), new ArrayList<Call>(), new ArrayList<Call>(), new ArrayList<Call>()
	};
	
	private void run_driver(int threshold, long minLocalLongCalls, long minAbroadLongCalls, int pastMonths){
		/* Comment the call below at test generation and execution time, use it for symbolic execution only*/
		//run_driver_CallThisAtSymbolicExecutionTimeOnly(threshold, minLocalLongCalls, minAbroadLongCalls, pastMonths);
	}
	private void run_driver_CallThisAtSymbolicExecutionTimeOnly(final int threshold, long minLocalLongCalls, long minAbroadLongCalls, int pastMonths){
		/*GIO*/Analysis.assume(threshold > 100);
		/*GIO*/Analysis.assume(minLocalLongCalls > 90);
		/*GIO*/Analysis.assume(minAbroadLongCalls > 25);
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

		sc.stop();
	}

	public List<Call> getDataset(int datasetId) {
		if (datasetId < 0 || datasetId >=  theInputDatasets.length) return null;
		else return theInputDatasets[datasetId];
	}

	private void addToDataset(int datasetId, String callerId, int length, int amount) {
		if (datasetId < 0 || datasetId >=  theInputDatasets.length) return;
		if (amount <= 0) return;
		for (int i= 0; i < amount; i++) {
			theInputDatasets[datasetId].add(new Call(callerId, UUID.randomUUID().toString(), length, length * 0.2));
		}
	}

	public void addToDatasetLast24HoursLocalCalls(int length, int amount) {
		addToDataset(0, "callerId", length, amount);
	}
	public void addToDatasetLast24HoursAbroadCalls(int length, int amount) {
		addToDataset(1, "callerId", length, amount);
	}
	public void addToDatasetCallsOfPastMonth(int length, int amount) {
		addToDataset(2, "callerId", length, amount);
	}

}