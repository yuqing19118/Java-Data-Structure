import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class Room {
	//name of the room
	private	String	name;
	//description of the room
	private	String	description;
	//whether the room is lit or dark
	private	boolean	visibility;
	//whether the room is habitable
	private	boolean habitability;
	//reason for room not being habitable (only relevant when habitability is false)
	private String	habitableMsg;
	//items in the room
	private	Set<Item>	items;
	// message handlers
	private	List<MessageHandler> handlers;
	//locked rooms and the reason for their being locked
	private HashMap<Room, String> lockedPassages;	
	//Do not add anymore data members
	
	public Room(String name, String description, boolean visibility, boolean habitability,
			String habMsg, Set<Item> items, List<MessageHandler> handlers){
		if(name==null||description==null)throw new IllegalArgumentException();
		this.name = name;
		this.description = description;
		this.visibility = visibility;
		this.habitability = habitability;
		this.habitableMsg = habMsg;
		this.items = items;
		this.handlers = handlers;
		lockedPassages = new HashMap<Room,String>();
	}
	/**
	 * Getter method to get the Room's name
	 * @return the Room's name
	 */
	public String getName(){
    	return name;
	}
	/**
	 * Returns whether the visibility in the room is true or false
	 * @return whether the visibility in the room is true or false
	 */
	public boolean isVisible(){
    	return visibility;
	}
	/**
	 * Returns true if the room is habitable, otherwise returns false
	 * @return true if the room is habitable, otherwise returns false
	 */
	public boolean isHabitable(){
    	return habitability;
	}
	/**
	 * If the room is not habitable, returns a String describing 
	 * the reason why it is not habitable. If the room is habitable 
	 * returns null.
	 * @return the reason why it is not habitable
	 */
	public String getHabitableMsg(){
    	if(!isHabitable()) return habitableMsg;
    	else return null;
	}
	/**
	 * Returns the HashMap containing the lockedRooms and 
	 * the corresponding reasons why they are locked.
	 * @return the HashMap containing the lockedRooms and 
	 * the corresponding reasons why they are locked.
	 */
	public HashMap<Room, String> getLockedPassages(){
    	return lockedPassages;
	}
	/**
	 * Adds a (RoomName, ReasonWhyLocked) pair to the list of locked 
	 * passages for a room.
	 * @param passage
	 * @param whyLocked
	 */
	public void addLockedPassage(Room passage, String whyLocked){
		if(passage==null|| whyLocked==null)throw new IllegalArgumentException();
		lockedPassages.put(passage, whyLocked);
		
	}
	/**
	 * If it finds the Item "itemName" in the Room's items, 
	 * it returns that Item. Otherwise it returns null 	
	 * @param item the item name
	 * @return the item
	 */
	public Item findItem(String item){
		Item i = null;
		Iterator<Item> itr = items.iterator();
    	while(itr.hasNext()){
    		i = itr.next();
    		if(i.getName().equals(item))return i;
    	}
    	return null;
	}
	/**
	 * Adds an the "item" to the Room's items. Duplicate items are not allowed. 
	 * Java Sets do not allow duplicate items to be added. Hence we use a Set 
	 * to store the items in the Room. Returns true if item is added, 
	 * returns false otherwise
	 * @param item the added item
	 * @return true if item is added, returns false otherwise
	 */
	public boolean addItem(Item item){
    	if(items.contains(item)) return false;
    	items.add(item);
    	return true;
	}
	/**
	 * Removes the "item" from the Rooms Set of items. 
	 * Returns true if item was removed, false otherwise.
	 * @param item the item to be removed 
	 * @return true if item was removed, false otherwise.
	 */
	public boolean removeItem(Item item){
		if(!items.contains(item)) return false;
    	items.remove(item);
    	return true;
	}

	/***
	 * Receives messages from items used by the player and executes the 
	 * appropriate action stored in a message handler
	 * @param message is the "message" sent by the item
	 * @return null, this Room or Unlocked Room depending on MessageHandler
	 * DO NOT MODIFY THIS METHOD
	 */
	public Room receiveMessage(String message){
		Iterator<MessageHandler> itr = handlers.iterator();
		MessageHandler msg = null;
		while(itr.hasNext()){
			msg = itr.next();
			if(msg.getExpectedMessage().equalsIgnoreCase(message))
				break;
			msg = null;
		}
		if(msg == null)
			return null;
		switch(msg.getType()){
		case("visibility") :
			this.visibility = true;
			return this;
		case("habitability") :
			this.habitability = true;
			return this;
		case("room") :
			for(Room rm : this.lockedPassages.keySet()){
				if(rm.getName().equalsIgnoreCase(msg.getRoomName())){
					this.lockedPassages.remove(rm);
					return rm;
				}
			}
		default:
			return null;
		}
	}

	@Override
	//Returns a String consisting of the Rooms name, its description,
	//its items and locked rooms.
	// DO NOT MODIFY THIS METHOD
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Welcome to the " + name + "!");
		sb.append(System.getProperty("line.separator"));
		if(!this.visibility){
			sb.append("Its too dark to see a thing!");
			sb.append(System.getProperty("line.separator"));
			sb.append("Places that can be reached from here :");
			sb.append(System.getProperty("line.separator"));
			for(Room rm :this.lockedPassages.keySet()){
				sb.append(rm.getName());
				sb.append(System.getProperty("line.separator"));
			}
			return sb.toString();
		}
		sb.append(description);
		sb.append(System.getProperty("line.separator"));
		if(this.items.size() >0){ 
			sb.append("Some things that stand out from the rest :");
			sb.append(System.getProperty("line.separator"));
		}
		Iterator<Item> itr = this.items.iterator();
		while(itr.hasNext()){
			sb.append(itr.next().getName());
			sb.append(System.getProperty("line.separator"));
		}
		sb.append("Places that can be reached from here :");
		sb.append(System.getProperty("line.separator"));
		for(Room rm :this.lockedPassages.keySet()){
			sb.append(rm.getName());
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
}

