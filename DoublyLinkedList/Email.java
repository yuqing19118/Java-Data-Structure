///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  UWmail.java
// File:             Email.java
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
 * Construct class Email to satisfy several features.
 *
 * @author ytan
 */
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
public class Email {
	private Date date;
	private String messageID;
	private String subject;
	private String from;
	private String to ;
	private ListADT<String> body;
	private String inReplyTo;
	private ListADT<String> references;

	/**
	 * Constructs an Email with the given attributes. This is the constructor 
	 * for an email that is the first in the conversation, i.e. without 
	 * In-Reply-To or References fields.
	 * @param date the date of email
	 * @param messageID the message ID of email
	 * @param subject the subject of email
	 * @param from the sender of the email
	 * @param to the receiver of the email
	 * @param body the body of the email
	 */
	public Email(Date date, String messageID, String subject, String from, 
			String to, ListADT<String> body) {
		this(date,messageID,subject,from,to,body,null,null);
	}

	/**
	 * Constructs an Email with the given attributes. This is the constructor
	 * for an email that is not the first in the conversation, i.e. contains 
	 * In-Reply-To and References fields.
	 * 
	 * @param date the date of email
	 * @param messageID the message ID of email
	 * @param subject the subject of email
	 * @param from the sender of the email
	 * @param to the receiver of the email
	 * @param body the body of the email
	 * @param inReplyTo the Message-ID of the single previous email
	 * @param references all of the Message-ID's of the emails which came 
	 * before it in the conversation
	 */
	public Email(Date date, String messageID, String subject, String from, 
			String to, ListADT<String> body, String inReplyTo, ListADT<String> 
	references) {
		this.date = date;
		this.messageID = messageID;
		this.subject = subject;
		this.from = from;
		this.to = to;
		this.body = body;
		this.inReplyTo = inReplyTo;
		this.references = references;
	}
	/**
	 * Return the date in a human-readable form. If the date on the email is
	 * today, then you should format it with a SimpleDateFormat object and the
	 * formatting string "h:mm a". Otherwise, format it with a SimpleDateFormat
	 * object and the formatting string "MMM d".
	 * @return
	 */
	public String getDate() {
		Date currDate = new Date();
		DateFormat dateWithoutTime, dateWithTime;
		dateWithoutTime = new SimpleDateFormat("MMM d"); 
		dateWithTime = new SimpleDateFormat("h:mm a");
		String dateStr =dateWithoutTime.format(date);
		String currDateStr = dateWithoutTime.format(currDate);
		if(dateStr.equals(currDateStr)) return dateWithTime.format(date);
		else return dateWithoutTime.format(date);
	}

	/**
	 * Return the unique email identifier that was stored in the Message-ID 
	 * field.
	 * @return the unique email identifier that was stored in the Message-ID 
	 * field.
	 */
	public String getMessageID() {
		return messageID;
	}
	/**
	 * Return what was stored in the Subject: field.
	 * @return what was stored in the Subject: field.
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * Return what was stored in the From: field.
	 * @return what was stored in the From: field.
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * Return what was stored in the To: field.
	 * @return what was stored in the To: field.
	 */
	public String getTo() {
		return to; 
	}
	/**
	 * Return the lines of text from the end of the header to the end of 
	 * the file.
	 * @return the lines of text from the end of the header to the end of the 
	 * file.
	 */
	public ListADT<String> getBody() {
		return body;
	}
	/**
	 * Return what was stored in the In-Reply-To: field. If the email was the 
	 * first in the conversation, return null.
	 * @return what was stored in the In-Reply-To: field.
	 */
	public String getInReplyTo() {
		return inReplyTo;

	}
	/**
	 * Return the Message-ID's from the References: field. If the email was 
	 * the first in the conversation, return null.
	 * @return Return the Message-ID's from the References: field. 
	 */
	public ListADT<String> getReferences() {
		return references;
	}
} 
