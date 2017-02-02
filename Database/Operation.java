public class Operation {
	// Enumeration of operator type.
	public enum OP {
		SET, //set,row,col,const -> set [row,col] to const
		CLEAR, //clear,row,col -> set [row,col] to 0
		ADD, //add,row,col,const -> add [row,col] by const
		SUB, //sub,row,col,const -> sub [row,col] by const
		MUL, //mul,row,col,const -> mul [row,col] by const
		DIV, //div,row,col,const -> div [row,col] by const
		UNDO, //undo the last operation
		REDO //redo the last undo
	}

	//TODO define some class variables.
	private String docName;
	private String userId;
	private OP op;
	private int rowIndex = -1;
	private int colIndex = -1;
	private int constant = -1;
	private long timestamp;

	/**
	 * This constructor is for operations that involve only one cell (i.e. 
	 * incrementing cell[rowIndex, colIndex] by a constant). Throws 
	 * IllegealArgumentException if op is not supported, or arguments 
	 * are not valid. 
	 */
	public Operation(String docName, String userId, OP op, int rowIndex, int
			colIndex, int constant, long timestamp) {
		//TODO constructor for operation class.
		if(docName == null) throw new IllegalArgumentException();
		this.docName = docName;
		if(userId == null) throw new IllegalArgumentException();
		this.userId = userId;
		if(op != OP.ADD && op != OP.SUB && op != OP.MUL && op != OP.DIV 
				&& op != OP.SET){
			throw new IllegalArgumentException();
		}
		this.op = op;
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		if(constant == -1 )throw new IllegalArgumentException();
		this.constant = constant;
		if(timestamp < 0)throw new IllegalArgumentException();
		this.timestamp = timestamp;
	}
	/**
	 * This constructor is for operations without constant(i.e. clear).Throws
	 * IllegealArgumentException if op is not supported, or arguments are not
	 * valid. 
	 * @param docName
	 * @param userId
	 * @param op
	 * @param rowIndex
	 * @param colIndex
	 * @param timestamp
	 */
	public Operation(String docName, String userId, OP op, int rowIndex, int
			colIndex, long timestamp) {
		if(docName == null) throw new IllegalArgumentException();
		this.docName = docName;
		if(userId == null) throw new IllegalArgumentException();
		this.userId = userId;
		if(op != OP.CLEAR){
			throw new IllegalArgumentException();
		}
		this.op = op;
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		if(timestamp < 0)throw new IllegalArgumentException();
		this.timestamp = timestamp;
	}
	/**
	 * This constructor is for operations that involve a document as a whole 
	 * (i.e. undo previous action).Throws IllegealArgumentException if op is 
	 * not supported, or arguments are not valid. 
	 * @param docName
	 * @param userId
	 * @param op
	 * @param timestamp
	 */
	public Operation(String docName, String userId, OP op, long timestamp) {
		if(docName == null) throw new IllegalArgumentException();
		this.docName = docName;
		if(userId == null) throw new IllegalArgumentException();
		this.userId = userId;
		if(op != OP.UNDO && op != OP.REDO){
			throw new IllegalArgumentException();
		}
		this.op = op;
		if(timestamp < 0)throw new IllegalArgumentException();
		this.timestamp = timestamp;
	}
	/**
	 * Returns the document Name. 
	 * @return the document Name. 
	 */
	public String getDocName() {
		return docName;
	}

	/**
	 * Returns the user ID. 
	 * @return the user ID. 
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 *  Returns the operator of this operation. 
	 * @return the operator of this operation. 
	 */
	public OP getOp() {
		return op;
	}
	/**
	 * Returns the row that is involved. 
	 * Returns -1 if this operation does not involve a cell. 
	 * @return the row that is involved
	 */
	public int getRowIndex() {
		return rowIndex;
	}
	/**
	 * Returns the column that is involved. 
	 * Returns -1 if this operation does not involve a cell. 
	 * @return the column that is involved
	 */
	public int getColIndex() {
		return colIndex;
	}

	/**
	 * Returns the timestamp of this operation. 
	 * @return the timestamp of this operation. 
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * Returns the constant that is involved. 
	 * Returns -1 if this operation does not involve a cell.
	 * @return the constant that is involved
	 */
	public int getConstant() {
		return constant;
	}
	/**
	 * Returns the string representation of this operation. 
	 * This method should be called for Operation Description part in output.
	 * @return 
	 */
	public String toString() {
		if(op == OP.ADD || op == OP.SUB || op == OP.MUL || op == OP.DIV 
				|| op == OP.SET){
			return timestamp+"\t"+docName+"\t"+userId+"\t"+op.name().toLowerCase()+"\t["+rowIndex+","+colIndex+"]\t"+constant;
		}
		else if(op == OP.CLEAR){
			return timestamp+"\t"+docName+"\t"+userId+"\t"+op.name().toLowerCase()+"\t["+rowIndex+","+colIndex+"]";
		}
		else {
			return timestamp+"\t"+docName+"\t"+userId+"\t"+op.name().toLowerCase();
		}	
		
	}
}
