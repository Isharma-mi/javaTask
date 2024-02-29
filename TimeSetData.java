package logSetsProgram.logic;

import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class TimeSetData {
	private List<Double> allTimes;
	private List<Double> realTimes;
	private List<Double> cpuTimes;
	private double totalOverallTime;
	private double totalRealTime;
	private double totalCPUTime;
	private String titleOfSet;
	
	public TimeSetData () {
		// Intializes instance vars
		this.allTimes = new ArrayList<>();
		this.realTimes = new ArrayList<>();
		this.cpuTimes = new ArrayList<>();
		this.totalOverallTime = 0;
		this.totalRealTime = 0;
		this.totalCPUTime = 0;
		this.titleOfSet = "";
	}
	
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return "------------------------------------"
				+ "\n" + this.titleOfSet
				+ "\nOverall time: " + df.format(this.totalOverallTime) + " seconds"
				+ "\nReal time: " + df.format(this.totalRealTime) + " seconds"
				+ "\nCPU time: " + df.format(this.totalCPUTime) + " seconds";
	}
	
	public String getTitle() {
		return this.titleOfSet;
	}
	
	public List<Double> getAllTimes() {
		return this.allTimes;
	}
	
	public void setTitle(String title) {
		this.titleOfSet = title;
	}
	
	public void addTime(Double time) {
		this.allTimes.add(time);
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
}
