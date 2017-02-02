/**
 * An ordered collection of items, where items are both added
 * and removed from the top.
 * @author CS367
 */
public class SimpleStack<E> implements StackADT<E> {

	//TODO
	//You may use an expandable array or a chain of listnodes.
	//You may NOT use Java's predefined classes such as ArrayList or LinkedList.
	private static final int INITSIZE = 10;//initial array size
	private E[] items;
	private int numItems;


	@SuppressWarnings("unchecked")
	public SimpleStack() {
		//TODO constructor of simple stack class.
		items = (E[])new Object [INITSIZE];
		numItems = 0;
	}
	public E[] get(){
		return items;
	}

	/**
	 * Adds item to the top of the stack.
	 * @param item the item to add to the stack.
	 * @throws IllegalArgumentException if item is null.
	 */
	public void push(E item) {
		//TODO
		if(item == null)throw new IllegalArgumentException();
		if(items.length==numItems)expandArray();
		items[numItems] = item;
		numItems++;
		
	}

	/**
	 * Removes the item on the top of the Stack and returns it.
	 * @return the top item in the stack.
	 * @throws EmptyStackException if the stack is empty.
	 */
	public E pop() {
		//TODO
		if (numItems == 0) {
			throw new EmptyStackException();
		}
		else {
			numItems--;
			return items[numItems];
		}
	}

	/**
	 * Returns the item on top of the Stack without removing it.
	 * @return the top item in the stack.
	 * @throws EmptyStackException if the stack is empty.
	 */
	public E peek() {
		//TODO
		if (numItems == 0) {
			throw new EmptyStackException();
		}
		else {
			return items[numItems-1];
		}
	}

	/**
	 * Returns true iff the Stack is empty.
	 * @return true if stack is empty; otherwise false.
	 */
	public boolean isEmpty() {
		//TODO
		return numItems == 0;
	}

	/**
	 * Removes all items on the stack leaving an empty Stack. 
	 */
	public void clear() {
		int temp = numItems;
		for(int i = 0;i < temp;i++){
			pop();
		}
	}

	/**
	 * Returns the number of items in the Stack.
	 * @return the size of the stack.
	 */
	public int size() {
		//TODO
		return numItems;
	}
	@SuppressWarnings("unchecked")
	private void expandArray(){
		E[] shadow = (E [])new Object[items.length*2];
		for(int i = 0; i < items.length;i++ ){
			shadow[i] = items[i];
		}
		items = shadow;
	}
}
