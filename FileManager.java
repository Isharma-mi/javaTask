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
		// NEED TO: Insert writeToFileMethod
		writeToFile(timeSet);
	}
	
	public void writeToFile(TimeSets timeSet) {
		// Tries to locate file we want to write on
		try (PrintWriter printWriter = new PrintWriter(this.fileName)) {
			timeSet.getTimeSets().stream()
				.forEach(t -> printWriter.println(t));
		} catch (Exception e) {
			System.out.println("Could not write to file");
		}
	}
	
	
}
