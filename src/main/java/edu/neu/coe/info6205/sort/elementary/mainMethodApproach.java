package edu.neu.coe.info6205.sort.elementary;
import java.util.Random;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;


public class mainMethodApproach extends ApplicationFrame {
    public mainMethodApproach(String title) {
        super(title);
    }

    public static void main(String[] args) {
    	
    	mainMethodApproach mainProgram = new mainMethodApproach("Insertion Sort Time Complexity");
        Integer[] randomArray, orderedArray, partiallyOrderedArray, reverseOrderedArray;
        InsertionSort insertionSort = new InsertionSort();
        
        int n = 2500; // starting value for n
        int numTrials = 5; // number of trials for each value of n
        
        XYSeries randomData = new XYSeries("Random Array");
        XYSeries orderedData = new XYSeries("Ordered Array");
        XYSeries partiallyOrderedData = new XYSeries("Partially Ordered Array");
        XYSeries reverseOrderedData = new XYSeries("Reverse Ordered Array");
        
        for (int i = 0; i < numTrials; i++) {
            randomArray = addArray(n,true,false,false,false);
            orderedArray = addArray(n,false,true,false,false);
            partiallyOrderedArray = addArray(n,false,false,true,false);
            reverseOrderedArray = addArray(n,false,false,false,true);
            System.out.println("Random array:");
            long startTime = System.currentTimeMillis();
            insertionSort.sort(randomArray, 0, randomArray.length);
            long endTime = System.currentTimeMillis();
            randomData.add(n, endTime - startTime);
            System.out.println("Time elapsed: " + (endTime - startTime) + "ms");
            System.out.println("Ordered array:");
            startTime = System.currentTimeMillis();
            insertionSort.sort(orderedArray, 0, orderedArray.length);
            endTime = System.currentTimeMillis();
            orderedData.add(n, endTime - startTime);
            System.out.println("Time elapsed: " + (endTime - startTime) + "ms");
            System.out.println("Partially ordered array:");
            startTime = System.currentTimeMillis();
            insertionSort.sort(partiallyOrderedArray, 0, partiallyOrderedArray.length);
            endTime = System.currentTimeMillis();
            partiallyOrderedData.add(n, endTime - startTime);
            System.out.println("Time elapsed: " + (endTime - startTime) + "ms");
            System.out.println("Reverse ordered array:");
            startTime = System.currentTimeMillis();
            insertionSort.sort(reverseOrderedArray, 0, reverseOrderedArray.length);
            endTime = System.currentTimeMillis();
            reverseOrderedData.add(n, endTime - startTime);
            System.out.println("Time elapsed: " + (endTime - startTime) + "ms");
            
            
            System.out.println();


            n *= 2; // double n for next trial
        }
        XYSeriesCollection data = new XYSeriesCollection();
        data.addSeries(randomData);
        data.addSeries(orderedData);
        data.addSeries(partiallyOrderedData);
        data.addSeries(reverseOrderedData);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Insertion Sort Time Complexity",
                "Array Size (n)",
                "Time (ms)",
                data,
                PlotOrientation.VERTICAL, true, true, false);

		// Display the chart
		ChartFrame frame = new ChartFrame("Insertion Sort Running Time", chart);
		frame.pack();
		frame.setVisible(true);
	}
    private static Integer[] addArray(int n,boolean Random,boolean Ordered, boolean PartialOrdered,boolean ReverseOrdered) {
        
    	Integer[] array = new Integer[n];
    	if(Random==true) {
    		 Random random = new Random();
    	        
    	        for (int i = 0; i < n; i++) {
    	            array[i] = random.nextInt();
    	        }
    	}
    	
    	else if(Ordered==true) {
    		for (int i = 0; i < n; i++) {
                array[i] = i;
            }
    	}
    	else if(PartialOrdered==true) {
    		for (int i = 0; i < n / 2; i++) {
                array[i] = i;
            }
            for (int i = n / 2; i < n; i++) {
                array[i] = n - i - 1;
            }
    	}
    	else if(ReverseOrdered==true) {
    		 for (int i = 0; i < n; i++) {
    	            array[i] = n - i - 1;
    	        }
    	}
    	 return array;
    }
}

    
   
