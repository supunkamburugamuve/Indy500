package com.dsc.iu.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//calculates the total execution time it takes to supply an input and generate it's corresponding anomaly score
public class AnomalyLatency {
	
	public static void main(String[] args) {
		try {
			//File f = new File("/scratch_ssd/sahil/out.csv");
//			File f = new File("/Users/sahiltyagi/Desktop/out.csv");
			File f = new File("C:\\Users\\styagi\\Desktop\\hpcreport\\out_dixon.csv");
			PrintWriter pw = new PrintWriter(f);
			
//			BufferedReader rdr = new BufferedReader(new InputStreamReader(new FileInputStream("/scratch_ssd/sahil/executionTime.txt")));
//			BufferedReader rdr = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/sahiltyagi/Desktop/executionTime.txt")));
			BufferedReader rdr = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\styagi\\Desktop\\hpcreport\\exeutionTime8.txt")));
			Map<Integer, Long> exectimemap = new HashMap<Integer, Long>();
			String s;
			while((s=rdr.readLine()) != null) {
				exectimemap.put(Integer.parseInt(s.split(",")[0]), Long.parseLong(s.split(",")[2]));
			}
			rdr.close();
			
//			rdr = new BufferedReader(new InputStreamReader(new FileInputStream("/scratch_ssd/sahil/htmsample.txt")));
//			rdr = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/sahiltyagi/Desktop/htmsample.txt")));
			rdr = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\styagi\\Desktop\\hpcreport\\htmoutput8.txt")));
			
			while((s=rdr.readLine()) != null) {
				Integer index = Integer.parseInt(s.split(",")[0]);
				Long ts = Long.parseLong(s.split(",")[3]);
				//handle redundancy of partially running experiments causing mismatch of # records flushed to each file
				if(exectimemap.containsKey(index)) {
					//System.out.println(index + "," + (ts - exectimemap.get(index)));
					pw.println(index + "," + (ts - exectimemap.get(index)));
				}
			}
			
			System.out.println("generated the HTM execution latency report");
			pw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
