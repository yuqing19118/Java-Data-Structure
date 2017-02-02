import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Player {
	// player name
	private String name;
	// the magic sack held by the player that contains all his/her items
	private Set<Item> magicSack;
	//Do not add anymore private data members

	public Player(String name, Set<Item> startingItems){
		if(name==null)throw new IllegalArgumentException();
		this.name = name;
		this.magicSack = startingItems;
	}
	
	public String getName(){
    	return name;
	}
	
	//Returns a String consisting of the items in the sack
	//DO NOT MODIFY THIS METHOD
	public String printSack(){
		//neatly printed items in sack
		StringBuilder sb = new StringBuilder();
		sb.append("Scanning contents of your magic sack");
		sb.append(System.getProperty("line.separator"));
		for(Item itm : magicSack){
			sb.append(itm.getName());
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	
	public Set<Item> getActiveItems(){
		Set<Item> set = new HashSet<Item>();
		Item i = null;
    	Iterator<Item> itr = magicSack.iterator();
    	while(itr.hasNext()){
    		i = itr.next();
    		if(i.activated())set.add(i);
    	}
    	return set;
	}
	
	public Item findItem(String item){
		if(item==null)return null;
		Item i = null;
		Iterator<Item> itr = magicSack.iterator();
    	while(itr.hasNext()){
    		i = itr.next();
    		if(i.getName().equals(item))return i;
    	}
    	return null;
	}
	
	public boolean hasItem(Item item){
		if(item==null)return false;
		Item i = null;
		Iterator<Item> itr = magicSack.iterator();
    	while(itr.hasNext()){
    		i = itr.next();
    		if(i.getName().equals(item.getName()))return true;
    	}
    	return false;
	}
	
	public boolean addItem(Item item){
		if(item==null)return false;
    	if(hasItem(item))return false;
		magicSack.add(item);
		return true;
	}
	
	public boolean removeItem(Item item){
		if(!hasItem(item))return false;
		magicSack.remove(item);
		return true;
	}
}
