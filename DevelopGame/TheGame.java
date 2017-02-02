import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TheGame {
	private static String gameIntro; // initial introduction to the game
	private static String winningMessage; //winning message of game
	private static String gameInfo; //additional game info
	private static boolean gameWon = false; //state of the game
	private static Scanner scanner = null; //for reading files
	private static Player player; //object for player of the game
	private static Room location; //current room in which player is located
	private static Room winningRoom; //Room which player must reach to win
	private static Item winningItem; //Item which player must find
	private static DirectedGraph<Room> layout; //Graph structure of the Rooms

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Bad invocation! Correct usage: "
					+ "java AppStore <gameFile>");
			System.exit(1);
		}

		boolean didInitialize = initializeGame(args[0]);

		if (!didInitialize) {
			System.err.println("Failed to initialize the application!");
			System.exit(1);
		}

		System.out.println(gameIntro); // game intro

		processUserCommands();
	}
	/**
	 * Reads the file named "gameFile" and initializes all variables for the 
	 * game to run smoothly.
	 * @param gameFile
	 * @return true if initialized successfully
	 */
	private static boolean initializeGame(String gameFile) {

		try {
			// reads player name
			System.out.println("Welcome worthy squire! What might be your name?");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String playerName = br.readLine();

			//TODO Remove this exception and implement the rest of this method 
			//	inside this try-catch block
			//  Please follow the steps outlined in the Specifications
			/*
			 * You will read the contents of the input file using Java File IO and
			 * then process them to initialize your player and layout (DirectedGraph)
			 */
			File inputFile = new File(gameFile);
			scanner = new Scanner(inputFile);
			layout = new DirectedGraph<Room>();
			gameIntro = scanner.nextLine();
			winningMessage = scanner.nextLine();
			gameInfo = scanner.nextLine();
			String line = scanner.nextLine();
			if(line.contains("#player items:")){
				line = scanner.nextLine();
				Set<Item> startingItems = new HashSet<Item>();
				while(line.contains("#item:")){
					/**
					 * 
					 * Item name
					 * Item description
					 * Boolean denoting whether item is activated already
					 * Item message
					 * Boolean denoting if item is one-time-use
					 * Message to print when item is used
					 */
					boolean isWin = false;
					if(line.contains("#win"))isWin = true;
					

					String	name = scanner.nextLine().trim();
					String	description = scanner.nextLine().trim();
					boolean activated = scanner.nextLine().trim().equals("true");
					String message = scanner.nextLine().trim();
					boolean oneTimeUse = scanner.nextLine().trim().equals("true");
					String usedString = scanner.nextLine().trim();
					Item item = new Item(name,  description,  activated, 
							message,oneTimeUse, usedString);
					if(isWin) winningItem = item;
					startingItems.add(item);
					line = scanner.nextLine();
				}
				player = new Player(playerName,startingItems);
			}

			/**
			 * 
    Room name
    Room description
    Boolean denoting visibility in the room
    Boolean denoting habitability in the room
        If the room habitability is false, the the next line will contain the reason why the room is not habitable. If habitability is true, the next line will begin the items as below.
    Now read the items in the room. Refer to step 5 for reading each item.
    Next you need to read the message handlers. They help the room handle messages which the items send. Each of them is headed by #messageHandler, followed by its properties in the following order :
        message
        type (visibility, habitability or room)
            If the second line is 'room', there will be a third line containing the roomName. Otherwise, there will be only two lines in the message handler definition.This is used for unlocking locked rooms

			 */
			int countRoom = 0;
			while(line.contains("#room:")){
				boolean isWinRoom = false;
				if(line.contains("#win"))isWinRoom = true;
				String	name = scanner.nextLine().trim();
				String	description = scanner.nextLine().trim();
				boolean	visibility = scanner.nextLine().trim().equals("true");
				boolean habitability = scanner.nextLine().trim().equals("true");
				String	habitableMsg = null;
				if(!habitability){
					habitableMsg = scanner.nextLine().trim();
				}
				Set<Item> items = new HashSet<Item>();
				List<MessageHandler> handlers = new ArrayList<MessageHandler>();

				line=scanner.nextLine();
				while(line.contains("#item:")){
					/**
					 * 
					 * Item name
					 * Item description
					 * Boolean denoting whether item is activated already
					 * Item message
					 * Boolean denoting if item is one-time-use
					 * Message to print when item is used
					 */
					boolean isWin = false;
					if(line.contains("#win"))isWin = true;
					
					String	itemName = scanner.nextLine().trim();
					String	itemDescription = scanner.nextLine().trim();
					boolean activated = scanner.nextLine().trim().equals("true");
					String message = scanner.nextLine().trim();
					boolean oneTimeUse = scanner.nextLine().trim().equals("true");
					String usedString = scanner.nextLine().trim();
					Item item = new Item(itemName,  itemDescription,  activated, 
							message,oneTimeUse, usedString);
					if(isWin) winningItem = item;
					items.add(item);
					line = scanner.nextLine();
				}
				while(line.contains("#messageHandler:")){
					String msg = scanner.nextLine().trim();
					String type = scanner.nextLine().trim();
					String roomName = null;
					if(type.equals("room")){
						roomName = scanner.nextLine().trim();

					}
					MessageHandler msgHandler = new MessageHandler(msg, type, roomName);
					handlers.add(msgHandler);
					line = scanner.nextLine();
				}
				Room room = new Room(name, description, visibility, habitability,
						habitableMsg, items, handlers);
				layout.addVertex(room);
				countRoom++;
				if(isWinRoom)winningRoom = room;
				if(countRoom == 1) location = room;
			}

			if(line.contains("#locked passages:")){
				line = scanner.nextLine().trim();
				String roomName1= line.substring(0, line.indexOf(' '));
				String roomName2= line.substring(line.indexOf(' ')+1,line.length());
				String whyLocked = scanner.nextLine().trim();
				Room room1 = findRoom(roomName1,layout.getAllVertices());
				if(room1!=null){
					Room room2 = findRoom(roomName2,layout.getAllVertices());
					if(room2!=null){
						room1.addLockedPassage(room2, whyLocked);
					}
					else throw new IllegalArgumentException();
				}
				else throw new IllegalArgumentException();
				line = scanner.nextLine();
			}
			if(line.contains("#Adjacency List:")){
				while(scanner.hasNextLine()){
					line = scanner.nextLine().trim();
					String[] tokens = line.split(" ");
					
					Room src = findRoom(tokens[0],layout.getAllVertices());
					if(src==null)throw new IllegalArgumentException();
					for(int i = 1; i<tokens.length;i++){
						Room target = findRoom(tokens[i],layout.getAllVertices());
						if(!layout.addEdge(src, target)) throw new IllegalArgumentException();
					}
				}
			}
			winningRoom = findRoom(winningRoom.getName(),layout.getAllVertices());
			location = findRoom(location.getName(),layout.getAllVertices());
			return true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	private static Room findRoom(String name,Set<Room> rooms){
		Iterator<Room> itr = rooms.iterator();
		Room findRoom = null;
		boolean isFound =false;
		while(itr.hasNext()){
			findRoom = itr.next();
			if(findRoom.getName().equals(name)){
				isFound = true;
				break;
			}
		}
		if(isFound)return findRoom;
		else return null;
	}
	private static void processUserCommands() {
		scanner = new Scanner(System.in);
		String command = null;
		do {

			System.out.print("\nPlease Enter a command ([H]elp):");
			command = scanner.next();
			switch (command.toLowerCase()) {
			case "p": // pick up
				processPickUp(scanner.nextLine().trim());
				goalStateReached();
				break;
			case "d": // put down item
				processPutDown(scanner.nextLine().trim());
				break;
			case "u": // use item
				processUse(scanner.nextLine().trim());
				break;
			case "lr":// look around
				processLookAround();
				break;
			case "lt":// look at
				processLookAt(scanner.nextLine().trim());
				break;
			case "ls":// look at sack
				System.out.println(player.printSack());
				break;
			case "g":// goto room
				processGoTo(scanner.nextLine().trim());
				goalStateReached();
				break;
			case "q":
				System.out.println("You Quit! You, " + player.getName() + ", are a loser!!");
				break;
			case "i":
				System.out.println(gameInfo);
				break;
			case "h":
				System.out
				.println("\nCommands are indicated in [], and may be followed by \n"+
						"any additional information which may be needed, indicated within <>.\n"
						+ "\t[p]  pick up item: <item name>\n"
						+ "\t[d]  put down item: <item name>\n"
						+ "\t[u]  use item: <item name>\n"
						+ "\t[lr] look around\n"
						+ "\t[lt] look at item: <item name>\n"
						+ "\t[ls] look in your magic sack\n"
						+ "\t[g]  go to: <destination name>\n"
						+ "\t[q]  quit\n"
						+ "\t[i]  game info\n");
				break;
			default:
				System.out.println("Unrecognized Command!");
				break;
			}
		} while (!command.equalsIgnoreCase("q") && !gameWon);
		scanner.close();
	}

	private static void processLookAround() {
		System.out.print(location.toString());
		for(Room rm : layout.getNeighbors(location)){
			System.out.println(rm.getName());
		}
	}

	private static void processLookAt(String item) {
		Item itm = player.findItem(item);
		if(itm == null){
			itm = location.findItem(item);
		}
		if(itm == null){
			System.out.println(item + " not found");
		}
		else
			System.out.println(itm.toString());
	}

	private static void processPickUp(String item) {
		if(player.findItem(item) != null){
			System.out.println(item + " already in sack");
			return;
		}
		Item newItem = location.findItem(item);
		if(newItem == null){
			System.out.println("Could not find " + item);
			return;
		}
		player.addItem(newItem);
		location.removeItem(newItem);
		System.out.println("You picked up ");
		System.out.println(newItem.toString());
	}

	private static void processPutDown(String item) {
		if(player.findItem(item) == null){
			System.out.println(item + " not in sack");
			return;
		}
		Item newItem = player.findItem(item);
		location.addItem(newItem);
		player.removeItem(newItem);
		System.out.println("You put down " + item);
	}

	private static void processUse(String item) {
		Item newItem = player.findItem(item);
		if(newItem == null){
			System.out.println("Your magic sack doesn't have a " + item);
			return;
		}
		if (newItem.activated()) {
			System.out.println(item + " already in use");
			return;
		}
		if(notifyRoom(newItem)){
			if (newItem.isOneTimeUse()) {
				player.removeItem(newItem);
			}
		}
	}

	private static void processGoTo(String destination) {
		Room dest = findRoomInNeighbours(destination);
		if(dest == null) {
			for(Room rm : location.getLockedPassages().keySet()){
				if(rm.getName().equalsIgnoreCase(destination)){
					System.out.println(location.getLockedPassages().get(rm));
					return;
				}
			}
			System.out.println("Cannot go to " + destination + " from here");
			return;
		}
		Room prevLoc = location;
		location = dest;
		if(!player.getActiveItems().isEmpty())
			System.out.println("The following items are active:");
		for(Item itm:player.getActiveItems()){
			notifyRoom(itm);
		}
		if(!dest.isHabitable()){
			System.out.println("Thou shall not pass because");
			System.out.println(dest.getHabitableMsg());
			location = prevLoc;
			return;
		}

		System.out.println();
		processLookAround();
	}

	private static boolean notifyRoom(Item item) {
		Room toUnlock = location.receiveMessage(item.on_use());
		if (toUnlock == null) {
			if(!item.activated())
				System.out.println("The " + item.getName() + " cannot be used here");
			return false;
		} else if (toUnlock == location) {
			System.out.println(item.getName() + ": " + item.on_useString());
			item.activate();
		} else {
			// add edge from location to to Unlock
			layout.addEdge(location, toUnlock);
			if(!item.activated())
				System.out.println(item.on_useString());
			item.activate();
		}
		return true;
	}

	private static Room findRoomInNeighbours(String room) {
		Set<Room> neighbours = layout.getNeighbors(location);
		for(Room rm : neighbours){
			if(rm.getName().equalsIgnoreCase(room)){
				return rm;
			}
		}
		return null;
	}

	private static void goalStateReached() {
		if ((location == winningRoom && player.hasItem(winningItem)) 
				|| (location == winningRoom && winningItem == null)){
			System.out.println("Congratulations, " + player.getName() + "!");
			System.out.println(winningMessage);
			System.out.println(gameInfo);
			gameWon = true;
		}
	}

}
