/**
 * @author Tobias Neumann
 */
package testingOfMinDistance;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import diGraph.DiGraph;
import diGraph.DiGraphNode;

public class TestU {
	
	//<<<<<< PARAMETERS:
	static int amount=10;	//how many different graphs will be generated
	static int node=20000;	//amount of nodes of each generated graph
	static int measur=10;	//amount of measures per graph
	static int range=50;	//set maximum range (in number of nodes) the nodes can be connectet with
	static int conMin=2,conMax=4;	//set min/max of possible connection from source node to target node
	static double oneWay=0.2d;	//set probability that this connection will be an one way connection
	static boolean deb=true;	//if true, each measure print additional log information in console
	//>>>>>>
	
	public static void main(String[] args) {
		long path=0,time=0;
		for(int i=0;i<amount;i++){
			System.out.println("###> Measure "+(i+1)+" of "+amount);
			long[] results=Measure(node,measur,deb);
			path+=results[0];
			time+=results[1];
		}
		path/=amount;
		time/=amount;
		System.out.println("--------");
		System.out.println("shortest route length: "+path+" edges");
		System.out.println("calculate time: "+(float)(time/1000000f)+"ms");
		System.out.println("finished!");
	}
	
	public static long[] Measure(int nodes,int meas,boolean debug){
		int numNodes=nodes;	//total number of nodes to generate
		if(debug) System.out.println("generate "+numNodes+" nodes...");
		//generate nodes with Integer as key object
		Integer[] keys=new Integer[numNodes];
		for(int i=0;i<numNodes;i++){
			keys[i]=new Integer(i);
		}
		
		//generate edges
		if(debug) System.out.println("generate edges...");
		boolean[][] adj=new boolean[numNodes][numNodes];
		long edges=0;
		for(int i=0;i<numNodes;i++){
			int con=(int)(Math.random()*(conMax-conMin+1))+conMin;	//randomize connection value with the min/max values
			for(int c=0;c<con;c++){
				int rangeRandom=(int)(Math.random()*(range+1));	//randomize range from 0 to the set range value
				for(int r=-rangeRandom;r<=rangeRandom;r++){	//relative from current node position: interval [-range / +range]
					int pos=i+r;	//calculate absolute position the current node have to connected with
					if(pos>=0 && pos<numNodes && !adj[pos][i]){	//set edge only then absolute position is accurate and no one way edge is set from the other side (or backwards)
						if(!adj[i][pos]){
							adj[i][pos]=true;
							edges++;
						}
						if(Math.random()>oneWay && !adj[pos][i]){	//set edge only when the random generated double is greater then the set limit
							adj[pos][i]=true;
							edges++;
						}
					}
				}
			}
		}
		if(debug) System.out.println(edges+" edges generated!");
		
		//building graph with it's constructor
		if(debug) System.out.println("building graph...");
		DiGraph G=new DiGraph(keys,adj);

		int measures=meas;	//measure serveral times to get better time
		if(debug) System.out.println("start "+measures+" measurements...");
		int invalidM=measures/5;	//first measures are not valid
		long time=0,path=0;
		for(int i=0;i<measures;i++){
			int uN=(int)(Math.random()*(numNodes));	//randomize start nodes
			int vN=(int)(Math.random()*(numNodes));
			int wN=(int)(Math.random()*(numNodes));
			DiGraphNode u=G.find(new Integer(uN));
			DiGraphNode v=G.find(new Integer(vN));
			DiGraphNode w=G.find(new Integer(wN));
			G.resetGraph();	//reset the values of the nodes of the graph, so the path can be calculated from initialized state
			//IF YOU HAVE NO reset() METHOD, THEN SET THE 'measur' PARAMETER TO 1 !!!
			long start=getTime();
			int pathD=G.minDistance(u,v,w);
			long stop=getTime();
			if(i>=invalidM-1){
				path+=pathD;
				time+=(stop-start);
			}
			if(debug){
				if(i==invalidM){
					System.out.print("!");
				}else{
					System.out.print(".");
				}
				if((i+1)%128==0){
					System.out.println(i+1);
				}
			}
		}
		if(debug) System.out.println("finish measurements...");
		path/=(measures-invalidM);
		time/=(measures-invalidM);
		System.out.println(">path: "+path+" edges");
		System.out.println(">time: "+(float)(time/1000000f)+"ms\n");
		
		long[] result={path,time};
		return result;
	}
	
	public static long getTime(){
		//time in milli seconds:
//		return (long)System.currentTimeMillis();
		//time in nano seconds:
		ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        bean.getCurrentThreadCpuTime( ) : 0L;
	}
}
