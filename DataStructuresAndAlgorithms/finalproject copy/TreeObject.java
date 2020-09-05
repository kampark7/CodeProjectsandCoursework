
public class TreeObject<T> {
	public int degree;
	public int seqSize;
	public String fileName;
	private T key;
	
	public TreeObject(T key) { // passing in objects of type T for when you call it in the driver class
		this.degree = 0;
		this.seqSize = 0;
		this.key = key;
	}
	
	public T getKey() { //long object
		return this.key; 
	}
	public int getDegree() {
		return this.degree;
	}
	
	
}