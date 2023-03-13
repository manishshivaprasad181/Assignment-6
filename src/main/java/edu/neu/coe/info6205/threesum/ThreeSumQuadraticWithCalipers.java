package edu.neu.coe.info6205.threesum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.neu.coe.info6205.util.Stopwatch;

/**
 * Implementation of ThreeSum which follows the approach of dividing the solution-space into
 * N sub-spaces where each sub-space corresponds to a fixed value for the middle index of the three values.
 * Each sub-space is then solved by expanding the scope of the other two indices outwards from the starting point.
 * Since each sub-space can be solved in O(N) time, the overall complexity is O(N^2).
 * <p>
 * The array provided in the constructor MUST be ordered.
 */
public class ThreeSumQuadraticWithCalipers implements ThreeSum {
    /**
     * Construct a ThreeSumQuadratic on a.
     *
     * @param a a sorted array.
     */
    public ThreeSumQuadraticWithCalipers(int[] a) {
        this.a = a;
        length = a.length;
    }

    /**
     * Get an array or Triple containing all of those triples for which sum is zero.
     *
     * @return a Triple[].
     */
    public Triple[] getTriples() {
        List<Triple> triples = new ArrayList<>();
        Collections.sort(triples); // ???
        for (int i = 0; i < length - 2; i++)
            triples.addAll(calipers(a, i, Triple::sum));
        return triples.stream().distinct().toArray(Triple[]::new);
    }

    /**
     * Get a set of candidate Triples such that the first index is the given value i.
     * Any candidate triple is added to the result if it yields zero when passed into function.
     *
     * @param a        a sorted array of ints.
     * @param i        the index of the first element of resulting triples.
     * @param function a function which takes a triple and returns a value which will be compared with zero.
     * @return a List of Triples.
     */
    
    public static List<Triple> calipers(int[] a, int i, Function<Triple, Integer> function) {
        List<Triple> triples = new ArrayList<>();
        Arrays.sort(a);
        if (i == 0 || (i > 0 && a[i] != a[i - 1])) {
            int low = i + 1, high = a.length - 1, target = 0 - a[i];
            while (low < high) {
                if (a[low] + a[high] == target) {
                    Triple temp = new Triple(a[i], a[low], a[high]);
                    triples.add(temp);
                    while (low < high && a[low] == a[low + 1]) {
                        low++;
                    }
                    while (low < high && a[high] == a[high - 1]) {
                        high--;
                    }
                    low++;
                    high--;
                } else if (a[low] + a[high] < target) {
                    low++;
                } else {
                    high--;
                }
            }
        }
        return triples;
    }

    
	public static void main(String[] args) {
		Supplier<int[]> intsSupplier = null;
		int[] ints = null; 
		List<int[]> listOfArrays = new ArrayList<>();
		List<Integer> nValues = new ArrayList<>();
		List<Double> nValues1 = new ArrayList<>();
	
		
		List<Double> times = new ArrayList<>();
		List<Double> times1 = new ArrayList<>();

        calculateValues(intsSupplier, listOfArrays, nValues, nValues1, times, times1);
        plotLineGraph(nValues, times);
        plotLineGraphLOG(nValues1, times1);
	}
	
	public static void calculateValues(Supplier<int[]> intsSupplier,List<int[]> listOfArrays,List<Integer> nValues,List<Double> nValues1,	List<Double> times, List<Double> times1) {
		
		int factor = (int)1.2;

	        int[] arrInt = null;

			for (int i = 1; i <= 8; i++) {
				factor *= 2; 
				intsSupplier = new Source(100 * factor, 2).intsSupplier(10);
				arrInt = intsSupplier.get();
				listOfArrays.add(arrInt);
				nValues.add(factor * 100);
				nValues1.add(Math.log(factor * 100));
			}
	        
	    	listOfArrays.forEach(arr -> {
				try (Stopwatch watch = new Stopwatch()) {
					Arrays.sort(arr);
					ThreeSum target = new ThreeSumQuadraticWithCalipers(arr);
					Triple[] triples = target.getTriples();
					double timeEnd =  watch.lap();
					times.add(timeEnd);
					times1.add(Math.log(timeEnd));
				}
			});
		
		
	}

	private static void plotLineGraph(List<Integer> nValues, List<Double> times) {

		XYSeries series = new XYSeries("Line Graph Example");
		
		System.out.println("\nThree Sum Quadratic With Calipers:");
		for (int i = 0; i < nValues.size(); i++) {
			int xval = nValues.get(i);
			double yval = times.get(i);
			System.out.println(" x : " + xval + " y : " + yval);
			
			series.add(xval, yval);
		}

		XYDataset dataset = new XYSeriesCollection(series);

		JFreeChart chart = ChartFactory.createXYLineChart("Line Graph", "ArrayLength", "Time", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		ChartPanel panel = new ChartPanel(chart);
		JFrame frame = new JFrame("Line Graph Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
	
	private static void plotLineGraphLOG(List<Double> nValues1, List<Double> times1) {

		XYSeries series = new XYSeries("Line Graph Example");

		System.out.println("\nThree Sum Quadratic With Calipers log plots:");
		for (int i = 0; i < nValues1.size(); i++) {
			double xval = nValues1.get(i);
			double yval = times1.get(i);
			if (!Double.isInfinite(yval)) {
				System.out.println(" x : " + xval + " y : " + yval);
				series.add(xval, yval);
			}
		}

		XYDataset dataset = new XYSeriesCollection(series);

		JFreeChart chart = ChartFactory.createXYLineChart("Line Graph for LOG", "ArrayLength", "Time", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		ChartPanel panel = new ChartPanel(chart);
		JFrame frame = new JFrame("Line Graph Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
    
    private final int[] a;
    private final int length;
}
