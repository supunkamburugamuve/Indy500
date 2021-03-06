package com.dsc.iu.streaming;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

public class AnomalyDetectionTest {
	public static void main(String[] args) {
		
		System.out.println("going to start indycar topology");
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("eRPlog", new TelemetryTestSpout());
		//builder.setBolt("htmbolt", new HTMBolt(), 2).shuffleGrouping("eRPlog");
		builder.setBolt("htmbolt", new HTMBolt(), 1).shuffleGrouping("eRPlog");
		builder.setBolt("sink", new SinkBolt()).shuffleGrouping("htmbolt");
		
		Config config = new Config();
		config.setNumWorkers(2);
		config.put(Config.WORKER_CHILDOPTS, "-Xmn32768m");
		config.put(Config.WORKER_HEAP_MEMORY_MB, 32768);
		
//		LocalCluster cluster = new LocalCluster();
//		cluster.submitTopology("indy500", config, builder.createTopology());
//		try {
//			Thread.sleep(1000000);			//running topology for 1000 seconds in local mode
//		} catch(InterruptedException e) {
//			e.printStackTrace();
//		}
//		cluster.shutdown();
		
		StormTopology stormTopology = builder.createTopology();
	    try {
			StormSubmitter.submitTopology("indy500", config, stormTopology);
			System.out.println("job command submitted");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
