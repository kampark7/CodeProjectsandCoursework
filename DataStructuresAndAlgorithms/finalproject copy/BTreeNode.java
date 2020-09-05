/**
 * @author brendenhubele
 * class BTreeNode
 * This class's purpose is to store BTree Objects
 */
public class BTreeNode {
    TreeObject[] keys;   // An array of keys 
    int degree;          // Minimum degree (defines the range for number of keys) 
    BTreeNode[] Children;// An array of child pointers 
    int numKeys;         // Current number of keys 
    boolean isLeaf;      // Is true when node is leaf. Otherwise false 


    public BTreeNode(int t, boolean l){
        degree = t;
        isLeaf = l;
        numKeys = 0;
        Children = new BTreeNode[2*degree];
        keys = new TreeObject[2*degree];
    }

    public void insertTreeObject(TreeObject t){
        keys[numKeys] = t;
        numKeys++;
    }

    

}