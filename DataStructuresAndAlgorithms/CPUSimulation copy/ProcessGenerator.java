import java.util.Random;
//randomly generates processes with a given probability. At beginning
//of each time unit, whether a process will arrive is decided by the given probability. In addition,
//while generating a new process, both its priority and the required time units to finish the
//process are randomly generated within given ranges.
public class ProcessGenerator{
	private double probability;
	Random rnd = new Random();
	private double random;
	
	public ProcessGenerator (double probability) {
		this.probability = probability;
		 this.random = rnd.nextDouble();
	}

	public boolean query() { //use next double method then compare it to my probability to return true or false
		if(random < probability && random > 0) {
			return true;
		}else {
			return false;
		}
	}

	public Process getNewProcess(int currentTime, int maxProcessTime, int maxLevel) {//max level sets the process
		
		Process process = new Process( maxLevel, maxProcessTime, currentTime);
		
		return process;
	}

}
