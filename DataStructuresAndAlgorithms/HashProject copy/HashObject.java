public class HashObject<T> {
	public int probecount;
	public int dupcount;
	private T key;
	
	public HashObject(T key) { // passing in objects of type T for when you call it in the driver class
		this.probecount = 0;
		this.dupcount = 0;
		this.key = key;
	}
	public int getProbes() {
		return probecount;
	}
	public void setProbe(int probes) {
		 this.probecount = probes;
	}
	public int getDuplicate() {
		return dupcount;
	}
	public void incrementDuplicate() { //increment probes
		this.dupcount++;
	}
	public T getKey() {
		return this.key; 
	}
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {  
	    if(obj instanceof HashObject) { //checking if it is a valid call
	    	if (this.getKey().equals(((HashObject<T>) obj).getKey())) {  
		          return true;  
		      }
	    }
		return false;
	}
	public String toString() {
		return this.key.toString() + " " + this.dupcount + " " + this.probecount;
		//String Builder
	}

}
