package it.polimi.deepse.dagsymb.examples;
import org.apache.hadoop.conf.Configuration;
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
 * Action 'collect' is used instead of action 'saveAstextFile' for the sake of simplicity
 *
 */


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

@SuppressWarnings("resource")
public class PromoCalls {
    public void run(final int threshold, long minLocalLongCalls, long minAbroadLongCalls, int pastMonths, int last24HLocalCallsLength, int last24HLocalCallsSize, int last24HAbroadCallsLength, int last24HAbroadCallsSize, int MonthCallsLength, int MonthCallsSize){
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("CallsExample")/*.setMaster("local[4]")*/); Configuration conf = sc.hadoopConfiguration(); //conf.set("fs.defaultFS","hdfs://localhost:9000");
        UserCallDB.addCallsToLast24HoursLocalCalls(sc, last24HLocalCallsLength, last24HLocalCallsSize);
        UserCallDB.addCallsToLast24HoursAbroadCalls(sc, last24HAbroadCallsLength, last24HAbroadCallsSize);
        UserCallDB.addCallsToMonthCalls(sc, MonthCallsLength, MonthCallsSize);
        long z = sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getLast24HoursLocalCalls()).count();
        long localLongCalls = sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getLast24HoursLocalCalls())
                .filter((String o) -> { 
                	String[] ss = o.split(" "); //("\\s+")
                    System.out.println(ss[2]);
                    System.out.println(threshold);
                    System.out.println(Integer.parseInt(ss[2]) > threshold);
                    return Integer.parseInt(ss[2]) > threshold;
                }).count();

        long abroadLongCalls = sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getLast24HoursAbroadCalls())
                .filter((String o) -> { String[] ss = o.split(" "); return Integer.parseInt(ss[2]) > threshold;}).count();

        System.out.println("#PATH: 0");

        if (localLongCalls > minLocalLongCalls || abroadLongCalls > minAbroadLongCalls){
            System.out.println("#PATH: 1");
            sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getLast24HoursLocalCalls()).map((String o) -> { String[] ss = o.split(" "); return ss[0]+" "+ss[1]+ss[2]+" "+Double.parseDouble(ss[3]) * 0.5; }).collect();
            sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getLast24HoursAbroadCalls()).map((String o) -> { String[] ss = o.split(" "); return ss[0]+" "+ss[1]+ss[2]+" "+Double.parseDouble(ss[3]) * 0.5; }).collect();
        }

        if (localLongCalls > minLocalLongCalls) {
            System.out.println("#PATH: 2");

            sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getCurrentMonthCalls()).map((String o) -> { String[] ss = o.split(" "); return ss[0]+" "+ss[1]+ss[2]+" "+Double.parseDouble(ss[3]) * 0.95; }).collect();

            for (int i = 1; i <= pastMonths; i++) {
                System.out.println("#PATH: 3");
                sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getPastMonthCalls(i)).map((String o) -> { String[] ss = o.split(" "); return ss[0]+" "+ss[1]+ss[2]+" "+Double.parseDouble(ss[3]) * 0.95; }).collect();
            }
        }

        if (abroadLongCalls > minAbroadLongCalls){
            System.out.println("#PATH: 4");
            sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getCurrentMonthCalls()).map((String o) -> { String[] ss = o.split(" "); return ss[0]+" "+ss[1]+ss[2]+" "+Double.parseDouble(ss[3]) * 0.95; }).collect();
        }

        System.out.println("#PATH: 5");
        sc.stop();

    }

    public void run(final int threshold, long minLocalLongCalls, long minAbroadLongCalls, int pastMonths){
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("CallsExample")/*.setMaster("local[4]")*/); Configuration conf = sc.hadoopConfiguration(); //conf.set("fs.defaultFS","hdfs://localhost:9000");
        long z = sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getLast24HoursLocalCalls()).count();
        long localLongCalls = sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getLast24HoursLocalCalls())
                .filter((String o) -> { 
                	String[] ss = o.split(" "); //("\\s+")
                    System.out.println(ss[2]);
                    System.out.println(threshold);
                    System.out.println(Integer.parseInt(ss[2]) > threshold);
                    return Integer.parseInt(ss[2]) > threshold;
                }).count();

        long abroadLongCalls = sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getLast24HoursAbroadCalls())
                .filter((String o) -> { String[] ss = o.split(" "); return Integer.parseInt(ss[2]) > threshold;}).count();

        System.out.println("#PATH: 0");

        if (localLongCalls > minLocalLongCalls || abroadLongCalls > minAbroadLongCalls){
            System.out.println("#PATH: 1");
            sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getLast24HoursLocalCalls()).map((String o) -> { String[] ss = o.split(" "); return ss[0]+" "+ss[1]+ss[2]+" "+Double.parseDouble(ss[3]) * 0.5; }).collect();
            sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getLast24HoursAbroadCalls()).map((String o) -> { String[] ss = o.split(" "); return ss[0]+" "+ss[1]+ss[2]+" "+Double.parseDouble(ss[3]) * 0.5; }).collect();
        }

        if (localLongCalls > minLocalLongCalls) {
            System.out.println("#PATH: 2");

            sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getCurrentMonthCalls()).map((String o) -> { String[] ss = o.split(" "); return ss[0]+" "+ss[1]+ss[2]+" "+Double.parseDouble(ss[3]) * 0.95; }).collect();

            for (int i = 1; i <= pastMonths; i++) {
                System.out.println("#PATH: 3");
                sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getPastMonthCalls(i)).map((String o) -> { String[] ss = o.split(" "); return ss[0]+" "+ss[1]+ss[2]+" "+Double.parseDouble(ss[3]) * 0.95; }).collect();
            }
        }

        if (abroadLongCalls > minAbroadLongCalls){
            System.out.println("#PATH: 4");
            sc.textFile(conf.get("fs.defaultFS") + "/" + UserCallDB.getCurrentMonthCalls()).map((String o) -> { String[] ss = o.split(" "); return ss[0]+" "+ss[1]+ss[2]+" "+Double.parseDouble(ss[3]) * 0.95; }).collect();
        }

        System.out.println("#PATH: 5");
        sc.stop();

    }

}