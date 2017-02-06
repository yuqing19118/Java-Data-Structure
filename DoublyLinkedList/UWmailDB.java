///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  UWmail.java
// File:             UWmailDB.java
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
 * Construct the database for UWmail and some descriptors for UWmail.
 *
 * @author ytan
 */
import java.util.Iterator;

public class UWmailDB {
	//data field
	DoublyLinkedList<Conversation> inbox;
	DoublyLinkedList<Conversation> trash;


	/**
	 * Constructs an empty UWmailDB.
	 */
	public UWmailDB() {
		inbox = new DoublyLinkedList<Conversation> ();
		trash = new DoublyLinkedList<Conversation> ();

	}
	/**
	 * Return the number of conversations in the inbox.
	 * @return the number of conversations in the inbox.
	 */
	public int size() {
		return inbox.size();
	}
	/**
	 * Determines the correct email conversation to add e to, and does so. 
	 * If there is no such conversation, it must add a new one to the inbox.
	 * @param e email to be added
	 */
	//Pre-condition: e will be added to a conversation for which it is the 
	//oldest email.
	//In other words, you can simply add it to the beginning of the list, 
	//if the list is sorted in chronological order.
	//Also, the messageID of e is guaranteed to be included in the References 
	//field of all emails in the conversation that it belongs in.
	public void addEmail(Email e) {
		if(inbox.size()==0){
			Conversation newCon = new Conversation(e);
			inbox.add(newCon);
		}
		Iterator <Conversation> itrCon = inbox.iterator();
		boolean match = false;// whether email is added to the existed conversation
		Conversation list = null;
		while(itrCon.hasNext()){
			list = itrCon.next();
			Email email = list.get(0);
			if(e.getMessageID().equals(email.getReferences())){
				list.add(e);
				match = true;
			}	
		}
		// if here is no conversation for email e
		if(!match){
			Conversation currCon=new Conversation (e);
			inbox.add(currCon);
		}
	}
	/**
	 * Returns a DoublyLinkedList of Conversation's, representing the 
	 * conversations in the inbox.
	 * @return the conversations in the inbox.
	 */
	public ListADT<Conversation> getInbox() {
		return inbox;
	}
	/**
	 * Returns a DoublyLinkedList of Conversation's, representing the 
	 * conversations in the trash.
	 * @return the conversations in the trash.
	 */
	public ListADT<Conversation> getTrash() {
		return trash;
	}
	/**
	 * Moves the conversation at the nth index of the inbox to the trash.
	 * @param idx the index of the conversation in the inbox.
	 */
	public void deleteConversation(int idx) {
		trash.add(inbox.remove(idx));
	}

}
