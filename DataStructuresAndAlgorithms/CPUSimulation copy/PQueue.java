//defines a priority queue using a max-heap
public class PQueue {
	Maxheap<Process> max = new Maxheap<Process>();

	public void enPQueue(Process next) { //new process call insert on the heap heapify up or heapify down
		max.Insert(next);
	}

	public boolean isEmpty() {
		if (max.Size() == 0) {
			return true;
		}else {
			return false;
		}
	}

	public Process dePQueue() { //get the highest priority out of the list and if the same level priority the oldest time
		return max.Extract();
	}

	public void update(int timeToIncrementLevel, int maxLevel) { //for loop to get the list then a for
		for (int i = 0; i < max.array.size(); i++) { 
			max.array.get(i).incrementTimeNotProcessed();
			if (max.array.get(i).getTimeNotProcessed() >= timeToIncrementLevel) {
				max.array.get(i).resetTimeNotProcessed();
				if(max.array.get(i).getPriority() < maxLevel) {
					max.array.get(i).incrementPriority(); //increment
					max.HeapifyUp(i);
				}
			}
		}

	}

}
