package logSetsProgram.logic;

import java.io.PrintWriter;
import java.io.File;

public class FileManager {
	private String fileName;
	
	public FileManager () {
		this.fileName = "";
	}
	
	public void createFile(String fileName, TimeSets timeSet) {
		// Updates file's name
		this.fileName = "CLEANED_" + fileName;
		// Creates a new file with the file name given
		File newFile = new File(this.fileName);
		writeToFile(timeSet);
	}
	
	public void writeToFile(TimeSets timeSet) {
		// Tries to locate file we want to write on
		try (PrintWriter printWriter = new PrintWriter(this.fileName)) {
			// Writes out each TimeSetData's information (Name of TimeSetData, times, tables with highest real times)
			timeSet.getTimeSets().stream()
				.forEach(t -> printWriter.println(t));
			
			
			// Below relates to printing out the long real times that were longer than a minute associated with the tables in each set
			// Spacing between each column of the table
			int spacing = 30;
			// Creates header information at top of table
			String tableHeader = String.format("%-"+ spacing + "s %-"+ spacing +"s %-" + spacing +"s", 
												"Title of Set", "Title of Table", "Highest Real Time");
			// Prints out header information
			printWriter.println(tableHeader);
			// Goes thru each time set
			for (TimeSetData timeSetData: timeSet.getTimeSets()) {
				// Writes the longRealTimeTable information
				printWriter.print(timeSetData.tableOfLongRealTimeTables(spacing));
			}
		} catch (Exception e) {
			System.out.println("Could not write to file");
		}
	}	
}
