import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DirectedGraph<V> implements GraphADT<V>{
	private HashMap<V, ArrayList<V>> hashmap;
    //DO NOT ADD ANY OTHER DATA MEMBERS
	/**
	 * Creates an empty graph.
	 */
    public DirectedGraph() {
    	hashmap = new HashMap<V, ArrayList<V>>();
    }
    /**
     * Creates a graph from a preconstructed hashmap. 
     * This method will be used for testing your submissions. 
     * @param hashmap
     */
    public DirectedGraph(HashMap<V, ArrayList<V>> hashmap) {
    	this.hashmap = hashmap;
    }
    /**
     * Adds the specified vertex to this graph if not already present.
     * More formally, adds the specified vertex v to this graph if this 
     * graph contains no vertex u such that u.equals(v). 
     * If this graph already contains such vertex, 
     * the call leaves this graph unchanged and returns false.
     * @param vertex
     * @return whether this vertex is added
     */
    @Override
    public boolean addVertex(V vertex) {
    	
    	if(hashmap.containsKey(vertex)){
    		return false;
    	}
		hashmap.put(vertex, new ArrayList<V>());
		return true;
    }
    /**
     * Creates a new edge from vertex v1 to v2 and returns true, if v1 and 
     * v2 are not the same vertex and an edge does not already exist from 
     * v1 to v2. Returns false otherwise.Vertices v1 and v2 must already 
     * exist in this graph. If they are not found in the graph 
     * IllegalArgumentException is thrown.
     * @param v1  vertex v1
     * @param v2  vertex v2
     * @return
     */
    @Override
    public boolean addEdge(V v1, V v2) {
    	if(!hashmap.containsKey(v1)) throw new IllegalArgumentException();
    	if(!hashmap.containsKey(v2)) throw new IllegalArgumentException();
    	if(v1.equals(v2))return false;
    	if(hashmap.get(v1).contains(v2)) return false;
    	hashmap.get(v1).add(v2);	
    	return true;
    }
    
    /**
     * Returns a set of all vertices to which there are outward edges from v.
     * Vertex v must already exist in this graph. If it is not found in the 
     * graph IllegalArgumentException is thrown.
     * @param vertex
     * @return
     */
    @Override
    public Set<V> getNeighbors(V vertex) {
    	
    	if(!hashmap.containsKey(vertex)) throw new IllegalArgumentException();
    	Set<V> set = new HashSet<V>();
    	
    	Iterator<V> itr = hashmap.get(vertex).iterator();
    	while(itr.hasNext()){
    		set.add(itr.next());
    	}
    	return set;
    }
    /**
     * If both v1 and v2 exist in the graph, and an edge exists 
     * from v1 to v2, remove the edge from this graph. Otherwise, do nothing.
     * @param v1
     * @param v2
     */
    @Override
    public void removeEdge(V v1, V v2) {
    	if(hashmap.containsKey(v1) && hashmap.containsKey(v2)){
    		if(hashmap.get(v1).contains(v2)){
    			hashmap.get(v1).remove(v2);
    		}	
    	}
    }
    /**
     * Returns a set of all the vertices in the graph.
     * @return a set of all the vertices in the graph.
     */
    @Override
    public Set<V> getAllVertices() {
    	return hashmap.keySet();
    }

    @Override
    //Returns a String that depicts the Structure of the Graph
    //This prints the adjacency list
    //This has been done for you
    //DO NOT MODIFY
    public String toString() {
        StringWriter writer = new StringWriter();
        for (V vertex: this.hashmap.keySet()) {
            writer.append(vertex + " -> " + hashmap.get(vertex) + "\n");
        }
        return writer.toString();
    }

}
