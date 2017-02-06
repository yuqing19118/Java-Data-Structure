///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  UWmail.java
// File:             DoublyLinkedListIterator.java
// Semester:         CS367 Fall 2015
//
// Author:           Yuting Tan
// CS Login:         ytan
// Lecturer's Name:  Jim Skrentny
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Qing Yu (Sabrina)
// Email:            qyu37@wisc.edu
// CS Login:         sabrina
// Lecturer's Name:  Jim Skrentny
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * Implement the iterator for doubly-linkedlist with several functions.
 *
 * @author ytan
 */
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