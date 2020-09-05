import java.util.LinkedList;
/**
 * Linked List implementation of a Cache.
 * 
 * @author Kamryn Parker
 * CS 321 Section 3
 *
 * @param <E> type to store
 */
public class Cache<E>{
	private LinkedList<Object> singleList;
	private int size;
	private int maxSize;

	/**
	 * Creates a cache list of a given size and uses Java Linked List functionality
	 * @param size
	 */
	public Cache(int size) {
		singleList = new LinkedList<Object>();//create a cache argument for certain size //argument processing in the test class later //has to be max size decided by user
		this.maxSize = size;
		size = 0;
	}
	/**
	 * Gets the object in the list to check and see if it is in the list.
	 * @param element
	 * @return
	 */
	public boolean getObject(E element) { //really just checking if the list contains that object
		return singleList.contains(element);
	}
	/**
	 * Adds object to the list and checks size to determine if list is full. If list is full then it takes the last element off the end of the list.
	 * Adds to the front of the list.
	 * @param element
	 */
	public void addObject(E element) {
		if ( size >= maxSize) { //if the list size is larger than the actual allowed size then you remove an object
			singleList.removeLast();
			size--;
		}
		singleList.addFirst(element);
		size++;

	}
	/**
	 * Removes an object from the list.
	 * @param element
	 */
	public void removeObject(E element) {
		singleList.remove(element);
		size--;
	}
	/**
	 * Moves an object to the front of the list. It first removes it and then adds it. No incrementing in size because it stays the same 
	 * it is just moving the object around in the list.
	 * @param element
	 */
	public void moveObject(E element) { //moving the object to the front of the list
		singleList.remove(element); //remove first
		singleList.addFirst(element); //then add it back
	}
	/**
	 * Clears the cache lsit
	 * @param element
	 */
	public void clearCache(E element) {
		singleList.clear();
		size = 0;
	}
	/**
	 * Checks to see if it is empty
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}
}
