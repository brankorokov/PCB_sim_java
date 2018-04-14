import java.util.ArrayList;

public class CPU {

	/* Init ready and waiting queue */
	private ArrayList<Integer> readyQueue = new ArrayList<Integer>();
	private ArrayList<Integer> waitingQueue = new ArrayList<Integer>();
	private int currentProc;

	/* Constructor for CPU */
	public CPU(int x) {
		this.currentProc = x;
	}

	/* Getter for readyQueue */
	public ArrayList<Integer> getReady() {
		return this.readyQueue;
	}

	/* Getter for waitingQueue */
	public ArrayList<Integer> getWaiting() {
		return this.waitingQueue;
	}

	public void setCurrent() {
		if(this.readyQueue.isEmpty() == false) {
			this.currentProc = this.readyQueue.get(this.readyQueue.size() - 1);
		}
	}

	/* Getter for current Process */
	public int getCurrent() {
		return currentProc;
	}

	/* Creates a process */
	public void create(int ID) {
		this.readyQueue.add(0, ID);
	}

	/* Terminate a process */
	public void destroy() {
		if(this.readyQueue.isEmpty() == false) {
		this.readyQueue.remove(this.readyQueue.size() - 1);
		} else {
			System.out.println("Invalid process count");
		}
	}

	/* Method to adjust Lists when IO is requested */
	public void reqIO() {
		this.waitingQueue.add(0, currentProc);
		this.readyQueue.remove(this.readyQueue.size() - 1);
	}

	/* Method to adjust Lists when IO is complete */
	public void completed(int ID) {
		for (int i = 0; i < this.waitingQueue.size(); i++) {
			if (this.waitingQueue.get(i) == ID) {
				this.readyQueue.add(0,this.waitingQueue.get(i));
				this.waitingQueue.remove(i);
			}
		}
	}

	/* Method for timer interrupt */
	public void interrupt() {
		this.readyQueue.add(0, currentProc);
		this.currentProc = this.readyQueue.get(this.readyQueue.size() - 2);
		this.readyQueue.remove(this.readyQueue.size() - 1);

	}

	/* Method for wait system call */
	public void waitCall() {
		this.waitingQueue.add(0, this.currentProc);
		this.readyQueue.remove(this.readyQueue.size() - 1);
	}

	/* Method for PID signal */
	public void pidCall(int ID) {
		for (int i = 0; i < this.waitingQueue.size(); i++) {
			if (this.waitingQueue.get(i) == ID) {
				/*
				 * We already have a method that takes a value from the waiting queue and adds
				 * it to the ready queue. Why reinvent the wheel.
				 */
				this.completed(ID);
			}
		}
	}

	/* Method for graceful shutdown */
	public void shutDown() {
		this.readyQueue.clear();
		this.waitingQueue.clear();
		this.currentProc = 0;
	}

	/* Method converts Queues and Processes into Strings */
	public String toString() {
		String result = "";

		this.setCurrent();
		
        if (this.readyQueue.size() < 1) {
				result +="no process in ready";
				result += "\nCurrent Process: " + "error: Process queue empty :(";
	
		}
        else if (this.readyQueue.size() == 1) {
			result +="no process in ready queue";
			result += "\nCurrent Process: " + this.currentProc;
		}
        else {
			result += "Ready: [";
			for (int i = 0; i < this.readyQueue.size() - 2; i++) {
				result += this.readyQueue.get(i) + ",";
			}
			result += this.readyQueue.get(this.readyQueue.size() - 2) + "]\nCurrent Process: " + this.currentProc;
		}
		result += "\nWaiting: [";
		for (int i = 0; i < this.waitingQueue.size() - 1; i++) {
			result += this.waitingQueue.get(i) + ",";
		}
		if (this.waitingQueue.size() > 0) {
			result += this.waitingQueue.get(this.waitingQueue.size() - 1) + "]\n";
		} else {
			result += "]\n";
		}
		return result;
	}
}
