///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             IntervalConflictException.java
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

public class IntervalConflictException extends RuntimeException{
	/**throw an exception when there is an error or print out a message
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IntervalConflictException(){
		super();
    }
    
    public IntervalConflictException(String message) {
        super(message);
    }
	
}
