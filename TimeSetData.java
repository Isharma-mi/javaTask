package logSetsProgram.logic;

import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class TimeSetData {
	private double totalOverallTime;
	private double totalRealTime;
	private double totalCPUTime;
	private List<Double> allTimes;
	private List<Double> realTimes;
	private List<Double> cpuTimes;
	private List<Table> tables;
	private List<Table> longRealTimeTables;
	private String titleOfSet;
	
	public TimeSetData () {
		// Intializes instance vars
		this.allTimes = new ArrayList<>();
		this.realTimes = new ArrayList<>();
		this.cpuTimes = new ArrayList<>();
		this.tables = new ArrayList<>();
		this.longRealTimeTables = new ArrayList<>();
		this.totalOverallTime = 0;
		this.totalRealTime = 0;
		this.totalCPUTime = 0;
		this.titleOfSet = "";
	}
	
	@Override
	public String toString() {
		// Used to round times tod decimal points
		DecimalFormat df = new DecimalFormat("0.00");
		
		return "------------------------------------"
				+ "\n" + this.titleOfSet 
				+ "\nTimes:" 
				+ "\n\tOverall time: " + df.format(this.totalOverallTime) + " seconds"
				+ "\n\tReal time: " + df.format(this.totalRealTime) + " seconds"
				+ "\n\tCPU time: " + df.format(this.totalCPUTime) + " seconds"
				+ "\nTables:\n" + this.indentedTableInfo(this.getTablesInfo());
		
	}
	
	public List<Double> getAllTimes() {
		return this.allTimes;
	}
	
	public String getTitle() {
		return this.titleOfSet;
	}
	
	public List<Table> getLongRealTimeTables() {
		return this.longRealTimeTables;
	}
	
	public String tableOfLongRealTimeTables(int spacing) {
		// Will store the the set's name, table's name, highest real time of table
		StringBuilder output = new StringBuilder();
		// Only goes thru tables if they aren't empty
		if (!this.longRealTimeTables.isEmpty()) {
			// Loops thru each longRealTimeTable in the set
			for (int i = 0; i < this.longRealTimeTables.size(); i++) {
				// Creates string with title of set, title of table, and highest time
				String longTimeTableInfo = String.format("%-" + spacing + "s %-" + spacing + "s %-" + spacing + "s"
														, this.titleOfSet
														, this.longRealTimeTables.get(i).getTableName()
														, this.longRealTimeTables.get(i).getHighestRealTime() + " seconds");
				// Adds formatted string to output
				output.append(longTimeTableInfo+"\n");
			}
		} 
		// Returns output
		return output.toString();
	}
	
	public void setTitle(String title) {
		this.titleOfSet = title;
	}	
	
	public void addTime(Double time) {
		this.allTimes.add(time);
	}
	
	public void addTable(Table table) {
		tables.add(table);
		
		// Checks if the table took at least one minute
		if (table.getHighestRealTime() >= 60.0) {
			this.longRealTimeTables.add(table);
			//System.out.println(table);
		}
	}
		
	public void organizeTimes() {
		// Loops thru all the times
		for (int i = 0; i < this.allTimes.size(); i++) {
			// Checks if the time is a real time or a cpu time
			if (i % 2 == 0) {
				this.realTimes.add(this.allTimes.get(i));	
			} else {
				this.cpuTimes.add(this.allTimes.get(i));
			}
		}
		
		this.calculateTotalTimes();
	}
		
	public String getTablesInfo() {
		// Set tablesInfo to no tables being found
		String tablesInfo = "NO TABLES FOUND";
		
		if (!this.tables.isEmpty()) {
			// Update tablesInfo to have information of each table
			tablesInfo = this.tables.stream()
					.map(t -> t.toString())
					.reduce("", (previousString, newValue) -> previousString + newValue + "\n");
		}

		// Returns each tables info on a separate line
		return tablesInfo;
	}

	private void calculateTotalTimes() {
		// Gets the totalOverAllTime
		this.totalOverallTime = this.allTimes.stream()
				.mapToDouble(d -> d)
				.sum();
		// Calculate total real time
		this.totalRealTime = this.realTimes.stream()
				.mapToDouble(d -> d)
				.sum();
		// Calculates total cpu time
		this.totalCPUTime = this.cpuTimes.stream()
				.mapToDouble(d -> d)
				.sum();
	}
	
	private String indentedTableInfo(String tableInfo) {
		// Takes a string and splits by each line
		String pieces[] = tableInfo.split("\\n");
		// Use StringBuilder to combine strings
		StringBuilder indentedTableInfo = new StringBuilder();
		
		for (String s: pieces) {
			// Adds each tables info on sep lines with indent 
			indentedTableInfo.append("\t" + s + "\n");
		}
		// Returns indented info
		return indentedTableInfo.toString();
	}
	
}
