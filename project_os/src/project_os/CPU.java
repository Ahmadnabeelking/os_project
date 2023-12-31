package project_os;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class CPU {

	public static void fcfs(List<Process> processes) {
		int currentTime = 0;
		for (Process process : processes) {
			process.startTime = Math.max(currentTime, process.arrivalTime);
			process.waitingTime = process.startTime - process.arrivalTime;
			process.turnaroundTime = process.waitingTime + process.cpuBurstTime;
			currentTime = process.startTime + process.cpuBurstTime;
		}
	}

	public static void rr(List<Process> processes, int timeQuantum) {
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
		int currentTime = 0;
		PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getCpuBurstTime));

		while (!processes.isEmpty() || !readyQueue.isEmpty()) {
			// Add arrived processes to the queue
			for (Process process : processes) {
				if (process.arrivalTime <= currentTime) {
					readyQueue.add(process);
				}
			}

			if (!readyQueue.isEmpty()) {
				Process running = readyQueue.poll();
				running.startTime = currentTime;

				// Run the process until completion or the next arrival
				int remainingBurst = Math.min(running.cpuBurstTime, processes.get(0).arrivalTime - currentTime);
				running.cpuBurstTime -= remainingBurst;
				currentTime += remainingBurst;

				if (running.cpuBurstTime > 0) {
					readyQueue.add(running); // Preempt if not finished
				} else {
					running.turnaroundTime = currentTime - running.arrivalTime;
					running.waitingTime = running.turnaroundTime - running.cpuBurstTime;
				}
			} else {
				currentTime++; // Idle CPU if no processes ready
			}
		}
	}


	public static void schedule(List<Process> processes) {
		int currentTime = 0;
		int NUM_QUEUES = 3;
		int[] TIME_QUANTA = { 10, 50, -1 };
		List<Queue<Process>> queues = new ArrayList<>(NUM_QUEUES);

		// Initialize queues
		for (int i = 0; i < NUM_QUEUES; i++) {
			queues.add(new LinkedList<>());
		}

		// Add initial processes to Q1
		for (Process process : processes) {
			if (process.arrivalTime <= currentTime) {
				queues.get(0).add(process);
			}
		}

		while (!processes.isEmpty() || !allQueuesEmpty(queues)) {
			// Find the highest priority non-empty queue
			int queueIndex = 0;
			while (queueIndex < NUM_QUEUES && queues.get(queueIndex).isEmpty()) {
				queueIndex++;
			}

			if (queueIndex < NUM_QUEUES) {
				Process running = queues.get(queueIndex).remove();
				running.startTime = currentTime;

				if (queueIndex < NUM_QUEUES - 1) { // RR for Q1 and Q2
					int remainingBurst = Math.min(running.cpuBurstTime, TIME_QUANTA[queueIndex]);
					running.cpuBurstTime -= remainingBurst;
					currentTime += remainingBurst;

					if (running.cpuBurstTime > 0) {
						queues.get(queueIndex + 1).add(running); // Move to next queue if not finished
					} else {
						running.turnaroundTime = currentTime - running.arrivalTime;
						running.waitingTime = running.turnaroundTime - running.cpuBurstTime;
					}
				} else { // FCFS for Q3
					while (running.cpuBurstTime > 0) {
						running.cpuBurstTime--;
						currentTime++;
					}
					running.turnaroundTime = currentTime - running.arrivalTime;
					running.waitingTime = running.turnaroundTime - running.cpuBurstTime;
				}
			} else {
				currentTime++; // Idle CPU if all queues are empty
			}
		}
	}

	private static boolean allQueuesEmpty(List<Queue<Process>> queues) {
		for (Queue<Process> queue : queues) {
			if (!queue.isEmpty()) {
				return false;
			}
		}
		return true;
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
