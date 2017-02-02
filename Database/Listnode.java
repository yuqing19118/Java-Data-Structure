/**
 * Implements the methods of listnode which are used in queue. 
 *
 * <p>Bugs: no bugs
 *
 * @author ytan
 */

	public class Listnode<E> {
		  //*** fields ***
		    private E data;
		    private Listnode<E> next;
		 
		  //*** constructors ***
		    // 2 constructors
		    public Listnode(E d) {
		        this(d, null);
		    }
		 
		    public Listnode(E d, Listnode<E> n) {
		        data = d;
		        next = n;
		    }
		     
		  //*** methods ***
		    // access to fields
		    public E getData() {
		        return data;
		    }
		 
		    public Listnode<E> getNext() {
		        return next;
		    }
		 
		    // modify fields
		    public void setData(E d) {
		        data = d;
		    }

		    public void setNext(Listnode<E> n) {
		        next = n;
		    }
		}

