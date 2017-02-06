///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  UWmail.java
// File:             DoublyLinkedList.java
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
 * Implement doubly-linked list with several functions.
 *
 * @author ytan
 */
import java.util.Iterator;

public class DoublyLinkedList<E> implements ListADT<E>{
	private Listnode<E> head; 
	private int numItems;
	/**
	 * Constructs a new list node with no links to its next or previous node.
	 * @param data the data to be stored in this node
	 */
	public DoublyLinkedList() {
		head = new Listnode<E>(null);
		numItems = 0;
	}
	/**
	 * Adds item to the end of the List. 
	 * @param item the item to be added in the list
	 * @throws IllegalArgumentException if item is null 
	 */
	public void add(E item){ 	
		if(item == null)throw new IllegalArgumentException();
		Listnode<E> newnode = new Listnode<E>(item);
		Listnode<E> curr = head;
		while(curr.getNext()!= null){
			curr = curr.getNext();
		}
		curr.setNext(newnode);
		newnode.setPrev(curr);
		numItems++;
	}
	/**
	 * Adds item at position pos in the List, moving the items originally
	 * in positions pos through size() - 1 one place to the right to make room. 
	 * @param pos the position to be added in the list
	 * @param item the item to be added in the list
	 * @throws IllegalArgumentException if item is null 
	 * @throws IndexOutOfBoundsException if pos is less than 0 or greater 
	 * than size()
	 */
	public void add(int pos,E item){ 	
		if(item == null)throw new IllegalArgumentException();
		if(pos < 0 ||pos >size()) throw new IndexOutOfBoundsException();
		Listnode<E> newnode = new Listnode<E>(item);
		Listnode<E> curr = head;
		Listnode<E> oldnode;
		if(pos == 0){
			head = newnode;
			newnode.setNext(curr);
			curr.setPrev(newnode);
		}
		else{
			int index = 0;
			while(curr.getNext()!= null && index != pos - 1 ){
				curr = curr.getNext();
				index++;
			}
			oldnode = curr.getNext();
			curr.setNext(newnode);
			newnode.setPrev(curr);
			newnode.setNext(oldnode);
			oldnode.setPrev(newnode);
		}
		numItems++;
	}
	/**
	 * Returns true iff item is in the List (i.e., there is an item x in
	 * the List such that x.equals(item)) 
	 * @param item the item to be checked
	 */
	public boolean contains(E item){
		if(item == null)throw new IllegalArgumentException();
		Iterator<E> itr = new DoublyLinkedListIterator<E>(head);
		if(itr.hasNext()){
			if (itr.next().equals(item)){
				return true;
			}	
		}
		return false;
	}
	/**
	 * Returns the item at position pos in the List. 
	 * @return the item at position pos in the List
	 */
	public E get(int pos) {
		if(pos < 0 ||pos > numItems) throw new IndexOutOfBoundsException();
		Iterator<E> itr = new DoublyLinkedListIterator<E>(head);
		int index = 0;
		E result =null;
		if(itr.hasNext() && index!=pos){
			result = itr.next();
			index++;
		}
		return result;
	}
	/**
	 * Returns true iff the List is empty. 
	 * @return true iff the List is empty.
	 */
	public boolean isEmpty(){
		if(numItems==0){
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Removes and returns the item at position pos in the List, moving 
	 * the items originally in positions pos+1 through size() - 1 one 
	 * place to the left to fill in the gap.
	 * @return true iff the List is empty.
	 */
	public E remove(int pos){
		if(pos < 0 ||pos >size()) throw new IndexOutOfBoundsException();
		Listnode<E> curr = head;
		Listnode<E> oldnode;
		Listnode<E> removedNode;
		int index = 0;
		while(curr.getNext()!= null && index != pos - 1 ){
			curr = curr.getNext();
			index++;
		}
		removedNode = curr.getNext();
		oldnode = curr.getNext().getNext();
		curr.setNext(oldnode);
		oldnode.setPrev(curr);
		numItems--;
		return removedNode.getData();
	}
	/**
	 * 	Return the number of items in the List.
	 * @return the number of items in the List
	 */
	public int size(){
		return numItems;
	}
	/**
	 * Returns an Iterator<E>, per the Iterable<E> interface. 
	 * @return  an Iterator<E> 
	 */
	public Iterator<E> iterator(){
		return new DoublyLinkedListIterator<E>(head);
	}
}

