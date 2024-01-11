package project_os;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		print_result();
	}
	
//	this function to get the result for each algrothim 
	private static  void print_result() {
//		define the iterations 
		int[] repetitions = { 100, 1000, 10000, 100000 };
		CPU cpu = new CPU();
		System.out
				.println("Iterations\tFCFS-ATT\tFCFS-AWT\t RR-ATT\t\tRR-AWT\t\tSRFT-ATT\tSRFT-AWT\tMLFQ-ATT\tMLFQ-AWT");
//		this for loop to move to all iterations
		for (int repetition : repetitions) {
//			generate process
			List<Process> processes = generateProcesses();
//			i use completableFuture from java for each Algorithm one for calculate turn around time and one to calculate wating time 
			CompletableFuture<Double> fcfsAttFuture = CompletableFuture.supplyAsync(() -> {
				List<Process> fcfsProcesses = new ArrayList<>(processes);
				cpu.fcfs(fcfsProcesses);
				return cpu.calculateAverageTurnaroundTime(fcfsProcesses);
			});

			CompletableFuture<Double> fcfsAwtFuture = CompletableFuture.supplyAsync(() -> {
				List<Process> fcfsProcesses = new ArrayList<>(processes);
				cpu.fcfs(fcfsProcesses);
				return cpu.calculateAverageWaitingTime(fcfsProcesses);
			});

			CompletableFuture<Double> srftAttFuture = CompletableFuture.supplyAsync(() -> {
				List<Process> srftProcesses = new ArrayList<>(processes);
				cpu.srft(srftProcesses);
				return cpu.calculateAverageTurnaroundTime(srftProcesses);
			});

			CompletableFuture<Double> srftAwtFuture = CompletableFuture.supplyAsync(() -> {
				List<Process> srftProcesses = new ArrayList<>(processes);
				cpu.srft(srftProcesses);
				return cpu.calculateAverageWaitingTime(srftProcesses);
			});

			CompletableFuture<Double> rrAttFuture = CompletableFuture.supplyAsync(() -> {
				List<Process> rrProcesses = new ArrayList<>(processes);
				cpu.rr(rrProcesses, 20);
				return cpu.calculateAverageTurnaroundTime(rrProcesses);
			});

			CompletableFuture<Double> rrAwtFuture = CompletableFuture.supplyAsync(() -> {
				List<Process> rrProcesses = new ArrayList<>(processes);
				cpu.rr(rrProcesses, 20);
				return cpu.calculateAverageWaitingTime(rrProcesses);
			});

			CompletableFuture<Double> mlfqAttFuture = CompletableFuture.supplyAsync(() -> {
				List<Process> mlfqProcesses = new ArrayList<>(processes);
				cpu.MultilevelFeedbackQueues(mlfqProcesses);
				return cpu.calculateAverageTurnaroundTime(mlfqProcesses);
			});

			CompletableFuture<Double> mlfqAwtFuture = CompletableFuture.supplyAsync(() -> {
				List<Process> mlfqProcesses = new ArrayList<>(processes);
				cpu.MultilevelFeedbackQueues(mlfqProcesses);
				return cpu.calculateAverageWaitingTime(mlfqProcesses);
			});

			try {
//				her i used .get() to get the result from CompletableFuture
				double fcfsAtt = fcfsAttFuture.get();
				double fcfsAwt = fcfsAwtFuture.get();
				double rrAtt = rrAttFuture.get();
				double rrAwt = rrAwtFuture.get();
				double srftAtt = srftAttFuture.get();
				double srftAwt = srftAwtFuture.get();
				double mlfqAtt = mlfqAttFuture.get();
				double mlfqAwt = mlfqAwtFuture.get();
//				print the result 
				System.out.println(repetition + "\t\t" + fcfsAtt + "\t\t" + fcfsAwt + "\t\t" + rrAtt + "\t\t" + rrAwt
						+ "\t\t" + +srftAtt + "\t\t" + srftAwt + "\t\t" + mlfqAtt + "\t\t" + mlfqAwt);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

	}
	

//	this function to generate process 
	private static List<Process> generateProcesses() {
		List<Process> processes = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < 8; i++) {
			int arrivalTime = i + 1; // assigning some order for arrival
			int cpuBurstTime = random.nextInt(96) + 5; // random CPU burst between 5 and 100
			processes.add(new Process(i + 1, arrivalTime, cpuBurstTime));
		}

		// sort processes based on arrival time
		processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
		return processes;
	}

}
