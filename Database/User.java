public class User {
    //TODO data field
	private String userId;
	private SimpleStack<WAL> undoStack;
	private SimpleStack<WAL> redoStack;
	/**
	 * Creates a User. Throws IllegalArgumentException is userId is invalid. 
	 * @param userId the Id of user
	 */
    public User(String userId) {
        //TODO constructor of User class.
    	if(userId ==null)throw new IllegalArgumentException();
        this.userId = userId;
        undoStack = new SimpleStack<WAL>();
    	redoStack = new SimpleStack<WAL>();
    }
    /**
     * Returns the top WAL for undo. If there is no such WAL, returns null. 
     * @return the top WAL for undo
     */
    public WAL popWALForUndo() {
        //TODO remove one undo WAL.
    	if(undoStack.size()==0)return null;
    	return undoStack.pop();
    }

    public WAL popWALForRedo() {
        //TODO remove one redo WAL
    	if(redoStack.size()==0)return null;
    	return redoStack.pop();
    }
    /**
     * Pushes the WAL into undo stack. Throws IllegalArgumentException if wal is null.
     * @param trans
     */
    public void pushWALForUndo(WAL trans) {
        //TODO push WAL for undo
    	if(trans == null) throw new IllegalArgumentException();
    	undoStack.clear();
    	undoStack.push(trans);
    }
    /**
     * Pushes the WAL into redo stack. Throws IllegalArgumentException if wal is null.
     * @param trans
     */
    public void pushWALForRedo(WAL trans) {
        //TODO push WAL for redo
    	if(trans == null) throw new IllegalArgumentException();
    	redoStack.clear();
    	redoStack.push(trans);
    }
    /**
     * Clear all redo WALs. 
     */
    public void clearAllRedoWAL() {
       redoStack.clear();
    }
    /**
     * Clear all undo WALs. 
     */
    public void clearAllUndoWAL() {
        //TODO clear all undo WALs.
       undoStack.clear();
    }
    /**
     * Returns the user id. 
     * @return the user id. 
     */
    public String getUserId() {
        //TODO return user id.
        return userId;
    }
}
