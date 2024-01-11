package project_os;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class CPU {

	public void fcfs(List<Process> processes) {
		int currentTime = 0;
//		this for loop about iteration 
		for (Process process : processes) {
			process.startTime = Math.max(currentTime, process.arrivalTime);// to calculate the start time
			process.waitingTime = process.startTime - process.arrivalTime;// to calculate watiting time of the process
			process.turnaroundTime = process.waitingTime + process.cpuBurstTime;// to calculate turn around time for the
																				// process
			currentTime = process.startTime + process.cpuBurstTime;// update the value of current time when the cpu is
																	// start to end
		}
	}

	public void rr(List<Process> processes, int quantum_time) {
		int currentTime = 0;
		int completed = 0;
		Queue<Process> queue = new LinkedList<>();
//		while loop for to work  all processes
		while (completed != processes.size()) {
			Process running = null;

			// add processes to the queue
			for (Process process : processes) {
				if (process.arrivalTime <= currentTime) {
					queue.add(process);
				}
			}

			// if there are processes in the queue
			if (!queue.isEmpty()) {
				running = queue.remove();

				// process will complete in this round this main that they erase the quantum
				// time form cpu burst
				if (running.cpuBurstTime <= quantum_time) {
					currentTime += running.cpuBurstTime;
					running.cpuBurstTime = 0;
					completed++;

					running.startTime = currentTime - running.cpuBurstTime;
					running.turnaroundTime = currentTime - running.arrivalTime;
					running.waitingTime = running.turnaroundTime - running.cpuBurstTime;
				} else {
					// process will not complete in this round
					currentTime += quantum_time;
					running.cpuBurstTime -= quantum_time;

					queue.add(running); // add back to queue
				}
			} else {
				// no processes in queue, idle cpu
				currentTime++;
			}
		}
	}

	public static void srft(List<Process> processes) {
//		first thing i use sort to sort the process for arrival time and cpu burst time 
		processes.sort((p1, p2) -> {
			if (p1.arrivalTime != p2.arrivalTime) {
				return Integer.compare(p1.arrivalTime, p2.arrivalTime);
			}
			return Integer.compare(p1.cpuBurstTime, p2.cpuBurstTime);
		});
		int currentTime = 0;
// 		her i calculate the waiting time and  turn around time for each process
		for (Process process : processes) {
			process.waitingTime = Math.max(0, currentTime - process.arrivalTime);
			currentTime += process.cpuBurstTime;
			process.turnaroundTime = process.waitingTime + process.cpuBurstTime;
		}
	}

	public void MultilevelFeedbackQueues(List<Process> processes) {
		LinkedList<Process> l1 = new LinkedList<>();
		LinkedList<Process> l2 = new LinkedList<>();
		LinkedList<Process> l3 = new LinkedList<>();
		int currentTime = 0;
//		i add the process in the first linked list 
		for (Process process : processes) {
			l1.add(process);
		}
//		her in this while loop i erase the quantum time for the burst time 
		while (!l1.isEmpty()) {
			Process currentProcess = l1.poll();
			currentProcess.startTime = Math.max(currentTime, currentProcess.arrivalTime);
			currentProcess.waitingTime = currentProcess.startTime - currentProcess.arrivalTime;

			if (currentProcess.cpuBurstTime <= 10) {
				currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.cpuBurstTime;
				currentTime = currentProcess.startTime + currentProcess.cpuBurstTime;
//			if the burst time is not complete i erase for burst time and add to linked list 2	
			} else {
				currentProcess.cpuBurstTime -= 10;
				currentTime = currentProcess.startTime + 10;
				l2.add(currentProcess);
				continue;
			}
		}

//		her in this while loop i erase the quantum time for the burst time 
		while (!l2.isEmpty()) {
			Process currentProcess = l2.poll();
			currentProcess.startTime = Math.max(currentTime, currentProcess.arrivalTime);
			currentProcess.waitingTime = currentProcess.startTime - currentProcess.arrivalTime;

			if (currentProcess.cpuBurstTime <= 50) {
				currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.cpuBurstTime;
				currentTime = currentProcess.startTime + currentProcess.cpuBurstTime;
			} else {
//				if the burst time is not complete i erase for burst time and add to linked list 3
				currentProcess.cpuBurstTime -= 50;
				currentTime = currentProcess.startTime + 50;
				l3.add(currentProcess);
				continue;
			}
		}

		// this the code for first come first serve(fcfs) the last level 
		while (!l3.isEmpty()) {
			Process currentProcess = l3.poll();
			currentProcess.startTime = Math.max(currentTime, currentProcess.arrivalTime);
			currentProcess.waitingTime = currentProcess.startTime - currentProcess.arrivalTime;
			currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.cpuBurstTime;
			currentTime = currentProcess.startTime + currentProcess.cpuBurstTime;
		}

	}

	
	public double calculateAverageTurnaroundTime(List<Process> processes) {
//		this methods to calculate the average turn around time 
		double totalTurnaroundTime = 0;
		for (Process process : processes) {
			totalTurnaroundTime += process.turnaroundTime;
		}
		if (processes.size() != 0) {
			double x = totalTurnaroundTime / processes.size();
			return x;
		} else {
			return 0;
		}
	}

	public double calculateAverageWaitingTime(List<Process> processes) {
//		this methods to calculate the average waiting time
		double totalWaitingTime = 0;
		for (Process process : processes) {
			totalWaitingTime += process.waitingTime;
		}
		if (processes.size() != 0) {
			double x = totalWaitingTime / processes.size();
			return x;
		} else {
			return 0;
		}
	}
}
