///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             Event.java
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

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Event represents events to be held in .
 */
public class Event implements Interval{
	//TODO add private data members
	private long start;
	private long end; 
	private String name; 
	private String resource; 
	private String organization; 
	private String description;
	/**
	 * Constructor for an Event object. 
	 * Throws an IllegalArgumentException for invalid inputs.
	 * @param start: the start date of the event 
	 * @param end: the end date of the event
	 * @param name: the name of the event
	 * @param resource: the place of event going to happen
	 * @param organization: the name of the student org
	 * @param description: the description of the event
	 */
	Event(long start, long end, String name, String resource, String organization, String description){
		//TODO Remove this exception and implement the method
		if(start<0) throw new IllegalArgumentException();
		this.start = start;
		if(end < 0 || end < start) throw new IllegalArgumentException();
		this.end = end; 
		if(name == null) throw new IllegalArgumentException();
		this.name = name; 
		if(resource == null) throw new IllegalArgumentException();
		this.resource = resource; 
		if(organization == null) throw new IllegalArgumentException();
		this.organization = organization;
		if(description == null) throw new IllegalArgumentException();
		this.description = description;
	}
	/**
	 * Returns the start of an interval.
	 * @return the start of an interval.
	 */
	@Override
	public long getStart(){
		//TODO Remove this exception and implement the method
		return start;
	}

	/**
	 * Returns the end of an interval.
	 * @return  the end of an interval.
	 */
	@Override
	public long getEnd(){
		return end;
	}
	/**
	 * Returns the name of the event.
	 * @return the name of the event.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns the resource for the event.
	 * @return the resource for the event.
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * Returns the organization to which this event belongs.
	 * @return the organization to which this event belongs.
	 */
	public String getOrganization(){
		return organization;
	}
	/**
	 * Returns the description of the event.
	 * @return the description of the event.
	 */
	public String getDescription() {
		return description;	
	}

	@Override
	/**
	 * Compares two events of type Interval. 
	 * Returns -1 if the start timestamp of this interval-type event 
	 * is less than the start timestamp of the other interval-type 
	 * event, otherwise returns 1.
	 * @return -1 if the start timestamp of this interval-type event 
	 * is less than the start timestamp of the other interval-type 
	 * event, otherwise returns 1.
	 */
	public int compareTo(Interval i) {
		//TODO Remove this exception and implement the method
		if(start<i.getStart()) return -1;
		else return 1;
	}
	
	/**
	 * Returns true if the start timestamp of this event is equal 
	 * to the start timestamp of the other event, else returns false.
	 * @param e another event
	 * @return true if the start timestamp of this event is equal 
	 * to the start timestamp of the other event, else returns false.
	 */
	public boolean equals(Event e) {
		if(start==e.getStart()) return true;
		else return false;
	}

	@Override
	/**
	 *  Returns true if the otherInterval object overlaps with this interval,
	 *  otherwise returns false. Two intervals overalap, if their timing 
	 *  intersection is non-zero. For example, event A(15:00 - 17:00) 
	 *  overlaps with event B(16:30 - 17:30), but does not overlap with 
	 *  event C(17:00 - 18:00).
	 *  @return true if the otherInterval object overlaps with this interval
	 */
	public boolean overlap(Interval i) {
		if(i.getStart()>start && i.getStart()<end) return true;
		else return false;
	}
	
	@Override
	/**
	 * Returns the string representation of this event. It must be in exactly the following format:
	 * <Event name>
	 * By: <Organization name>
	 * In: <Name of resource>
	 * Start: <Start of event in mm/dd/yyyy,hh:mm>
	 * End: <End of event in mm/dd/yyyy,hh:mm>
	 * Description: <Description of event>
	 * @return the string representation of this event.
	 */
	public String toString(){
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy,HH:mm");
		String retval = name + "\nBy: "+ organization + "\nIn: "+resource+"\nStart: ";
		
			Date startDate = new Date(start*1000);
			String startStr = df.format(startDate);
			Date endDate = new Date(end*1000);
			String endStr = df.format(endDate);
			retval+= startStr+"\nEnd: "+endStr+"\nDescription: "+description;
		
		return retval;
	}
}
