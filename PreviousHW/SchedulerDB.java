import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;



public class SchedulerDB {
	//TODO add private data members
	private List<Resource> list;

	/**
	 *Constructor for the SchedulerDB class.
	 */
	SchedulerDB(){
		list = new ArrayList<Resource>();
	}
	/**
	 * Adds a resource.
	 * @param resource
	 * @return whether added or not
	 */
	public boolean addResource(String resource){
		Resource newResource = new Resource(resource);
		if(list.size()==0){
			list.add(newResource);
			return true;
		}
		Resource oldResource = findResource(resource);
		if(oldResource==null){
			list.add(newResource);
			return true;
		}
		return false;
	}
	/**
	 * Removes a resource.
	 * @param r
	 * @return whether removed or not
	 */
	public boolean removeResource(String r){
		Resource delete = new Resource(r);
		if(list.size()==0){
			return false;
		}
		Resource oldResource = findResource(r);
		if(oldResource==null)return false;
			if(oldResource.getName().equals(delete.getName())){
				list.remove(oldResource);
				return true;
		}
		return false;
	}
	/**
	 * Adds an event to a resource.
	 * @param r resource name
	 * @param start start time
	 * @param end end time
	 * @param name name of the event
	 * @param organization organization of the event
	 * @param description description of the event
	 * @return 
	 */
	public boolean addEvent(String r, long start, long end, String name, 
			String organization, String description){
		Event newEvent = new Event(start,end,name,r, organization,description);
		Resource resource = findResource(r);
		if(resource==null)return false;
		Event oldEvent = null;
		Iterator<Event> itrEvent = resource.iterator();
		while(itrEvent.hasNext()){
			oldEvent = itrEvent.next();
			if(oldEvent.overlap(newEvent)){
				return false;
			}
		}
		resource.addEvent(newEvent);
		return true;
	}
	/**
	 * Deletes an event from a resource.
	 * @param start the start time of event
	 * @param resource the resource of event
	 * @return the deleted event
	 */
	public boolean deleteEvent(long start, String resource){
		Resource rsc = findResource(resource);
		if(rsc==null)return false;		
		Event oldEvent = null;
		Iterator<Event> itrEvent = rsc.iterator();
		while(itrEvent.hasNext()){
			oldEvent = itrEvent.next();
			if(oldEvent.getStart()==start){
				rsc.deleteEvent(start);
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns a resource given its name.
	 * @param r the given name of the resource
	 * @return a resource
	 */
	public Resource findResource(String r){
		Resource oldResource = null;
		Iterator<Resource> itr = list.iterator();
		while(itr.hasNext()){
			oldResource = itr.next();
			if(oldResource.getName().equals(r)){
				return oldResource;
			}
		} 
		return null;
	}
	/**
	 * Returns a list of resources maintained by this SchedulerDB class.
	 * @return a list of resources maintained by this SchedulerDB class.
	 */
	public List<Resource> getResources(){
		return list;
		}

	public List<Event> getEventsInResource(String resource){
		Resource rsc = findResource(resource);
		List<Event> arr = new ArrayList<Event>();
		Iterator<Event> itr = rsc.iterator();
		while(itr.hasNext()){
			arr.add(itr.next());
		}
		return arr;
	}

	public List<Event> getEventsInRange(long start, long end){
		Event range=new Event(start,end,"","","","");
		List<Event> arr = getAllEvents();
		List<Event> arr1= new ArrayList<Event>();
		Iterator<Event> itr = arr.iterator();
		while(itr.hasNext()){
			Event oldEvent=itr.next();
			if(oldEvent.overlap(range)){
				
				arr1.add(oldEvent);
			}
		} 
		return arr1;
	}	

	public List<Event> getEventsInRangeInResource(long start, long end, String resource){
		List<Event> arr = getEventsInRange(start,end);
		List<Event> arr1= new ArrayList<Event>();
		Iterator<Event> itr = arr.iterator();
		while(itr.hasNext()){
			Event oldEvent=itr.next();
			if(oldEvent.getResource().equals(resource)){
				arr1.add(oldEvent);
			}
		} 
		return arr1;
	}

	public List<Event> getAllEvents(){
		List<Event> arr = new ArrayList<Event>();
		Iterator<Resource> itr = list.iterator();
		while(itr.hasNext()){
			Resource oldResource=itr.next();
			Iterator <Event> itr1= oldResource.iterator();
			while(itr1.hasNext()){
				arr.add(itr1.next());
			}
		}
		return arr;
	}

	public List<Event> getEventsForOrg(String org){
		List<Event> arr = getAllEvents();
		List<Event> arr1= new ArrayList<Event>();
		Iterator<Event> itr = arr.iterator();
		while(itr.hasNext()){
			Event oldEvent=itr.next();
			if(oldEvent.getOrganization().equals(org)){
				arr1.add(oldEvent);
			}
		} 
		return arr1;
	}
}
