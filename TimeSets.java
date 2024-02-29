package logSetsProgram.logic;

import java.util.List;
import java.util.ArrayList;

public class TimeSets {
	// List to store all the TimeSetData objects
	private List<TimeSetData> timeSets;
	
	public TimeSets() {
		this.timeSets = new ArrayList<>();
	}
	
	public void add(TimeSetData timeSet) {
		// Adds a specific TimeSetData obj to list 
		this.timeSets.add(timeSet);
	}
	
	public List<TimeSetData> getTimeSets() {
		// Returns all TimeSetData objs added to list
		return this.timeSets;
	}
}
