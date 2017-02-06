import java.util.Iterator;
import java.util.*;


public class DoublyLinkedListIterator<E> implements Iterator<E> {
	private Listnode<E> curr;
	private int curPos;
	public DoublyLinkedListIterator(Listnode<E> head){
		curr = head;
		curPos = 0;
	}
	/**
	 * Returns true if the iteration has more elements.
	 * @return Returns true if the iteration has more elements.
	 */
	@Override
	public boolean hasNext() {
		if(curr.getNext()!=null) return true;
		return false;
	}
	/**
	 *Returns the next element in the iteration.
	 * @return Returns the next element in the iteration.
	 */

	@Override
	public E next() {
		if (!hasNext()) {
            throw new NoSuchElementException();
        }
		curr = curr.getNext();
        E result = curr.getData();
        curPos++;
        return result;
	}



	/**Throws an UnsupportedOperationException.
	 *@return Throws an UnsupportedOperationException.
	 */
	public void remove()throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	

	

}