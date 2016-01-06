package testingOfMinDistance;

import static org.junit.Assert.*;

import java.lang.management.*;

import org.junit.Test;

public class SpeedTest {
	
	@Test
	public void test() {
		long startUserTimeNano   = getUserTime( );
		for(int i = 0; i< 1000000;i++){
			//DistanceTest.test();
			DistanceTest3.test();
		}
		
		System.out.println("Time it took in ms: "+(getUserTime( ) - startUserTimeNano)/1000000);
	}
	

	/** Get user time in nanoseconds. */
	public long getUserTime( ) {
	    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        bean.getCurrentThreadUserTime( ) : 0L;
	}
}

