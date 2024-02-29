package logSetsProgram.logic;

import java.io.PrintWriter;
import java.io.File;
import javax.swing.JTable;


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
		// NEED TO: Insert writeToFileMethod
		writeToFile(timeSet);
	}
	
	public void writeToFile(TimeSets timeSet) {
		// Tries to locate file we want to write on
		try (PrintWriter printWriter = new PrintWriter(this.fileName)) {
			// Writes out each TimeSetData's information (Name of TimeSetData, times, tables with highest real times)
			timeSet.getTimeSets().stream()
				.forEach(t -> printWriter.println(t));
			
			timeSet.getTimeSets().stream() // Gets each time set
				.map(t -> t.getLongRealTimeTables()) // Gets all the longtime tables
				.filter(t -> !t.isEmpty()) // Gets the time tables that are not empty
				.forEach(t -> printWriter.println(t));
				
			// NEED TO: Write out TimeSetData timings that took more than a minute
		} catch (Exception e) {
			System.out.println("Could not write to file");
		}
	}	
}
