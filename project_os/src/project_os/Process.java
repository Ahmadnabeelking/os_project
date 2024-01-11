package project_os;

public class Process {
	int processid;
	int arrivalTime;
	int cpuBurstTime;
	int startTime;
	int waitingTime;
	int turnaroundTime;

	public Process(int processid, int arrivalTime, int cpuBurstTime) {
		this.processid = processid;
		this.arrivalTime = arrivalTime;
		this.cpuBurstTime = cpuBurstTime;
	}

	public int getProcessId() {
		return processid;
	}

	public void setProcessId(int processId) {
		this.processid = processId;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getCpuBurstTime() {
		return cpuBurstTime;
	}

	public void setCpuBurstTime(int cpuBurstTime) {
		this.cpuBurstTime = cpuBurstTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getTurnaroundTime() {
		return turnaroundTime;
	}

	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}

}
