///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  UWmail.java
// File:             DateTesting.java
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

import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

public class DateTesting {
  public static void main(String[] args) throws ParseException {
    Date date1, date2;
    DateFormat dateWithoutTime, df;
    String dateStr;

    dateWithoutTime = new SimpleDateFormat("yyyyMMdd"); 

    df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
    dateStr = "Tue, 12 Sep 2015 08:22:32 -0500";

    try {
      date1 = df.parse(dateStr);
      date2 = new Date();

      System.out.println(dateWithoutTime.format(date1));
      System.out.println(dateWithoutTime.format(date2));

      System.out.println("Dates are equal: " + 
          dateWithoutTime.format(date1).equals(dateWithoutTime.format(date2)));
    } catch (ParseException e) {
      System.out.println("Caught an error parsing the date!");
    }
  } 
}

