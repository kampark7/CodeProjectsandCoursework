import java.io.RandomAccessFile;

public class BTree {

	private int degree; // minimum degree of the tree
	private BTreeNode root; // root node of the 
	private long currentOffset; // curent location in the Random Access File
	private int nodeSize; // size of the given node
	private long rootOffset; // offset of the root within the RAF
	private int maxChildren, maxKeys; // number of children and keys for each node
	private static int seqLength; // length of each sequence in the node
	private Cache<Long, BTreeNode> cache; // optional cache to use
	public final int STARTING_ADDRESS = 32; // allows space for metadata to be written when the tree is complete
	
	private String binFile; // name of the binary file
	private RandomAccessFile disk; // RAF for storing and accessing the data
	
	public BTree(int degree, String fileName, int seqSize) {
		if (degree == 0)
			this.degree = chooseOptimumDegree(4096);
		else
			this.degree = degree;
		root = null; // empty tree has no root
		rootOffset = STARTING_ADDRESS;
		currentOffset = rootOffset;
		nodeSize = numBytes();
		maxChildren = 2 * degree;
		maxKeys = (2 * degree) - 1;
		seqLength = seqSize;
		cache = null;
		
		binFile = fileName;
		disk = new RandomAccessFile(binFile, "rw");
	}
	
	public void insert(Long data) {
		TreeObject sequence = new TreeObject(data);
		
	}
	
	private void split() {
		// split a BTreeNode
	}
	
	
	
	
	private int calculateOptimumDegree(int diskBlocksize) {
		// The degree of the table is calculated based on the following if you are not using cash and you can hard code this:
		degree = 103;  //4 4x + 4(x+) = 4096, x = 205, for 2t-1 = 205, t = 103
		// If user selects cash then the degree is an input take from argument 
		
		
	int b = diskBlocksize; // we will use 4096
	int meta = 200; // size of metadata per TreeNode
	int k = 8; // 64 bit Long object
	int p = 8; // size of pointer
	int m = Math.floor((b + k - (meta + p)) / (k + p));
	if  (m % 2 == 0)
		return m / 2;
	else
		return (m - 1) / 2;
}
	
	private int numBytes() {
		int numBytes = 0;
		// magical things
		return numBytes;
	}
	
	
	
}