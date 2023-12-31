package project_os;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] repetitions = { 100, 1000, 10000, 100000 };
		CPU cpu = new CPU();
		System.out.println("\t\tATT \t\t\tAWT");

		for (int repetition : repetitions) {
			double totalAtt = 0;
			double totalAwt = 0;
			double averageAtt = 0;
			double averageAwt = 0;
			
			for (int i = 0; i < repetition; i++) {
				List<Process> processes = generateProcesses();
				//CPU.rr(processes,20);
				CPU.schedule(processes);
				//CPU.srft(processes);
				totalAtt += cpu.calculateAverageTurnaroundTime(processes);
				totalAwt += cpu.calculateAverageWaitingTime(processes);

			}
			

			 averageAtt = totalAtt / repetition;
			 averageAwt = totalAwt / repetition;

			System.out.println(repetition + "\t\t" + averageAtt + "\t\t" + averageAwt);
		}
	}

	private static List<Process> generateProcesses() {
		List<Process> processes = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < 8; i++) {
			int arrivalTime = i+1; // Assigning some order for arrival
			int cpuBurstTime = random.nextInt(96) + 5; // Random CPU burst between 5 and 100
			processes.add(new Process(i + 1, arrivalTime, cpuBurstTime));
		}

		// Sort processes based on arrival time
		processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
		return processes;
	}
	
	
}
