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
		boolean foundTimeSet = false;
		
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
				// Check if the line has the time information
				if (line.contains("real time") || line.contains("cpu time")) {
					line = line.trim();
					// Checks if time is formatted as minutes:second.milliseconds
					if (line.contains(":")) {
						// Splits line to separate by minutes, seconds, and milliseconds
						String[] pieces = line.substring(20,line.length()).split(":|\\.");
						
						// Calculates the time in seconds
						Double timeInSeconds = Double.valueOf(pieces[0]) * 60 
								+ Double.valueOf(pieces[1]) 
								+ Double.valueOf(pieces[2]) * 0.01;
						timeSetData.addTime(timeInSeconds);
						
					} else {
						// Converts the line to a double if its already formatted as seconds.milliseconds
						Double timeInSeconds = Double.valueOf(line.substring(line.length() - 13 , line.length() - 8));
						// Adds time to data
						timeSetData.addTime(timeInSeconds);	
					}
				}
			}
			
			// Checks if at the end of a time set
			if (line.contains("*****The End") && !line.contains(";") && foundTimeSet) {
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
	}