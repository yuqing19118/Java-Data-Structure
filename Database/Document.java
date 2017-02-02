import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;



public class Document {
	private String docName;
	private int rowSize;
	private int colSize;
	private int[][] arr;
	private List<User> userList= new ArrayList<User> ();

	public Document(String docName, int rowSize, int colSize, List<User>
	userList) {
		if(docName == null)throw new IllegalArgumentException();
		if (rowSize < 0 || colSize < 0) throw new IllegalArgumentException();
		if(userList == null)throw new IllegalArgumentException();
		this.docName=docName;
		this.rowSize=rowSize;
		this.colSize=colSize;
		this.userList=userList;
		arr = new int[rowSize][colSize];
		for(int i = 0; i< rowSize;i++){
			for(int j = 0; j<colSize;j++){
				arr[i][j] = 0;
			}
		}
	}
	/**
	 * Applies the input operation to the document. Throws a IllegalArgumentException if the operation is not valid. 
	 * @param operation the input operation to the document.
	 */
	public void update(Operation operation) {
		//TODO update the database.
		User user = getUserByUserId(operation.getUserId());
		WAL wal = null;
		Operation.OP op = operation.getOp();
		if(op == Operation.OP.ADD || op == Operation.OP.SUB || op == Operation.OP.MUL || op == Operation.OP.DIV 
				|| op == Operation.OP.SET){
			int rowIndex = operation.getRowIndex();
			int colIndex = operation.getColIndex();
			int constant = operation.getConstant();
			wal = new WAL(rowIndex,colIndex,arr[rowIndex][colIndex]);
			user.pushWALForUndo(wal);
			if(op == Operation.OP.ADD ){
				arr[rowIndex][colIndex] += constant; 
				
			}
			if(op == Operation.OP.SUB){
				arr[rowIndex][colIndex] -= constant; 
			}
			if(op == Operation.OP.MUL){
				arr[rowIndex][colIndex] *= constant; 
			}
			if( op == Operation.OP.DIV ){
				arr[rowIndex][colIndex] /= constant; 
			}
			if(op == Operation.OP.SET){
				arr[rowIndex][colIndex] = constant; 
			}
		}
		else if(op == Operation.OP.UNDO){
			wal = user.popWALForUndo();
			WAL newWal = new WAL(wal.getRowIndex(),wal.getColIndex(),arr[wal.getRowIndex()][wal.getColIndex()]);
			arr[wal.getRowIndex()][wal.getColIndex()] = wal.getOldValue();
			user.pushWALForRedo(newWal);
		}
		else if(op == Operation.OP.REDO){
			wal = user.popWALForRedo();
			WAL newWal = new WAL(wal.getRowIndex(),wal.getColIndex(),arr[wal.getRowIndex()][wal.getColIndex()]);
			arr[wal.getRowIndex()][wal.getColIndex()] = wal.getOldValue();
			user.pushWALForUndo(newWal);
		}
		else if(op == Operation.OP.CLEAR){
			int rowIndex = operation.getRowIndex();
			int colIndex = operation.getColIndex();
			wal = new WAL(rowIndex,colIndex,arr[rowIndex][colIndex]);
			arr[rowIndex][colIndex] = 0; 
			user.pushWALForUndo(wal);
		}
	}

	public String getDocName() {
		return docName;
	}

	private User getUserByUserId(String userId) {
		User findUser = null;
		boolean isFound = false;
		Iterator<User> itr = userList.iterator();
		while(itr.hasNext()){
			findUser = itr.next();
			if(findUser.getUserId().equals(userId)){
				isFound = true;
				break;
			}
		}
		if(isFound) return findUser;
		else return null;
	}

	public int getCellValue(int rowIndex, int colIndex){
		if (rowIndex < 0 || colIndex < 0 || rowIndex>= rowSize || colIndex >=colSize ) 
			throw new IllegalArgumentException();
		return arr[rowIndex][colIndex];
	}

	public String toString() {
		//TODO return the formatted string representation of the document.
		String retval =  "Document Name: "+getDocName()+"\tSize: ["+rowSize+","+colSize+"]\nTable:\n";
		for(int i = 0; i< rowSize;i++){
			for(int j = 0; j<colSize;j++){
				if(j<colSize-1) retval+=getCellValue(i,j)+"\t";
				if(j == colSize - 1) retval+=getCellValue(i,j)+"\n";
			}
		}
		return retval;
		 
	}
}
