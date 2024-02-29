package logSetsProgram.logic;

public class Table {
	private String tableName;
	private Double highestRealTime;
	private String titleOfSet;
	
	public Table(String tableName, String titleOfSet) {
		// When creating table only set the name, -1 for saying no time found yet
		this.tableName = tableName;
		this.highestRealTime = -1.0;
		this.titleOfSet  = titleOfSet;
	}
	
	@Override
	public String toString() {
		// If the table was made but didnt get any times added
		if (this.highestRealTime == -1.0) {
			return this.tableName + " - NO REAL TIME WAS ADDED";
		} else {
			return this.tableName + " - Highest real time: " + this.highestRealTime;
		}
	}
	
	public Double getHighestRealTime() {
		return this.highestRealTime;
	}
	
	public void setHighestRealTime(Double time) {
		this.highestRealTime = time;
	}

}
