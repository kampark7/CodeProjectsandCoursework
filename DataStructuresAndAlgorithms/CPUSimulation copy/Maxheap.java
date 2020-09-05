import java.util.ArrayList;
import java.util.NoSuchElementException;

// defines a max-heap. Each node in the max-heap contains a process.
@SuppressWarnings("hiding")
public class Maxheap<Process extends Comparable<Process>> {
	public ArrayList<Process> array;
	public int capacity;
	public int heap_type;

	public Maxheap () {
		array = new ArrayList<Process>();
	}

	public Process Parent(int childIndex) { // (i-1)/2 //Big O notation is 1
		if (childIndex <= 0 || childIndex >= array.size()) {
			return null;
		}
		return array.get((childIndex - 1)/ 2);
	}
	
	public Process LeftChild(int parentIndex) { // 2*i
		int left = (2 * parentIndex) + 1;
		if(left >= array.size()) {
			return null;
		}
		return array.get(left);
	}

	public Process RightChild(int parentIndex) {
		int right  = (2 * parentIndex) + 2;
		if (right >= array.size()) {
			return null;
		}
		return array.get(right);
	}

	public void HeapifyUp(int i) { //heapify up	
		int parent = ((i-1)/2);
		Process tmp = array.get(parent);
		
		if (array.get(i).compareTo(array.get(parent))==1){
			array.set(parent, array.get(i));
			array.set(i, tmp);
			this.HeapifyUp(parent);
		}
	}
	
	public void HeapifyDown(int i) { //get the left child get the right child, if it is larger than the parent then swap //this is heapify down.
		//have to access the parent or the child not both of them 
		int left, right, head;
		left = (2*i)+1;
		right = (2*i)+2;
		head = i;

		if(left < array.size() && array.get(left).compareTo(array.get(head)) == 1) { //use compareTo
			head = left;
		}
		if (right < array.size() && array.get(right).compareTo(array.get(head)) == 1) { 
			head = right;
		}

		if (head != i) { //this is for it the max keeps moving and doesn't fix on the first try. It is checking for each parent and children
			Process tmp = array.get(i);
			array.set(i, array.get(head));
			array.set(head, tmp); //set head at the index of max

			HeapifyDown(head);  //Calls heapify again to start the process over checking each child
		}
	}

	public Process Extract() {
		Process max;
		if (array.isEmpty()) {
			throw new NoSuchElementException("No element in the list");
		}
		max = array.get(0);
		array.set(0, array.get(array.size() - 1));
		array.remove(array.size()-1);
		if (array.size() != 0) {
			this.HeapifyDown(0);
		}
		return max;
	}

	public void Insert(Process data) {
		array.add(data);
		HeapifyUp(array.size()-1);	
	}
	public ArrayList<Process> getList() {
		return array;
	}
	public int Size(){
		return array.size();
	}

}
