import java.util.Random;

// defines a process. You need to implement the compareTo method in this class.
// Each process has a priority level, time remaining to finish, and arrival time
public class Process implements Comparable<Process>{
	private int priorityLevel;
	private int timeLeftFinish;
	private int arrivalTime;
	private int timeNotProcessed;
	
	Random rand = new Random();
	public Process (int priorityLevel, int timeLeftFinish, int arrivalTime) {
		this.priorityLevel = rand.nextInt(priorityLevel)+1;
		this.timeLeftFinish = rand.nextInt(timeLeftFinish)+1;
		this.arrivalTime = arrivalTime;
		this.timeNotProcessed = 0;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getPriority() {
		return priorityLevel;
	}

	public void resetTimeNotProcessed() {
		timeNotProcessed = 0;
	}

	public int getTimeRemaining() {
		return timeLeftFinish;
		
	}

	public boolean finish() {
		return timeLeftFinish == 0;
		
	}

	public void reduceTimeRemaining() {
		timeLeftFinish--;
		
	}
	public void incrementTimeNotProcessed() {
		timeNotProcessed++;
	}
	public int getTimeNotProcessed() { //new
		return timeNotProcessed;
	}
	
	public void incrementPriority() { //new
		priorityLevel++;
	}

	@Override
	public int compareTo(Process o) { //compares whatever is in the array list,which is a process
		if (this.priorityLevel > o.getPriority()) {
			return 1;
		}else if (this.priorityLevel < o.getPriority()) {
			return -1;
		}else {
			if (this.arrivalTime < o.getArrivalTime()) {
				return 1;
			}else if (this.arrivalTime > o.arrivalTime){
				return -1;
			}else {
				return 0;
			}
		}
	}

}
