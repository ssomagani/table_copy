import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class CopyTables extends VoltProcedure {

	private final SQLStmt GET_KILL_FLAG = new SQLStmt("select value from flags where flag='kill_copy_tables'");
	private final SQLStmt GET = new SQLStmt("select * from source order by id offset ? limit 1");
	private final SQLStmt PUT = new SQLStmt("insert into dest values (?, ?)");
	
	private final SQLStmt EXPORT = new SQLStmt("insert into LOOPBACK_STREAM values (?)");
	
	public VoltTable[] run(int offset) {
		
		voltQueueSQL(GET_KILL_FLAG);
		VoltTable flagTable = voltExecuteSQL()[0];
		if(flagTable.advanceRow()) {
			int killCopy = (int) flagTable.getLong(0);
			if(killCopy == 1)
				return null;
		} else {
			return null;
		}
		
		voltQueueSQL(GET, offset);
		VoltTable[] sourceRowsArray = voltExecuteSQL();
		if(sourceRowsArray != null && sourceRowsArray.length > 0) {
			VoltTable sourceRows = sourceRowsArray[0];
			if(sourceRows.getRowCount() != 0) {
				while(sourceRows.advanceRow()) {
					voltQueueSQL(PUT, (int) sourceRows.getLong(0), sourceRows.getString(1));
				}
				voltQueueSQL(EXPORT, (offset + 1));
				return voltExecuteSQL();
			}
		}
		return null;
	}
}
