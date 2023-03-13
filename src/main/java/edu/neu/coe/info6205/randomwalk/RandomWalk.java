// Completed by Pranav Kapoor

package edu.neu.coe.info6205.randomwalk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class RandomWalk {

	private int xcoord = 0;
	private int ycoord = 0;

	private final Random random = new Random();

	/**
	 * Private method to move the current position, that's to say the drunkard moves
	 *
	 * @param dx the distance he moves in the x direction
	 * @param dy the distance he moves in the y direction
	 */
	private void move(int dx, int dy) {
		// FIXME do move by replacing the following code
		xcoord += dx;
		ycoord += dy;
		// END
	}

	/**
	 * Perform a random walk of m steps
	 *
	 * @param m the number of steps the drunkard takes
	 */
	private void randomWalk(int m) {
		for (int i = 0; i < m; i++) {
			randomMove();
		}
		// FIXME
		// END
	}

	/**
	 * Private method to generate a random move according to the rules of the
	 * situation. That's to say, moves can be (+-1, 0) or (0, +-1).
	 */
	private void randomMove() {
		boolean ns = random.nextBoolean();
		int step = random.nextBoolean() ? 1 : -1;
		move(ns ? step : 0, ns ? 0 : step);
	}

	/**
	 * Method to compute the distance from the origin (the lamp-post where the
	 * drunkard starts) to his current position.
	 *
	 * @return the (Euclidean) distance from the origin to the current position.
	 */
	public double distance() {
		// FIXME by replacing the following code

		return Math.sqrt(Math.pow(xcoord, 2) + Math.pow(ycoord, 2));
		// END
	}
	
	/**
	 * Perform multiple random walk experiments, returning the mean distance.
	 *
	 * @param m the number of steps for each experiment
	 * @param n the number of experiments to run
	 * @return the mean distance
	 */

	public static double randomWalkMulti(int m, int n) {

		double totalDistance = 0;

		List<Double> totalDistList = new ArrayList<Double>();
		
		for (int i = 0; i < n; i++) { // run 10 experiments
			RandomWalk walk = new RandomWalk();
			walk.randomWalk(m);
			totalDistance = totalDistance + walk.distance(); // 1 experiment for 10 steps
			totalDistList.add(totalDistance);
			totalDistance = 0;

		}

		return totalDistList.stream().mapToDouble(Double::longValue).sum() / n; // 10 steps 10 experiments gives you the mean
																		// expected distance.
	}

	private static void graph(List<Integer> x, List<Double> y) {

		XYSeries series = new XYSeries("Line Graph Example");

		for (int i = 0; i < x.size(); i++) {
			int xval = x.get(i);
			double yval = y.get(i);
			series.add(xval, yval);
		}

		XYDataset dataset = new XYSeriesCollection(series);

		JFreeChart chart = ChartFactory.createXYLineChart("Line Graph Example", "steps", "DistanceTravelled", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		ChartPanel panel = new ChartPanel(chart);
		JFrame frame = new JFrame("Line Graph Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
	
	public static void constructGraph(int x,int y) {
		List<Integer> m = new ArrayList<Integer>();

		for (int u = 0; u < x; u++) { // adding 0 to 50 steps
			m.add(u);
		}
		
		int noOfExpe=y;
		
		double meanDistance=0;
		
		List<Integer> steps = new ArrayList<Integer>();
		List<Double> meanDist = new ArrayList<Double>();

		for (int a : m) {
			steps.add(a);
			meanDistance = randomWalkMulti(a, noOfExpe);
			meanDist.add(meanDistance);
			System.out.println(a + " steps: " + meanDistance + " over " + n + " experiments");
			meanDistance = 0;
		}
		


		List<Integer> xaxis = new ArrayList<Integer>();
		List<Double> yaxis = new ArrayList<Double>();

		for (int a : m) {
			xaxis.add(a);
			yaxis.add(0.9 * Math.sqrt(a));
		}

		RandomWalk.graph(steps, meanDist); // plotting the experimental graph

		RandomWalk.graph(xaxis, yaxis); // plotting the ideal graph

		
	}
	public static void main(String[] args) {

		if (args.length == 0)
			throw new RuntimeException("Syntax: RandomWalk steps [experiments]");

		int singlestep = 0;

		singlestep = Integer.parseInt(args[0]);


		int n = -1;

		if (args.length > 1)
			n = Integer.parseInt(args[1]);
		
		constructGraph(singlestep,n);

		double meanDistance = randomWalkMulti(singlestep,n);
		
		System.out.println(singlestep + " steps: " + meanDistance + " over " + n + " experiments");
	}


	}