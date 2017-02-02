import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
/**
 * create database which contains all documents for different users. We can edit the database by adding new documents
 * update operations and get the whole list of documents.
 *
 * <p>Bugs: no bugs
 *
 * @author ytan
 */
public class Database {
    //TODO define some class variables.
	List<Document> documentList;
	/**
	 * Creates an empty database.
	 */
    public Database() {
        documentList = new ArrayList<Document>();
    }
    /**
     * Adds one document to database. 
     * Throws IllegalArgumentException if doc is invalid. 
     * @param doc a document to be added
     */
    public void addDocument(Document doc) {
        //TODO add the document to database.
        Iterator<Document> itr = documentList.iterator();
        Document oldDoc = null;
        boolean match = false;
        while(itr.hasNext()){
        	oldDoc = itr.next();
        	if(oldDoc.getDocName().equals(doc.getDocName())){
        		match =true;
        	}
        }
        if(match)throw new IllegalArgumentException();
        else documentList.add(doc);
    }
    /**
     * Returns the list of documents in the database. 
     * @return the list of documents in the database. 
     */
    public List<Document> getDocumentList() {
        //TODO returns the document list.
        return documentList;
    }
    /**
     * Applies the given operation to the database. Returns the formatted 
     * string representation of the updated document content. 
     * @param operation the given operation to the database
     * @return the formatted string representation of the updated document content
     */
    public String update(Operation operation) {
        //TODO update the database return the updated document content.
        Document doc = getDocumentByDocumentName(operation.getDocName());
        doc.update(operation);
        return doc.toString();
    }
    /**
     * Returns one document based on document id. 
     * Returns null if there is no such document in the database. 
     * @param docName name of document
     * @return one document based on document id. 
     */
    public Document getDocumentByDocumentName(String docName) {
        //TODO return the document with the given document name.
    	Iterator<Document> itr = documentList.iterator();
        Document oldDoc = null;
        while(itr.hasNext()){
        	oldDoc = itr.next();
        	if(oldDoc.getDocName().equals(docName)){
        		return oldDoc;
        	}
        }
        return null;
    }

}
