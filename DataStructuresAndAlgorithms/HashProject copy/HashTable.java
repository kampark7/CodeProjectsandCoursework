
public class HashTable<T> {
	public int sizeOfTable, sizeOfList;
	public int dupcount;
	public int totalprobes;
	public int h1;
	public int h2;
	HashObject<T>[] arr;
	
	@SuppressWarnings("unchecked")
	public HashTable(int tableSize) {
		this.arr = (HashObject<T>[])(new HashObject<?>[tableSize]);
		this.sizeOfTable = tableSize;
		this.dupcount = 0;
		this.totalprobes = 0;
	}
	public int primaryHash(HashObject<T> key) {
		h1 = key.getKey().hashCode() % sizeOfTable;
		if (h1 < 0) {
			h1 += sizeOfTable;
		}
		return h1;
	}
	public int secondaryHash(HashObject<T> key) {
		h2 = 1 + (key.getKey().hashCode() % (sizeOfTable - 2));
		if (h2 < 0) {
			h2 += (sizeOfTable -2);
		}
		return h2;
	}
	public int linearHash(HashObject<T> obj) {
		int localprobe = 0;
		int hash = primaryHash(obj);
		for(int i = 0; i < sizeOfTable; i++) {
			
			int x = (hash+ i) % sizeOfTable;

			localprobe++;
			if(this.arr[x] == null) {
				arr[x] = obj; //adds the unique
				this.arr[x].setProbe(localprobe);
				totalprobes += localprobe;
				return x;
			}else if(this.arr[x].equals(obj)){
				this.arr[x].incrementDuplicate();
				this.dupcount++;
				return -1; //didn't insert
			}
		}
		return -1;
	}
	public int DoubleHash(HashObject<T> obj) {
		int localprobe = 0;
		int hash = primaryHash(obj);
		int hashTwo = secondaryHash(obj); 
		for(int i = 0; i < sizeOfTable; i++) {
			
			int x = (hash + (i * hashTwo)) % sizeOfTable;

			localprobe++;
			if(this.arr[x] == null) {
				arr[x] = obj; //adds the unique
				this.arr[x].setProbe(localprobe);
				totalprobes += localprobe;
				return x;
			}else if(this.arr[x].equals(obj)){
				this.arr[x].incrementDuplicate();
				this.dupcount++;
				return -1; //didn't insert
			}
		}
		return -1;
	}
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) { //check in case the array is null then it will skip it
				str.append("table[");
				str.append(i);
				str.append("]: ");
				str.append(arr[i].toString());
				str.append("\n");
			}
		}
		return str.toString();
	}
	
	public int getDuplicateValues() {
		return this.dupcount;
	}
	public int getProbeValues() {
		return this.totalprobes;
	}
	
	
}
