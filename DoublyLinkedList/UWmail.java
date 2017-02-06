///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  UWmail.java
// File:             UWmail.java
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
 * Implement class UWmail with several basic functions.
 *
 * @author ytan
 */


import java.util.Date;
import java.util.Enumeration;
import java.util.Scanner;
import java.lang.Integer;
import java.lang.NumberFormatException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.io.*;
import java.text.*;

public class UWmail {
	private static UWmailDB uwmailDB = new UWmailDB();
	private static final Scanner stdin = new Scanner(System.in);

	public static void main(String args[]) {
		if (args.length != 1) {
			System.out.println("Usage: java UWmail [EMAIL_ZIP_FILE]");
			System.exit(0);
		}

		loadEmails(args[0]);

		displayInbox();
	}

	private static void loadEmails(String fileName) {
		//TODO
		try (ZipFile zf = new ZipFile(fileName);) {
			Enumeration<? extends ZipEntry> entries = zf.entries();
			while(entries.hasMoreElements()) {
				ZipEntry ze = entries.nextElement();
				if(ze.getName().endsWith(".txt")) {
					InputStream in = zf.getInputStream(ze);
					Scanner sc = new Scanner(in);
					Date date = null;
					String messageID = null;
					String subject = null;
					String from = null;
					String to = null;
					ListADT<String> body = new DoublyLinkedList<String>();
					String inReplyTo = null;
					ListADT<String> references = new DoublyLinkedList<String>();
					//read in a txt file
					while(sc.hasNextLine()) {
						String line = sc.nextLine();
						String[] tokens = line.split(";");
						try{
							if(tokens[0].equalsIgnoreCase("Date:")){
								if(tokens[1].isEmpty()) throw new MalformedEmailException();
								DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
								date = df.parse(tokens[1]);
							}
							else if(tokens[0].equalsIgnoreCase("Message-ID:")){
								if(tokens[1].isEmpty()) throw new MalformedEmailException();
								messageID = tokens[1];
							}
							else if(tokens[0].equalsIgnoreCase("Subject:")){
								if(tokens[1].isEmpty()) throw new MalformedEmailException();
								subject = tokens[1];
							}
							else if(tokens[0].equalsIgnoreCase("From:")){
								if(tokens[1].isEmpty()) throw new MalformedEmailException();
								from = tokens[1];
							}
							else if(tokens[0].equalsIgnoreCase("To:")){
								if(tokens[1].isEmpty()) throw new MalformedEmailException();
								to = tokens[1];
							}
							else if(tokens[0].equalsIgnoreCase("In-Reply-To::")){
								if(tokens[1].isEmpty()) throw new MalformedEmailException();
								inReplyTo = tokens[1];
							}
							else if(tokens[0].equalsIgnoreCase("References:")){

								if(tokens[1].isEmpty()) throw new MalformedEmailException();
								String[] ref = tokens[1].split(",");
								if(ref.length > 1){
									for(int i = 0; i < ref.length; i++) {
										references.add(ref[i]);
									}
								}
								else references.add(tokens[1]);
							}
							else {
								body.add(line);
							}
						}catch(MalformedEmailException e){
							break;
						} catch (ParseException e) {
							System.out.println("Caught an error parsing the date!");
						}
						try{
							if(date == null || messageID == null || subject == null || from == null || to == null){
								throw new MalformedEmailException();
							}
							else if(inReplyTo == null && references == null){
								uwmailDB.addEmail(new Email(date, messageID, subject, from, to, body));
							}
							else uwmailDB.addEmail(new Email(date, messageID, subject, from, to, body, inReplyTo,references));
						}catch (MalformedEmailException e){
							break;
						}
					}
					sc.close();
				}
			}
		} catch (ZipException e) {
			System.out.println("A .zip format error has occurred for the file.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("An I/O error has occurred for the file.");
			System.exit(1);
		} catch (SecurityException e) {
			System.out.println("Unable to obtain read access for the file.");
			System.exit(1);
		}
	}

	private static void displayInbox(){
		boolean done = false;
		//TODO: print out the inbox here, according to the guidelines in the problem
		System.out.println("Inbox:");
		System.out.println("--------------------------------------------------------------------------------");
		if(uwmailDB.size()==0){
			System.out.println("No conversations to show.");
		}
		else{
			ListADT <Conversation> inbox = uwmailDB.getInbox();
			Conversation con;
			for(int i = 0; i < uwmailDB.size();i++){
				con = inbox.get(i);
				System.out.println("["+i+"] "+inbox.get(i).get(0)+" ("+con.get(con.size()-1).getDate()+")");
			}
		}
		System.out.println("Enter option ([#]Open conversation, [T]rash, [Q]uit):");
		while (!done) 
		{
			System.out.print("Enter option ([#]Open conversation, [T]rash, " + 
					"[Q]uit): ");
			String input = stdin.nextLine();

			if (input.length() > 0) 
			{

				int val = 0;
				boolean isNum = true;

				try {
					val = Integer.parseInt(input);
				} catch (NumberFormatException e) {
					isNum = false;
				}

				if(isNum) {
					if(val < 0) {
						System.out.println("The value can't be negative!");
						continue;
					} else if (val >= uwmailDB.size()) {
						System.out.println("Not a valid number!");
						continue;
					} else {
						displayConversation(val);
						continue;
					}

				}

				if(input.length()>1)
				{
					System.out.println("Invalid command!");
					continue;
				}

				switch(input.charAt(0)){
				case 'T':
				case 't':
					displayTrash();
					break;

				case 'Q':
				case 'q':
					System.out.println("Quitting...");
					done = true;
					break;

				default:  
					System.out.println("Invalid command!");
					break;
				}
			} 
		} 
		System.exit(0);
	}

	private static void displayTrash(){
		boolean done = false;
		//TODO: print out the trash here according to the problem specifications
		System.out.println("Trash:");
		System.out.println("--------------------------------------------------------------------------------");
		if(uwmailDB.size()==0){
			System.out.println("No conversations to show.");
		}
		else{
			ListADT <Conversation> trash = uwmailDB.getTrash();
			Conversation con;
			for(int i = 0; i < uwmailDB.size();i++){
				con = trash.get(i);
				System.out.println("["+i+"] "+trash.get(i).get(0)+" ("+con.get(con.size()-1).getDate()+")");
			}
		}
		System.out.println("Enter option ([#]Open conversation, [T]rash, [Q]uit):");
		while (!done) 
		{
			System.out.print("Enter option ([I]nbox, [Q]uit): ");
			String input = stdin.nextLine();

			if (input.length() > 0) 
			{
				if(input.length()>1)
				{
					System.out.println("Invalid command!");
					continue;
				}

				switch(input.charAt(0)){
				case 'I':
				case 'i':
					displayInbox();
					break;

				case 'Q':
				case 'q':
					System.out.println("Quitting...");
					done = true;
					break;

				default:  
					System.out.println("Invalid command!");
					break;
				}
			} 
		} 
		System.exit(0);
	}

	private static void displayConversation(int val) {
		//TODO: Check whether val is valid as a conversation number. If not, take
		//the user back to the inbox view and continue processing commands.
		if(val<0 || val >= uwmailDB.size()){
			displayInbox();
			return;
		}

		boolean done = false;
		//TODO: Print the conversation here, according to the problem specifications
		ListADT<Conversation> inbox =  uwmailDB.getInbox();
		Iterator<Conversation> itr = inbox.iterator();
		Conversation currCon = null;
		int index = 0;
		while(itr.hasNext() && index < inbox.size()){
			currCon = itr.next();
			index++;
			if(val == index) break;
		}
		/*
		 * SUBJECT: p2
--------------------------------------------------------------------------------
sender@wisc.edu | Don't forget that p2 is due soon! | Sep 21
--------------------------------------------------------------------------------
From: you@wisc.edu
To: sender@wisc.edu
Sep 22

Thanks for the reminder!

On Mon, Sep 21, 2015 at 9:46 PM, <sender@wisc.edu> wrote:
> Don't forget that p2 is due soon!
--------------------------------------------------------------------------------
Enter option ([N]ext email, [P]revious email, [J]Next conversation, [K]Previous
conversation, [I]nbox, [#]Move to trash, [Q]uit):

		 */
		System.out.println("SUBJECT: "+currCon.get(0).getSubject());
		int count = 0;
		while(count < currCon.getCurrent()){
			System.out.println("--------------------------------------------------------------------------------");
			System.out.print(currCon.get(count).getFrom()+" | "+currCon.get(count).getBody().get(0)+" | "+currCon.get(count).getDate());
			System.out.println("--------------------------------------------------------------------------------");
			count++;
		}
		if(count == currCon.getCurrent()){
			System.out.println("From: "+ currCon.get(count).getFrom());
			System.out.println("To: "+ currCon.get(count).getInReplyTo());
			System.out.println( currCon.get(count).getDate());
			System.out.println(currCon.get(count).getBody().get(0));
		}
		while(count > currCon.getCurrent() && count < currCon.size()){
			System.out.println("--------------------------------------------------------------------------------");
			System.out.print(currCon.get(count).getFrom()+" | "+currCon.get(count).getBody().get(0)+" | "+currCon.get(count).getDate());
			System.out.println("--------------------------------------------------------------------------------");
			count++;
		}

		while (!done) 
		{
			System.out.print("Enter option ([N]ext email, [P]revious email, " +
					"[J]Next conversation, [K]Previous\nconversation, [I]nbox, " +
					"[#]Move to trash, [Q]uit): ");
			String input = stdin.nextLine();

			if (input.length() > 0) 
			{

				if(input.length()>1)
				{
					System.out.println("Invalid command!");
					continue;
				}

				switch(input.charAt(0)){
				case 'P':
				case 'p':
					//TODO: for this conversation, move the current email pointer back 
					//  using Conversation.moveCurrentBack().
					//
					currCon.moveCurrentBack();
					displayConversation(val);
					break;
				case 'N':
				case 'n':
					//TODO: for this conversation, move the current email pointer 
					//  forward using Conversation.moveCurrentForward().
					//
					currCon.moveCurrentForward();
					displayConversation(val);
					break;
				case 'J':
				case 'j':
					//TODO: Display the next conversation
					displayConversation(val++);
					break;

				case 'K':
				case 'k':
					//TODO: Display the previous conversation
					displayConversation(val--);
					break;

				case 'I':
				case 'i':
					displayInbox();
					return;

				case 'Q':
				case 'q':
					System.out.println("Quitting...");
					done = true;
					break;

				case '#':
					//TODO: add delete conversation functionality. This conversation
					//should be moved to the trash when # is entered, and you should
					//take the user back to the inbox and continue processing input.
					//

					ListADT<Conversation> trash = uwmailDB.getTrash();
					trash.add(inbox.remove(val));
					return;

				default:  
					System.out.println("Invalid command!");
					break;
				}
			} 
		} 
		System.exit(0);
	}

}

