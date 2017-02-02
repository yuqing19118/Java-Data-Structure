///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            project 3
// Files:            Database.java, Document.java, Listnode.java, Server.java, User.java, Operation.java, WAL.java,
//                   SimpleStack.java, SimpleQueue.java, EmptyQueueException.java, EmptyStackException.java,StackADT.java, 
//                   QueueADT.java
// Semester:          CS 367 Fall 2015
//
// Author:           Yuting Tan
// Email:            ytan43@wisc.edu
// CS Login:         ytan
// Lecturer's Name:  Jim Skrentny
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     Qing Yu (Sabrina)
// Email:            qyu37@wisc.edu
// CS Login:         sabrina
// Lecturer's Name:  Jim Skrentny
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.PrintWriter;
/**
 * Simply read everything from sampleinput and print the output. Also create a database 
 * for user information and a queue for operation.
 *
 * <p>Bugs: no bugs
 *
 * @author ytan
 */
public class Server {
	//TODO define some class variables.
	private String inputFileName;
	private String outputFileName;
	private static Scanner scanner = null;
	private SimpleQueue<Operation> queue;
	private Database database;
	private PrintWriter pw;
	/**
	 * Creates a new server with the given input file name 
	 * and output file name 
	 * @param inputFileName the given input file name
	 * @param outputFileName the given output file name
	 */
	public Server(String inputFileName, String outputFileName) {
		//TODO constructor of server class.
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		queue = new SimpleQueue<Operation>();
		database = new Database();
	}
	/**
	 * Run the server. This method will call initialize 
	 * and process method sequently. 
	 */
	public void run(){
		initialize();
		process();
		
	}
	/**
	 *  Initializes the server based on the information from the input file. 
	 *  This is where you create document objects and queue all operations 
	 *  in the input file 
	 */
	public void initialize() {
		//TODO parse the input file.
	
		File inputFile = new File(inputFileName);
		//initialize user data file
		try {
			scanner = new Scanner(inputFile);
			//load the data from user data file
			String docString = scanner.nextLine();
			int docNum = Integer.parseInt(docString);
			for(int i = 0;i < docNum;i++){
				String line = scanner.nextLine();
				String[] tokens = line.split(",");
				int rowIndex = Integer.parseInt(tokens[1]);
				int colIndex = Integer.parseInt(tokens[2]);
				List<User> userList = new ArrayList<User>();
				for(int j = 3;j < tokens.length;j++){
					userList.add(new User(tokens[j]));
				}
				Document doc = new Document(tokens[0], rowIndex, 
						colIndex, userList);
				database.addDocument(doc);
				
			}
			/**
			 * Given different operations, initialize all the inputs and put the operations onto queue. 
			 */
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				String[] tokens = line.split(",");
				long timestamp = Long.parseLong(tokens[0]);
				String userId = tokens[1];
				String docName = tokens[2];
				Operation.OP op = null;
				if(Operation.OP.ADD.name().equals(tokens[3].toUpperCase())){
					op = Operation.OP.ADD;
				}
				else if(Operation.OP.SUB.name().equals(tokens[3].toUpperCase())){
					op = Operation.OP.SUB;
				}
				else if(Operation.OP.MUL.name().equals(tokens[3].toUpperCase())){
					op = Operation.OP.MUL;
				}
				else if(Operation.OP.DIV.name().equals(tokens[3].toUpperCase())){
					op = Operation.OP.DIV;
				}
				else if(Operation.OP.SET.name().equals(tokens[3].toUpperCase())){
					op = Operation.OP.SET;
				}
				else if(Operation.OP.UNDO.name().equals(tokens[3].toUpperCase())){
					op = Operation.OP.UNDO;
				}
				else if(Operation.OP.REDO.name().equals(tokens[3].toUpperCase())){
					op = Operation.OP.REDO;
				}
				else if(Operation.OP.CLEAR.name().equals(tokens[3].toUpperCase())){
					op = Operation.OP.CLEAR;
				}
				if(op == Operation.OP.ADD || op == Operation.OP.SUB || 
						op == Operation.OP.MUL || op == Operation.OP.DIV 
						|| op == Operation.OP.SET){
					int rowIndex = Integer.parseInt(tokens[4]);
					int colIndex = Integer.parseInt(tokens[5]);
					int constant = Integer.parseInt(tokens[6]);
					Operation operation = new Operation( docName, userId, op, rowIndex, 
							colIndex, constant, timestamp);
					queue.enqueue(operation);
				}
				else if(op == Operation.OP.UNDO || op == Operation.OP.REDO){
					Operation operation = new Operation( docName, userId, op, timestamp);
					queue.enqueue(operation);
				}
				else if(op == Operation.OP.CLEAR){
					int rowIndex = Integer.parseInt(tokens[4]);
					int colIndex = Integer.parseInt(tokens[5]);
					Operation operation = new Operation( docName, userId, op, rowIndex, 
							colIndex, timestamp);
					queue.enqueue(operation);
				}
				
			}

		} catch (FileNotFoundException e) {

		}
	}
	/**
	 * Processes each operation. Once you have queued all operations, you 
	 * begin extracting one operation from the operation queue one at a time, 
	 * updating the database and logging everything to the output file. 
	 */
	public void process() {
		//TODO process operations from the operation queue, one at a time.
		File outputFile = new File(outputFileName);
		try{
			pw = new PrintWriter(outputFile);
			while(!queue.isEmpty()){
				pw.println("----------Update Database----------");
				Operation operation = queue.dequeue();
				pw.println(operation);
				pw.println();
				database.update(operation);
				Document doc = database.getDocumentByDocumentName(operation.getDocName());
				pw.println(doc);
							
			}
			pw.close();
		}catch(FileNotFoundException e){
			
		}
		
	}

	public static void main(String[] args){
		if(args.length != 2){
			System.out.println("Usage: java Server [input.txt] [output.txt]");
			System.exit(0);
		}
		Server server = new Server(args[0], args[1]);
		server.run();
	}
}
