package it.polimi.deepse.dagsymb.examples;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.commons.lang.StringUtils;
//import java.nio.file.Paths;

import java.io.*;
import java.net.URI;

public class Utils
{

	public static void reset() { _I = new Utils(); }
	private static Utils _I = new Utils();
	private Utils() {}

	public static List<Call> createRandomCallForCaller(String callerId, int callLength,  int size){
		List<Call> res = new ArrayList<>();

		while(size>0){
			res.add(new Call(callerId, UUID.randomUUID().toString(), callLength, callLength * 0.2));
			size--;
		}
		return res;

	}
	
	public static void createRandomCallForCaller(String filename, String callerId, int callLength, int size){
		
		JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("CallsExampleGenData")/*.setMaster("local[4]")*/);
		//Get configuration of Hadoop system
	    Configuration conf = sc.hadoopConfiguration();//new Configuration();
	    //conf.set("fs.defaultFS","hdfs://localhost:9000");
	    System.out.println("Connecting to -- "+conf.get("fs.defaultFS"));
	    
		try {
			URI uri = URI.create(conf.get("fs.defaultFS") + "/" + filename);
		    FileSystem fs = FileSystem.get(uri, conf);
		    OutputStream output = fs.create(new Path(filename));
		    System.out.println("Output: "+output.toString());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
	    
			for (int i=0; i<size; ++i) {
		      String line = callerId + " " + UUID.randomUUID().toString() + " " + callLength + " " + callLength * 0.2;
	      	  /*	      
		      String[] ss = {	
		    		  callerId,
		    		  UUID.randomUUID().toString(),
		    		  Integer.toString(callLength),
		    		  Double.toString(callLength * 0.2)
		    		  };
		      String line = StringUtils.join(ss, ' ');
      	 	  */	      
			  //System.out.println(line);
		      writer.write(line + "\n");
	        };
	        writer.flush();
	        writer.close();
	        fs.close();
	    } catch (IOException e) { 
	    	System.out.println(e.getMessage());
	    	};
	    sc.close();
	}
	
public static void createRandomCallForCaller(JavaSparkContext sc, String filename, String callerId, int callLength, int size){
		
		//JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("CallsExampleGenData")/*.setMaster("local[4]")*/);
		//Get configuration of Hadoop system
	    Configuration conf = sc.hadoopConfiguration();//new Configuration();
	    //conf.set("fs.defaultFS","hdfs://localhost:9000");
	    System.out.println("Connecting to -- "+conf.get("fs.defaultFS"));
	    
		try {
			URI uri = URI.create(conf.get("fs.defaultFS") + "/" + filename);
		    FileSystem fs = FileSystem.get(uri, conf);
		    OutputStream output = fs.create(new Path(filename));
		    System.out.println("Output: "+output.toString());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
	    
			for (int i=0; i<size; ++i) {
		      String line = callerId + " " + UUID.randomUUID().toString() + " " + callLength + " " + callLength * 0.2;
	      	  writer.write(line + "\n");
	        };
	        writer.flush();
	        writer.close();
	        fs.close();
	    } catch (IOException e) { 
	    	System.out.println(e.getMessage());
	    	};
	    //sc.close();
	}
}