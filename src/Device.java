import java.util.ArrayList;

public class Device {
	/* Set waiting queue */
	private ArrayList<Integer> deviceQueue = new ArrayList<Integer>();
	private int currentProc;

	/* ID of device */
	public int deviceID;

	/* Constructor for device */
	public Device(int x) {
		this.deviceID = x;
	}

	/* Setter for currentProc */
	public void setCurrentProc() {
		this.currentProc = this.deviceQueue.get(this.deviceQueue.size() - 1);
	}

	/* Getter for the current running process */
	public int getCurrentProc() {
		this.currentProc = this.deviceQueue.get(this.deviceQueue.size() - 1);
		return currentProc;
	}

	/* Method to add a process to the waiting queue */
	public void addProc(int proc) {
		
		deviceQueue.add(0,proc);
	}

	/* Getter for the list of processes */
	public ArrayList<Integer> getList() {
		return this.deviceQueue;
	}

	/* Getter for the device ID */
	public int getID() {
		return this.deviceID;
	}

	/* Setter for device ID */
	public void setID(int x) {
		this.deviceID = x;
	}

	/* Method for completed I/O */
	public void completeIO() {
		this.deviceQueue.remove(this.deviceQueue.size() - 1);
	}

	/* Method for the graceful shutdown */
	public void shutDown() {
		this.deviceQueue.clear();
		this.currentProc = 0;
	}

	/* Converts Device contents into a String */
	public String toString() {

		this.getCurrentProc();
		String result = "Device ID: " + deviceID + ". Current Process: " + this.currentProc + "\nDevice Queue: [";

		for (int i = 0; i < this.deviceQueue.size() - 2; i++) {
			result += this.deviceQueue.get(i) + ",";
		}
		if (this.deviceQueue.size() > 1) {
			result += this.deviceQueue.get(this.deviceQueue.size() - 2) + "]\n";
		} else {
			result += "]\n";
		}
		return result;
	}
}
