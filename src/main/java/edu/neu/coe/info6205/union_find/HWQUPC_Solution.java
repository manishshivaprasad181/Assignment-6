package edu.neu.coe.info6205.union_find;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Random;

import javax.swing.JFrame;

public class HWQUPC_Solution {

	private static int solve(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n must be a positive integer");
		}

		int no_connections = 0;
		int no_pairs = 0;

		UF_HWQUPC uf = new UF_HWQUPC(n);
		while (uf.components() != 1) {
			int x = (int) (Math.random() * (n));
			int y = (int) (Math.random() * (n));
			if (x == y) {
				continue; 
			}
			no_pairs += 1;
			if (!uf.connected(x, y)) {
				uf.union(x, y);
				no_connections++;
			}
		}

		System.out.println("Number of connections: " + no_connections);
		System.out.println("Number of pairs: " + no_pairs);

		return no_pairs;
	}

	public static void main(String[] args) {
		int n = 408;
		int numTrials = 7;

		for (int i = 0; i < numTrials; i++) {
			System.out.println("Number of nodes: " + n);
				solve(n);

			n *= 2;
		}

	}

}