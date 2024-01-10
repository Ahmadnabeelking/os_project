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
		for (Process process : processes) {
			process.startTime = Math.max(currentTime, process.arrivalTime);
			process.waitingTime = process.startTime - process.arrivalTime;
			process.turnaroundTime = process.waitingTime + process.cpuBurstTime;
			currentTime = process.startTime + process.cpuBurstTime;
		}
	}

	public  void rr(List<Process> processes, int timeQuantum) {
		int currentTime = 0;
		int completed = 0;
		Queue<Process> readyQueue = new LinkedList<>();
		while (completed != processes.size()) {
			Process running = null;

			// Add processes that have arrived to the ready queue
			for (Process process : processes) {
				if (process.arrivalTime <= currentTime) {
					readyQueue.add(process);
				}
			}

			// If there are processes in the ready queue
			if (!readyQueue.isEmpty()) {
				running = readyQueue.remove();

				if (running.cpuBurstTime <= timeQuantum) {
					// Process will complete in this round
					currentTime += running.cpuBurstTime;
					running.cpuBurstTime = 0;
					completed++;

					running.startTime = currentTime - running.cpuBurstTime;
					running.turnaroundTime = currentTime - running.arrivalTime;
					running.waitingTime = running.turnaroundTime - running.cpuBurstTime;
				} else {
					// Process will not complete in this round
					currentTime += timeQuantum;
					running.cpuBurstTime -= timeQuantum;

					readyQueue.add(running); // Add back to ready queue
				}
			} else {
				// No processes in ready queue, idle CPU
				currentTime++;
			}
		}
	}

	public static void srft(List<Process> processes) {
		processes.sort((p1, p2) -> {
			if (p1.arrivalTime != p2.arrivalTime) {
				return Integer.compare(p1.arrivalTime, p2.arrivalTime);
			}
			return Integer.compare(p1.cpuBurstTime, p2.cpuBurstTime);
		});
		int currentTime = 0;

		for (Process process : processes) {
			process.waitingTime = Math.max(0, currentTime - process.arrivalTime);
			currentTime += process.cpuBurstTime;
			process.turnaroundTime = process.waitingTime + process.cpuBurstTime;
		}
	}

	public void MultilevelFeedbackQueues(List<Process> processes) {
		 LinkedList<Process> q1 = new LinkedList<>();
	        LinkedList<Process> q2 = new LinkedList<>();
	        LinkedList<Process> q3 = new LinkedList<>();
	        int currentTime = 0;
	        for (Process process : processes) {
	            q1.add(process);
	        }
	        while (!q1.isEmpty()) {
				Process currentProcess = q1.poll();
				currentProcess.startTime = Math.max(currentTime, currentProcess.arrivalTime);
				currentProcess.waitingTime = currentProcess.startTime - currentProcess.arrivalTime;

				if (currentProcess.cpuBurstTime <= 10) {
					currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.cpuBurstTime;
					currentTime = currentProcess.startTime + currentProcess.cpuBurstTime;
					
				}else{
					currentProcess.cpuBurstTime -= 10;
					currentTime = currentProcess.startTime + 10;
					q2.offer(currentProcess);
					continue;
				}
			}

	     // Process Q2
			while (!q2.isEmpty()) {
				Process currentProcess = q2.poll();
				currentProcess.startTime = Math.max(currentTime, currentProcess.arrivalTime);
				currentProcess.waitingTime = currentProcess.startTime - currentProcess.arrivalTime;

				if (currentProcess.cpuBurstTime <= 50) {
					currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.cpuBurstTime;
					currentTime = currentProcess.startTime + currentProcess.cpuBurstTime;
				}else{
					currentProcess.cpuBurstTime -= 50;
					currentTime = currentProcess.startTime + 50;
					q3.offer(currentProcess);
					continue;
				}
			}

			// Process Q3
			while (!q3.isEmpty()) {
				Process currentProcess = q3.poll();
				currentProcess.startTime = Math.max(currentTime, currentProcess.arrivalTime);
				currentProcess.waitingTime = currentProcess.startTime - currentProcess.arrivalTime;
				currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.cpuBurstTime;
				currentTime = currentProcess.startTime + currentProcess.cpuBurstTime;
			}

	}

	public double calculateAverageTurnaroundTime(List<Process> processes) {
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
