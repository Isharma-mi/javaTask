package logSetsProgram.logic;

import java.util.Scanner;
import java.nio.file.Paths;

public class MainProgram {
	
	public static void main(String[] args) {
		// Create scanner for asking file name
		Scanner scanner = new Scanner(System.in);
		
		// To keep asking user for files
		while (true) {		
			System.out.println("------------------------------------------");
			// Ask and store a file name
			System.out.println("What is the file name? Input end to stop.");
			String fileName = scanner.nextLine();
			
			// Terminates program if user wants to
			if (fileName.equals("end")) {
				break;
			}

			// Tries to find file name given by user
			try (Scanner fileReader = new Scanner(Paths.get(fileName))) {	
				// Analyzes file
				analyzeFile(fileName, fileReader);
				// Creates new file after analysis
				fileReader.close();
			} catch (Exception e) {
				System.out.println("File could not be read!");
			}
			System.out.println();
		}
		scanner.close();
	}
	
	private static void analyzeFile(String fileName, Scanner fileReader) {
		TimeSets timeSets = new TimeSets();
		TimeSetData timeSetData = new TimeSetData();
		Table table = null;
		boolean foundTimeSet = false;
		boolean foundTable = false;
		
		// Loops thru file's lines
		while(fileReader.hasNextLine()) {
			// Gets current line it is reading
			String line = fileReader.nextLine();
			//System.out.println(line);
			
			// Checks if the line is the start of a new group of times 
			if (line.contains("*****The Start") && !line.contains(";")) {
				timeSetData.setTitle(line.substring(15, line.length() - 9));
				foundTimeSet = true;
			}
			
			// If in a time set
			if (foundTimeSet) {
				
				// Checks if line has a table created in it
				if (line.contains("NOTE: Table") 
						&& line.contains("row")
						&& line.contains("column")) {
					String tableName = createTableName(line);
					
					// Checks that table has already been made but we have found a new table
					if (!(table == null)) {
						// Adds already made table 
						timeSetData.addTable(table);
					}
					
					// Creates new table with table name
					table = new Table(tableName);
					foundTable = true;
				}
				
				// Check if the line has the time information
				if (line.contains("real time") || line.contains("cpu time")) {
					// Sets time that will be added to -1 in case error in processing
					Double timeInSeconds = -1.0;
					line = line.trim();

					// Checks if time is formatted as minutes:second.milliseconds
					if (line.contains(":")) {
						// Splits line to separate by minutes, seconds, and milliseconds
						String[] pieces = line.substring(20,line.length()).split(":|\\.");
						
						// Calculates the time in seconds
						timeInSeconds = Double.valueOf(pieces[0]) * 60 
								+ Double.valueOf(pieces[1]) 
								+ Double.valueOf(pieces[2]) * 0.01;
					} else {
						// Calculates the time in seconds if time already formatted as seconds.milliseconds
						timeInSeconds = Double.valueOf(line.substring(line.length() - 13 , line.length() - 8));
					}
					
					// Checks that found a table and looking at a real time
					if (foundTable && line.contains("real time") && !(table == null)) {
						// Checks that table's current highest real time is less than the new real time
						if (table.getHighestRealTime() < timeInSeconds) {
							// Updates highest real time
							table.setHighestRealTime(timeInSeconds);
						}
					}
					
					// Adds time to data
					timeSetData.addTime(timeInSeconds);
				}
			}
			
			// Checks if at the end of a time set
			if (line.contains("*****The End") && !line.contains(";") && foundTimeSet) {
				// Adds table when reaching the end of a set
				if (foundTable) {
					timeSetData.addTable(table);
					// Rests table to be null so that a table will be created with the correct set
					table = null;
				}
				// Sets to false since table will not carry over to next set
				foundTable = false;
				// Set to false since set has been completed
				foundTimeSet = false;
				
				// Organizes timeSetData' times and calculates total times
				timeSetData.organizeTimes();
				// Adds completed set info to list
				timeSets.add(timeSetData);
				// Resets current time set's info for next set				
				timeSetData = new TimeSetData();
			}
		}
		// Creates file
		FileManager fileManager = new FileManager();
		fileManager.createFile(fileName, timeSets);
	}
	
	private static String createTableName(String tableCreationLine) {
		// Gets index from where the table's name begins
		int startIndexOfTableName = tableCreationLine.indexOf("Table") + 6;
		// Gets index to where the table's name ends
		int endIndexOfTableName = tableCreationLine.indexOf("created") - 1;
		// Adds tables name to list
		String tableName = tableCreationLine.substring(startIndexOfTableName, endIndexOfTableName);
		
		return tableName;
	}
}
