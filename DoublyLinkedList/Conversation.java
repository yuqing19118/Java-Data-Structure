import java.util.Iterator;

public class Conversation implements Iterable<Email> {
	//data field
	private DoublyLinkedList<Email> list;
	private int curPos = -1;
	/**
	 * Constructs a Conversation with the given email as the first 
	 * and only email.
	 * @param e the first email in this conversation
	 */
	public Conversation(Email e) {
		list = new DoublyLinkedList<Email>();
		list.add(e);
		curPos = 0;
	}

	/**
	 * Returns the index of the email which the user had in focus last time 
	 * they viewed this conversation. If the Conversation has not been viewed 
	 * yet, then return the last email in the conversation (the newest).
	 * @return the index of the email
	 */
	public int getCurrent() {
		if(curPos>=0)return curPos;
		else return size()-1;
	}

	/**
	 * Move the pointer to the last viewed email back one, i.e. the user is 
	 * viewing a conversation and presses p for the previous message. Should
	 * not move the pointer before the beginning of the list.
	 */
	public void moveCurrentBack() {
		if(curPos > 0 && curPos < list.size()){
			curPos--;
		}
	}
	/**
	 * Move the pointer to the last viewed email forward one, i.e. the user is 
	 * viewing a conversation and presses n for the next message. Should not 
	 * move the pointer past the end of the list.
	 */
	public void moveCurrentForward() {
		if(curPos >= 0 && curPos < list.size() - 1){
			curPos++;
		}
	}
	/**
	 * Return the number of emails in the conversation.
	 * @return the number of emails in the conversation.
	 */
	public int size() {
		return list.size();
	}
	/**
	 * Return the nth Email from the conversation.
	 * @param n the index of email
	 * @return the nth Email from the conversation
	 */
	public Email get(int n) {
		if(n>size()) {
			throw new IndexOutOfBoundsException();		
		}
		Email nthEmail = list.get(n);
		return nthEmail;
	}
	/**
	 * Add the email to the beginning of the conversation. Note: we can assume
	 * that all emails in the .zip are in reverse chronological order, so 
	 * every call to add(Email e) should be with an email that is older than 
	 * the last. Hence, each time we can simply add the email to the beginning 
	 * of the conversation.
	 * @param e the email to be added
	 */
	public void add(Email e) {
		if(!list.contains(e)){
			list.add(e);
		}
	}
	/**
	 * Return an Iterator that can be used to iterate over the emails in '
	 * the conversation.
	 * @return an Iterator that can be used to iterate over the emails 
	 * in the conversation.
	 */
	public Iterator<Email> iterator() {
		return list.iterator();
	}

}
