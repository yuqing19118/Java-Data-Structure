public class WAL {
   private int rowIndex;
   private int colIndex;
   private int oldValue;
   
	/*Creates a WAL, which records what the old value is before an operation. 
	Throws IllegalArgumentException if any argument is invalid. 
	*/
    public WAL(int rowIndex, int colIndex, int oldValue) {
       this.rowIndex=rowIndex;
       this.colIndex=colIndex;
       this.oldValue=oldValue;
    }
    
    //Returns the old value. 
    public int getOldValue() {
       return oldValue;
    }
    //Returns the row value. 
    public int getRowIndex() {
        return rowIndex;
     }
     //Returns the col value. 
    public int getColIndex() {
       return colIndex;
    }

}
