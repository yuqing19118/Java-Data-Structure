///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             IntervalBSTnode.java
// Semester:         cs367 Fall 2015
//
// Author:           Yuting Tan ytan43@wisc.edu
// CS Login:         ytan
// Lecturer's Name:  Jim Skrentny
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Qing Yu
// Email:            qyu37@wisc.edu
// CS Login:         sabrina
// Lecturer's Name:  Jim Skrentny
//
//////////////////////////// 80 columns wide //////////////////////////////////

class IntervalBSTnode<K extends Interval> {
	private K key;
    private IntervalBSTnode<K> left, right;
    private long maxEnd;
	/**
	 * Constructs a BST node with the given key value and whose left and right children are null.
	 * @param keyValue
	 */
    public IntervalBSTnode(K keyValue) {
		key = keyValue;
		left = null;
		right = null; 
    }
    /**
     * Constructs a BST node with the given key value, left child, and right child.
     * @param keyValue: the value of the node
     * @param leftChild: the left child of this node
     * @param rightChild: the right child of this node
     * @param maxEnd: the maximum ending time in the tree
     */
    public IntervalBSTnode(K keyValue, IntervalBSTnode<K> leftChild, IntervalBSTnode<K> rightChild, long maxEnd) {
    	if(keyValue == null)throw new IntervalConflictException();
		key = keyValue;
		if(left.getKey().compareTo(right.getKey()) == 1) throw new IntervalConflictException();
		left = leftChild;
		right = rightChild;
		this.maxEnd = maxEnd;
    }

    public K getKey() { 
		return key;
    }
    
    public IntervalBSTnode<K> getLeft() { 
		return left;
    }
  
    public IntervalBSTnode<K> getRight() { 
		return right;
    }
    /**
     * Returns the maximum end value of the intervals in this node's subtree.
     * @return the maximum end value of the intervals in this node's subtree.
     */
    public long getMaxEnd(){
    	if( right ==null){
    		maxEnd = getEnd();
    		return maxEnd;
    	}
    	else maxEnd = getRight().getMaxEnd(); 
		return maxEnd;
    }
    /**
     * Changes the key value for this node to the one given.
     * @param newK new key value
     */
    public void setKey(K newK) { 
		key = newK;
    }
    /**
     * Changes the left child for this node to the one given.
     * @param newL new left child
     */
    public void setLeft(IntervalBSTnode<K> newL) { 
		left = newL;
    }
    /**
     * Changes the right child for this node to the one given.
     * @param newR new right child
     */
    public void setRight(IntervalBSTnode<K> newR) { 
		right  = newR;
    }
    /**
     * Changes the maxEnd to the updated maximum end in the subtree.
     * @param newEnd
     */
    public void setMaxEnd(long newEnd) { 
		maxEnd = newEnd;
    }
    /**
     * Returns the start timestamp of this interval.
     * @return the start timestamp of this interval.
     */
    public long getStart(){ 
		return key.getStart();
	}
    /**
     * Returns the end timestamp of this interval.
     * @return the end timestamp of this interval.
     */
    public long getEnd(){
		return key.getEnd();
	}
    /**
     * Returns the key value of this BST node.
     * @return the key value of this BST node.
     */
    public K getData(){
		return key;
	}
    
}