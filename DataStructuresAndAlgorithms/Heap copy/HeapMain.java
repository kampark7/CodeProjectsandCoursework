
public class HeapMain {

	public static void main(String[] args) {
		int arr[] = {2,1,5,4,3,7,6,8,9};
		int n = arr.length;
		Heap h = new Heap(n,0);
		h.BuildHeap(h, arr, n);
		h.PrintHeap(h, n);
	}

}
