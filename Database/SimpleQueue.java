/**
 * An ordered collection of items, where items are added to the rear
 * and removed from the front.
 */
public class SimpleQueue<E> implements QueueADT<E> {

    //TODO
    //You may use an expandable circular array or a chain of listnodes. 
    //You may NOT use Java's predefined classes such as ArrayList or LinkedList.
	private Listnode<E> head;  // pointer to the linked list of items in the stack
	private int numItems;
    public SimpleQueue() {
        head=null;
        numItems=0;
    }

    /**
     * Adds an item to the rear of the queue.
     * @param item the item to add to the queue.
     * @throws IllegalArgumentException if item is null.
     */
    public void enqueue(E item) {
      if (item==null) throw new IllegalArgumentException();
      Listnode<E> newNode=new Listnode <E> (item);
      if(head==null) {
    	  head=newNode;
      } else {
    	  Listnode<E> curr=head;
    	  while (curr.getNext() !=null)
    		  curr=curr.getNext();
    	  curr.setNext(newNode);
      } 
      numItems++;
    }

    /**
     * Removes an item from the front of the Queue and returns it.
     * @return the front item in the queue.
     * @throws EmptyQueueException if the queue is empty.
     */
    public E dequeue() {
    	 if (isEmpty()) 
    	        throw new EmptyQueueException();  // step 1
    	    E tmp = head.getData();              // step 2(a)
    	    head = head.getNext();              // step 2(b)
    	    numItems--;                           // step 3
    	    return tmp;                           // step 4                 
    }

    /**
     * Returns the item at front of the Queue without removing it.
     * @return the front item in the queue.
     * @throws EmptyQueueException if the queue is empty.
     */
    public E peek() {
    	if (isEmpty()) 
	        throw new EmptyQueueException();  // step 1
	    E tmp = head.getData();              // step 2(a)
	    return tmp;                           // step 4    
    }

    /**
     * Returns true iff the Queue is empty.
     * @return true if queue is empty; otherwise false.
     */
    public boolean isEmpty() {
        return numItems==0;
    }
    
    /**
     * Removes all items in the queue leaving an empty queue.
     */
    public void clear() {
    	
    	int temp = numItems;
		for(int i = 0;i < temp;i++){
			dequeue();
		}
    }

    /**
     * Returns the number of items in the Queue.
     * @return the size of the queue.
     */
    public int size() {
       return numItems;
    }
}
