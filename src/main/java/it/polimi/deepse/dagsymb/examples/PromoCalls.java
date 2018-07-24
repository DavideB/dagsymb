package it.polimi.deepse.dagsymb.examples;

/*GIO*///NB: Le parti annotate con il commento /*GIO*/ indicano le mie instrumentazioni del codice originale







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


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

@SuppressWarnings("resource")
public class PromoCalls {
    public void run(final int threshold, long minLocalLongCalls, long minAbroadLongCalls, int pastMonths){
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("CallsExample")/*.setMaster("local[4]")*/);
        long z = sc.parallelize(UserCallDB.getLast24HoursLocalCalls()).count();
        long localLongCalls = sc.parallelize(UserCallDB.getLast24HoursLocalCalls())
                .filter((Call o) ->{
                    System.out.println(o.getLength());
                    System.out.println(threshold);
                    System.out.println(o.getLength() > threshold);

                    return o.getLength() > threshold;
                }).count();

        long abroadLongCalls = sc.parallelize(UserCallDB.getLast24HoursAbroadCalls())
                .filter((Call o) -> o.getLength() > threshold).count();

        System.out.println("#PATH: 0");

        if (localLongCalls > minLocalLongCalls || abroadLongCalls > minAbroadLongCalls){
            System.out.println("#PATH: 1");
            sc.parallelize(UserCallDB.getLast24HoursLocalCalls()).map((Call o) -> o.setCost(o.getCost() * 0.5)).collect();
            sc.parallelize(UserCallDB.getLast24HoursAbroadCalls()).map((Call o) -> o.setCost(o.getCost() * 0.5)).collect();
        }

        if (localLongCalls > minLocalLongCalls) {
            System.out.println("#PATH: 2");

            sc.parallelize(UserCallDB.getCurrentMonthCalls()).map((Call o) -> o.setCost(o.getCost() * 0.95)).collect();

            for (int i = 1; i <= pastMonths; i++) {
                System.out.println("#PATH: 3");
                sc.parallelize(UserCallDB.getPastMonthCalls(i)).map((Call o) -> o.setCost(o.getCost() * 0.95)).collect();
            }
        }

        if (abroadLongCalls > minAbroadLongCalls){
            System.out.println("#PATH: 4");
            sc.parallelize(UserCallDB.getCurrentMonthCalls()).map((Call o) -> o.setCost(o.getCost() * 0.9)).collect();
        }

        System.out.println("#PATH: 5");
        sc.stop();

    }

}