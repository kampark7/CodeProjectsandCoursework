import java.util.Arrays;

public class Heap {
	public int[] array;
	public int count;
	public int capacity;
	public int heap_type;
	
	public Heap (int capacity, int heap_type) {
		this.heap_type = heap_type;
		this.count = 0;
		this.capacity = capacity;
		this.array = new int[capacity];
	}
	
	public int Parent(int i) { // (i-1)/2 //Big O notation is 1
		if (i <= 0 || i >= this.count) {
			return -1;
		}
		return i - 1 / 2;
	}
	
	public int LeftChild(int i) { // 2*i
		int left = 2 * i  + 1;
		if(left >= this.count) {
			return -1;
		}
		return left;
	}
	
	public int RightChild(int i) {
		int right  = 2 * i + 2;
		if (right >= this.count) {
			return -1;
		}
		return right;
	}
	
	public void Heapify(int i) { //get the left child get the right child, if it is larger than the parent then swap //this is heapify down.
		int left, right, max, temp;
		left = LeftChild(i);
		right = RightChild(i);
		
		if( left != -1 && this.array[left] > this.array[i]) {
			max = left;
		}else {
			max = i;
		}
		if (right != -1 && this.array[right] > this.array[max] && this.array[right] >= this.array[left]) { 
			max = right;
		}
		
		if (max != i) {					//this is for it the max keeps moving and doesn't fix on the first try. It is checking for each parent and children
			temp = this.array[i];
			this.array[i] = this.array[max];
			this.array[max] = temp;
			
			Heapify(max);  //Calls heapify again to start the process over checking each child
		}
	}
	
	int DeleteMax() {
		return capacity;
		
	}
	
	public void ResizeHeap() { //write resize heap
		array = Arrays.copyOf(array,  array.length*2);
	}
	
	public void Insert(int data) {
		
	}
	
	public void BuildHeap (Heap h, int[] A, int n) {
		if (h == null) {
			return;
		}
		while( n > this.capacity) {
			h.ResizeHeap();
		}
		for (int i = 0; i < this.capacity; i++) { //copying array
			this.array[i] = A[i];
		}
		this.count = n;
		for (int i = (n - 1) / 2; i >= 0; i--) {
			Heapify(i);
		}
	}
	
	public void PrintHeap(Heap h, int n) {
		System.out.println("Array representation of Heap is:\n");
		for (int i = 0; i < n; i++) {
			System.out.println(this.array[i]);
		}
		System.out.println("\n");
	}
}
